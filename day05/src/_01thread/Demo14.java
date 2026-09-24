package _01thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
# Java 等待唤醒（生产者消费者）用阻塞队列实现

核心要点：
`java.util.concurrent` 下的阻塞队列 `BlockingQueue` 本身**自带等待唤醒逻辑**，内部已经封装了 `Lock + Condition`，不用我们自己写 `wait()/notify()`。

- 生产者：往队列放数据，队列满时自动阻塞等待
- 消费者：从队列取数据，队列为空时自动阻塞等待
常用实现类：`ArrayBlockingQueue`（有界队列，生产最常用）

> 对比原生 wait/notify：阻塞队列更简洁、不容易写出假唤醒、通知丢失的bug，项目里优先用。

## 可运行完整代码

```
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockQueueWaitNotifyDemo {
    // 有界阻塞队列，容量3
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

    public static void main(String[] args) {
        // 生产者线程
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    System.out.println("生产者准备放入：" + i);
                    // put：队列满就阻塞，等待空位（底层Condition.await）
                    queue.put(i);
                    System.out.println("生产者成功放入：" + i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "生产者");

        // 消费者线程
        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    // take：队列为空就阻塞，等待有数据
                    Integer data = queue.take();
                    System.out.println("消费者拿到数据：" + data);
                    Thread.sleep(1000); // 模拟消费耗时
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "消费者");

        producer.start();
        consumer.start();
    }
}
```

## 关键方法说明

1. `put(E e)`：添加元素，队列满→阻塞等待，有空位自动唤醒
2. `take()`：取出队首元素，队列空→阻塞等待，有数据自动唤醒
3. 内部：`ReentrantLock + 两个Condition`（notFull、notEmpty），区分生产者、消费者等待集合，比 `wait/notifyAll` 高效

## 和原生 wait-notify 的区别

- 原生：自己锁、自己判断队列状态、`wait()`、`notifyAll()`，容易丢通知、虚假唤醒
- BlockingQueue：把等待唤醒封装到底层，业务代码只需要 `put/take`，工业级代码首选

## 运行现象

队列容量3，生产者放满3条后，`put` 会卡住；消费者取走一条，队列有空位，生产者自动恢复继续放。

## Lock + Condition 手动实现等待唤醒

> 核心：自己维护队列、锁、两个条件变量 `notEmpty`（消费者等数据）、`notFull`（生产者等空位），对应上面 `ArrayBlockingQueue` 底层的实现思路。
> 重点：**while循环判断条件，防止虚假唤醒**

```
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionWaitNotifyDemo {
    // 容器，模拟队列
    private static final LinkedList<Integer> queue = new LinkedList<>();
    // 队列最大容量
    private static final int MAX_SIZE = 3;

    private static final ReentrantLock lock = new ReentrantLock();
    // 条件1：队列非空，消费者等待
    private static final Condition notEmpty = lock.newCondition();
    // 条件2：队列未满，生产者等待
    private static final Condition notFull = lock.newCondition();

    public static void main(String[] args) {
        // 生产者
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                lock.lock();
                try {
                    // while 防止虚假唤醒！！不能用if
                    while (queue.size() == MAX_SIZE) {
                        System.out.println("队列已满，生产者等待");
                        notFull.await(); // 生产者等待，释放锁
                    }
                    queue.addLast(i);
                    System.out.println("生产者放入：" + i);
                    notEmpty.signal(); // 唤醒等待数据的消费者
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        }, "生产者");

        // 消费者
        Thread consumer = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        System.out.println("队列为空，消费者等待");
                        notEmpty.await(); // 消费者等待
                    }
                    Integer data = queue.removeFirst();
                    System.out.println("消费者取出：" + data);
                    notFull.signal(); // 唤醒生产者
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(1000); // 模拟消费耗时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者");

        producer.start();
        consumer.start();
    }
}
```

## synchronized + wait/notifyAll

> 缺陷：只有一个等待集合，只能 `notifyAll`，不能精准唤醒，性能差，容易踩坑

```
import java.util.LinkedList;

public class SyncWaitNotifyDemo {
    private static final LinkedList<Integer> queue = new LinkedList<>();
    private static final int MAX_SIZE = 3;

    public static void main(String[] args) {
        // 生产者
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                synchronized (queue) {
                    try {
                        while (queue.size() == MAX_SIZE) {
                            System.out.println("队列满，生产者wait");
                            queue.wait();
                        }
                        queue.add(i);
                        System.out.println("生产者放入：" + i);
                        queue.notifyAll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }, "生产者").start();

        // 消费者
        new Thread(() -> {
            while (true) {
                synchronized (queue) {
                    try {
                        while (queue.isEmpty()) {
                            System.out.println("队列空，消费者wait");
                            queue.wait();
                        }
                        Integer val = queue.removeFirst();
                        System.out.println("消费者取出：" + val);
                        queue.notifyAll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者").start();
    }
}
```

## 三、三者对比总结

1. **synchronized + wait/notifyAll**
   - 只有一个等待集，只能全部唤醒
   - 必须 while 判断，防止虚假唤醒
   - 底层监视器锁，无法中断等待、非公平
2. **ReentrantLock + Condition**
   - 可以多个Condition，生产者、消费者分开等待、精准唤醒（signal）
   - `ArrayBlockingQueue` 底层就是这套代码
   - 可公平/非公平锁、支持中断等待
3. **BlockingQueue（推荐业务使用）**
   - 封装上面这套逻辑，对外只暴露 put/take
   - 业务代码极简，不容易出错

## 面试常问点

- 为什么 await / wait 要用 while 而不是 if？
> 线程被唤醒之后，**条件可能又被别的线程修改了**，if 只会判断一次，会出现数据异常；while 唤醒后再次检查条件，不满足继续等待。

- signal 和 signalAll 区别？
> signal：唤醒一个在该Condition等待的线程；signalAll：唤醒全部。
*/
public class Demo14 {
    // 有界阻塞队列，容量3
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

    public static void main(String[] args) {
        // 生产者线程
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    // put：队列满就阻塞，等待空位（底层Condition.await）
                    queue.put(i);
                    System.out.println(STR."生产者成功放入：\{i}");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "生产者");

        // 消费者线程
        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    // take：队列为空就阻塞，等待有数据
                    Integer data = queue.take();
                    System.out.println(STR."消费者拿到数据：\{data}");
                    Thread.sleep(1000); // 模拟消费耗时
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "消费者");

        producer.start();
        consumer.start();
    }
}
