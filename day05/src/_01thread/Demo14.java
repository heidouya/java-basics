package _01thread;
/*
# 为什么需要等待唤醒机制
**等待唤醒机制，就是让线程“没事就歇着（释放锁、阻塞），有活再叫醒干活”，避免CPU空转浪费资源。**
它解决的核心问题：线程之间**条件不满足时不要轮询死循环一直抢CPU**，线程间互相通信。

---
## 1. 没有等待唤醒会怎么样？（轮询）
举个例子：消费者不断循环查队列有没有数据
```java
// 低效写法，轮询
while(true){
    if(!queue.isEmpty()){
        拿数据消费
    }
    // 不加sleep，CPU直接100%，疯狂循环判断
}
```
- 队列空的时候，线程不停循环判断，白白占用CPU
- 就算加`sleep`也有缺点：间隔不好调，数据来了不能立刻处理，延迟高

## 2. 等待唤醒做了什么
- `wait()` / `await()`：**线程主动进入等待状态，并且释放持有的锁**，让出CPU，不再竞争
- `notify()` / `signal()`：别的线程修改完条件后，发信号，唤醒等待的线程
- 唤醒后线程重新抢锁，再判断条件，继续工作

> 本质：**线程间条件通信**，适合“条件不具备就等待，条件就绪再执行”的场景

## 3. 典型业务场景
1. **生产者消费者模型（最经典）**
队列满 → 生产者等待；队列有空位唤醒生产者
队列空 → 消费者等待；有数据唤醒消费者
前面阻塞队列、Lock+Condition代码都是这个场景。

2. **任务队列、线程池**
线程池里的工作线程，没有任务时阻塞等待新任务，任务提交后唤醒线程。

3. **资源控制、限流**
资源用完了，线程等待，别人归还资源之后再唤醒。比如连接池，连接用光了，请求线程等待连接释放。

4. **多线程协同、顺序控制**
A线程要等B线程完成某个操作之后才能继续，B做完唤醒A。比如：主线程等待子线程加载完数据再往下走。

## 4. 几种实现的定位
1. `synchronized + wait / notify`：原生，简单，只能一个等待集合，容易虚假唤醒，老代码用
2. `ReentrantLock + Condition`：可以多组等待，精准唤醒（生产者一组、消费者一组），ArrayBlockingQueue底层就是它
3. `BlockingQueue`：封装等待唤醒，业务代码直接用，**开发首选**，屏蔽底层wait/await细节

## 补充区分
⚠️ 它不是用来解决线程安全（原子性、可见性）的，**线程安全靠锁；等待唤醒是线程通信协作**。锁保证同一时间只有一个线程操作共享变量；等待唤醒决定线程什么时候该停下来等、什么时候继续跑。

## 对比：轮询（低效） vs wait等待唤醒（高效）

核心目标：消费者检测队列是否有数据。

> 轮询：不停循环判断，不释放锁，CPU空转；
> wait等待：没数据就释放锁，进入阻塞，不占CPU，有数据再被唤醒。

## 版本1：轮询版（不使用等待唤醒，CPU高）

```
import java.util.LinkedList;

public class PollDemo {
    private static final LinkedList<Integer> queue = new LinkedList<>();
    private static final int MAX = 3;

    public static void main(String[] args) {
        // 生产者
        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                synchronized (queue) {
                    while (queue.size() >= MAX) {
                        // 轮询：这里只能不断释放锁再抢，没有等待机制
                    }
                    queue.add(i);
                    System.out.println("生产：" + i);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "生产者").start();

        // 消费者【重点：轮询】
        new Thread(() -> {
            while (true) {
                synchronized (queue) {
                    if (!queue.isEmpty()) {
                        Integer val = queue.removeFirst();
                        System.out.println("消费：" + val);
                    }
                    // 不加sleep：疯狂循环抢锁判断，CPU飙升
                }
                // 妥协方案：加sleep降低CPU，但带来延迟
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者").start();
    }
}
```

### 轮询的痛点

1. 不加`sleep`：while无限循环抢锁判断，CPU占用很高；
2. 加`sleep`降CPU：数据来了不能立刻处理，有延迟；
3. 你没法精准知道“什么时候数据就绪”，只能盲猜间隔。

---

## 版本2：wait + notify 等待唤醒版（高效）

```
import java.util.LinkedList;

public class WaitNotifyDemo {
    private static final LinkedList<Integer> queue = new LinkedList<>();
    private static final int MAX = 3;

    public static void main(String[] args) {
        // 生产者
        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                synchronized (queue) {
                    try {
                        while (queue.size() >= MAX) {
                            System.out.println("队列满，生产者等待");
                            queue.wait(); // 释放锁，阻塞，让出CPU
                        }
                        queue.add(i);
                        System.out.println("生产：" + i);
                        queue.notifyAll(); // 唤醒等待的消费者
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "生产者").start();

        // 消费者【等待唤醒】
        new Thread(() -> {
            while (true) {
                synchronized (queue) {
                    try {
                        while (queue.isEmpty()) {
                            System.out.println("队列为空，消费者等待");
                            queue.wait(); // 释放锁，阻塞，不消耗CPU
                        }
                        Integer val = queue.removeFirst();
                        System.out.println("消费：" + val);
                        queue.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "消费者").start();
    }
}
```

### wait版特点

1. 队列空时，消费者执行`wait()`：**释放监视器锁，线程挂起，不再参与CPU调度**，几乎不消耗CPU；
2. 生产者放入数据后调用`notifyAll()`，主动发信号唤醒消费者；
3. 唤醒后线程重新抢锁，再校验条件（while防虚假唤醒），立刻处理数据，**无轮询延迟**。

---

## 简单总结

- 轮询：**我不停去问问好了没有**，要么CPU高，要么有延迟；
- 等待唤醒：**你好了喊我一声，我先睡觉不占CPU**。

## 面试精简版
等待唤醒机制用于**多线程之间的通信协作**。当线程执行的条件不满足时，让线程阻塞等待并释放锁，不消耗CPU；当其他线程修改条件后，发送信号唤醒等待线程，从而替代低效的轮询，节约CPU资源，常见于生产者消费者、线程池、资源池等场景。

一句话：等待唤醒机制就是为了解决轮询的缺陷：条件不满足时线程释放锁阻塞，不浪费CPU；条件就绪由其他线程主动唤醒，做到及时响应。
*/
import java.util.LinkedList;

public class Demo14 {
    private static final LinkedList<Integer> queue = new LinkedList<>();
    private static final int MAX = 3;

    public static void main(String[] args) {
        // 生产者
        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                synchronized (queue) {
                    while (queue.size() >= MAX) {
                        // 轮询：这里只能不断释放锁再抢，没有等待机制
                    }
                    queue.add(i);
                    System.out.println("生产：" + i);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "生产者").start();

        // 消费者【重点：轮询】
        new Thread(() -> {
            while (true) {
                synchronized (queue) {
                    if (!queue.isEmpty()) {
                        Integer val = queue.removeFirst();
                        System.out.println("消费：" + val);
                    }
                    // 不加sleep：疯狂循环抢锁判断，CPU飙升
                }
                // 妥协方案：加sleep降低CPU，但带来延迟
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者").start();
    }
}
