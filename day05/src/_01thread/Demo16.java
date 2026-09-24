package _01thread;
/*
# Demo14中轮询和等待唤醒对比示例，从任务管理器，根本看不出 CPU 占用有啥区别

先说核心结论：
> **Demo14那段轮询代码，直接跑，任务管理器很难看出巨大CPU差异**，原因不是理论错了，是代码写得“不够极端”，有两个拖累因素：
> 1. 轮询版本我加了 `Thread.sleep(200)`，本身就是主动降频；
> 2. 锁 `synchronized` 会让线程频繁阻塞在抢锁，不是纯粹无休的空循环。

## 1. 真正能看出CPU爆炸的轮询：去掉sleep、去掉锁内无意义判断
纯空转版本：线程无休眠、无阻塞，一直跑循环判断，这种才能看到CPU飙升。

```java
// 【纯空转轮询！高CPU版本】不要在生产写，仅测试观察CPU
public class BusyWaitDemo {
    private static volatile boolean hasData = false;

    public static void main(String[] args) {
        // 消费者：忙等，没有sleep，没有wait，一直轮询
        new Thread(() -> {
            while (true) {
                if (hasData) {
                    System.out.println("拿到数据");
                    hasData = false;
                }
                // 重点：这里什么都不做，疯狂循环！
            }
        }, "忙等消费者").start();

        // 生产者，3秒后放数据
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                hasData = true;
                System.out.println("生产者放入数据");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
```
运行这个：你会看到这个线程直接吃满一个CPU核心（100%单核心占用）。
原理：CPU一直在跑这个while循环，没有任何地方让线程让出CPU，操作系统不会把它挂起。

> `volatile` 保证可见性，但**不阻止CPU空转**。

## 2. 等待唤醒版本，同等场景，几乎不耗CPU
```java
public class WaitDemo {
    private static boolean hasData = false;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    try {
                        while (!hasData) {
                            System.out.println("没数据，wait，线程挂起");
                            lock.wait(); // 线程进入WAITING状态，OS把它挂起，不分配CPU时间片
                        }
                        System.out.println("拿到数据");
                        hasData = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "wait消费者").start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                synchronized (lock) {
                    hasData = true;
                    lock.notify();
                    System.out.println("生产者放入数据，唤醒消费者");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
```
这个在等待期间，线程状态是 `WAITING`，操作系统**不给它分配CPU时间片**，CPU占用几乎为0。

## 3. 回到刚才生产者消费者的那个轮询代码，为什么看不出差距？
```java
// 之前的轮询消费者片段
while (true) {
    synchronized (queue) {
        if (!queue.isEmpty()) {
            ...消费
        }
    }
    Thread.sleep(200); // <<< 关键在这里
}
```
- `sleep(200)`：线程主动休眠，休眠期间线程是 `TIMED_WAITING`，不消耗CPU；
- 就算去掉sleep，里面有`synchronized`抢锁，大量时间阻塞在锁上，不是纯粹的CPU计算；
所以它不是教科书里的“忙等(busy spin)”。

> 两个概念要分清：
> - **忙等待（自旋、busy-wait）**：不释放CPU，一直循环判断 → CPU高
> - **带sleep的轮询**：主动休息，牺牲延迟换CPU，CPU不会爆高

## 4. 面试里容易混淆的点
面试官问“wait为什么节约CPU”，指的是**对比无休眠的忙自旋**，不是对比带sleep的轮询。
- 忙自旋：CPU持续跑循环，线程处于RUNNABLE，一直抢时间片
- wait/await：线程进入WAITING，操作系统挂起，不再调度

## 5. 补充：自旋锁（CAS）就是忙等
`LockSupport.park()` 才是阻塞挂起；CAS自旋就是忙等，适合等待时间极短的场景，等太久CPU就炸。

## 6. 怎么在电脑上验证？
1. 运行【BusyWaitDemo】纯忙等代码
2. Windows任务管理器 → 详细信息，找到java进程，看CPU
3. 再换成WaitDemo对比
> 注意：现代CPU有多核，它只会占满**一个逻辑核心**，整机CPU可能只显示百分之十几，要看单线程占用，不是总CPU。
*/
public class Demo16 {
    private static boolean hasData = false;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    try {
                        while (!hasData) {
                            System.out.println("没数据，wait，线程挂起");
                            lock.wait(); // 线程进入WAITING状态，OS把它挂起，不分配CPU时间片
                        }
                        System.out.println("拿到数据");
                        hasData = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "wait消费者").start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                synchronized (lock) {
                    hasData = true;
                    lock.notify();
                    System.out.println("生产者放入数据，唤醒消费者");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
