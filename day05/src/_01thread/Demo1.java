package _01thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/*
# Java线程简要介绍

## 1. 什么是线程

**线程（Thread）** 是进程内独立执行的最小单元。一个进程可以包含多个线程，线程共享进程的堆、方法区资源，但每个线程拥有独立的**程序计数器、虚拟机栈、本地方法栈**。

多线程：同一进程内多条代码路径并发执行，充分利用 CPU。

> 进程：操作系统资源分配单位；线程：CPU 调度单位。

## 2. 为什么需要多线程

一句话总结：**最大化利用硬件资源、提升程序响应速度，把串行排队的任务改成并行处理**。

### 2.1 充分利用 CPU（核心原因）

CPU 速度远快于磁盘、网络 IO。
单线程场景：发起网络请求 / 读文件时，线程阻塞等待 IO，CPU 空闲干等着，资源浪费。
多线程：一个线程等 IO，其他线程继续跑计算任务，CPU 不会闲置。

> 区分：
> - IO 密集型（数据库、接口、文件读写）：多线程收益巨大
> - CPU 密集型（大量计算）：多核下多线程才能提速；单核多线程只是交替执行，不能提升计算速度

## 2.2 提升程序响应性（GUI / 服务端）

单线程如果执行耗时任务，整个程序会卡住。
例：桌面软件，点击按钮下载文件，单线程会导致界面卡死；新开线程做下载，主线程负责界面交互，界面始终可操作。
后端 Web 服务器：Tomcat，一个请求一个线程，如果单线程，同一时间只能处理一个用户请求。

## 2.3 任务拆分，业务模型更自然

有些业务本身就是多个独立任务同时进行：

- 服务同时接收用户请求、写日志、统计监控
- 消息消费：一边拉取消息，一边处理业务

> 用多线程可以把独立任务分开编写，比单线程轮询更容易理解。

## 2.4 多核 CPU 硬件红利

现在电脑、服务器都是多核 CPU。
单线程只能跑在一个 CPU 核心上，再多核心也用不上；多线程可以把任务分配到多个核心，真正并行执行，缩短总耗时。

## 3. 多线程存在的问题
但是！多线程不是万能的，有代价

1. 线程本身有开销：创建、销毁、上下文切换，频繁新建线程反而变慢（所以生产用线程池）
2. 线程安全问题：共享变量并发修改，出现脏数据，要加锁，加锁又会带来性能损耗、死锁风险
3. 代码复杂度大幅上升：bug 难复现、调试困难

> **IO 等待多、需要同时处理多个请求、多核机器想压榨性能，就用多线程；简单小任务没必要强行上多线程。**

## 4. 创建线程的 4 种方式

1. **继承 Thread 类**，重写`run()`，调用`start()`启动（`start()`会创建线程，自动调用 run；直接调用 run 只是普通方法）

```java
public class ThreadDemo extends Thread {
    @Override
    public void run() {
        System.out.println("线程执行：" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        ThreadDemo t1 = new ThreadDemo();
        t1.setName("继承Thread线程");
        t1.start(); // start开启新线程；直接调用run只是普通方法
    }
}
```

2. **实现 Runnable 接口**，重写`run()`，传入 Thread 构造。**推荐**，避免单继承限制。

```java
public class RunnableDemo implements Runnable {
    @Override
    public void run() {
        System.out.println("线程执行：" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Runnable runnable = new RunnableDemo();
        Thread t2 = new Thread(runnable, "实现Runnable线程");
        t2.start();

        // lambda简写写法（JDK8+）
        new Thread(() -> {
            System.out.println("lambda简写线程");
        }, "lambda线程").start();
    }
}
```

3. **实现 Callable 接口** + FutureTask：有返回值、可抛异常，`call()`方法

Runnable的run无返回值，Callable的`call()`有返回值

```java
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("Callable线程执行：" + Thread.currentThread().getName());
        return 100; // 线程返回结果
    }

    public static void main(String[] args) throws Exception {
        Callable<Integer> callable = new CallableDemo();
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        Thread t3 = new Thread(futureTask, "Callable线程");
        t3.start();
        // 获取返回值，会阻塞等待线程执行完毕
        Integer result = futureTask.get();
        System.out.println("线程返回结果：" + result);
    }
}
```

4. **线程池**（`ExecutorService`）：生产环境首选，复用线程，避免频繁创建销毁开销

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        // 创建固定大小线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);
        // 提交任务
        pool.submit(() -> {
            System.out.println("线程池任务：" + Thread.currentThread().getName());
        });
        pool.submit(() -> {
            System.out.println("线程池任务2：" + Thread.currentThread().getName());
        });
        // 关闭线程池
        pool.shutdown();
    }
}
```

简单对比总结：

1. 继承Thread：不能再继承其他类，耦合高，不推荐
2. Runnable：无返回值，避免单继承限制，常用
3. Callable+FutureTask：**有返回值，可捕获异常**
4. 线程池：项目实际开发使用，减少创建销毁开销

> 面试一句话：**创建线程只有一种本质，就是new Thread，传入任务；Runnable、Callable、线程池都只是任务的不同形式**

## 5. 线程 5 大状态

- **新建 NEW**：new Thread () 之后，未 start

- **就绪 RUNNABLE**：调用 start ()，等待 CPU 分配时间片

- **运行 RUNNING**：拿到 CPU 时间片，执行 run

- **阻塞 BLOCKED/WAITING/TIMED_WAITING**：等待锁、sleep、wait 等，让出 CPU

- **终止 TERMINATED**：run 执行完毕或异常退出

## 6. 常用方法

- `start()`：启动线程

- `sleep(long)`：休眠，**不释放锁**

- `wait()`：阻塞，**释放锁**，必须在 synchronized 内调用，由 notify 唤醒

- `notify()/notifyAll()`：唤醒等待锁的线程

- `join()`：等待其他线程执行完毕

- `yield()`：让出 CPU，回到就绪态

## 7. 线程安全问题

多个线程**同时操作共享变量**，会出现数据错乱。
解决手段：

- `synchronized`：同步锁（对象锁 / 类锁）

- `Lock`锁（ReentrantLock）

- 原子类 AtomicXXX（CAS 无锁）

- volatile：保证可见性、禁止指令重排，**不保证原子性**

## 8. 并发常见概念

- 并发：多个线程交替跑（单核）

- 并行：多个线程同时跑（多核 CPU）

- 锁：保证同一时间只有一个线程访问临界资源

- 死锁：多个线程互相持有对方需要的锁，互相等待，程序卡死

## 9. 多线程的应用场景
同时做多件事，就会用到多线程，比如：下载文件、上传文件、访问数据库、处理用户请求等。

*/
public class Demo1 {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        thread1.setName("线程1");
        thread1.start();

        MyThread thread2 = new MyThread();
        thread2.setName("线程2");
        thread2.start();


        Thread thread3 = new Thread(new MyRunnable(), "线程3");
        thread3.start();

        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(myCallable);
        Thread thread4 = new Thread(futureTask, "线程4");
        thread4.start();
        try {
            Integer result = futureTask.get();
            System.out.println(STR."线程4执行结果：\{result}");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

/*
创建线程方式1：继承 Thread 类
*/
class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println(STR."线程启动\{getName()}");
    }
}

/*
创建线程方式2：实现 Runnable 接口
*/
class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println(STR."线程启动\{Thread.currentThread().getName()}");
    }
}

/*
创建线程方式3： Callable接口 + FutureTask：有返回值、可抛异常
*/
class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(STR."线程启动\{Thread.currentThread().getName()}");
        return 1;
    }
}

/*
创建线程方式4：线程池
*/
class ThreadPoolDemo {
    public static void main(String[] args) {
        // 创建固定大小线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);
        // 提交任务
        pool.submit(() -> {
            System.out.println(STR."线程池任务1：\{Thread.currentThread().getName()}");
        });
        pool.submit(() -> {
            System.out.println(STR."线程池任务2：\{Thread.currentThread().getName()}");
        });
        // 关闭线程池
        pool.shutdown();
    }
}
