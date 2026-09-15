package _01thread;
/*
# Java Thread 常用方法

本文汇总Java线程**所有高频面试/开发常用方法**，包含基础操作、线程休眠、等待唤醒、线程优先级、守护线程、线程中断等，附带完整可运行代码、核心特性及面试考点，全覆盖无遗漏。

方法分为两大类：**实例方法（线程对象调用）**、**静态方法（Thread类直接调用）**

## 一、基础核心方法

### 1. start()：启动线程（实例方法）

作用：向JVM申请创建新线程、分配资源，线程进入就绪状态，JVM自动回调run()。

重点：**只能调用一次**，重复调用抛出IllegalThreadStateException；直接调用run()不会开启新线程。

```java
public class ThreadMethodDemo {
    public static void main(String[] args) {
        Thread t = new Thread(() -> System.out.println("新线程执行任务"));
        t.start(); // 开启新线程
        // t.start(); // 二次调用，直接报错
    }
}
```

### 2. run()：线程任务方法（实例方法）

作用：封装线程具体执行的业务逻辑。

重点：手动调用run()只是普通方法执行，**不会创建新线程**，在当前主线程执行。

```java
Thread t = new Thread(() -> System.out.println("执行run任务"));
t.run(); // 主线程执行，无新线程
```

### 3. currentThread()：获取当前线程（静态方法）

作用：获取当前正在执行代码的线程对象，高频用于日志打印、线程判断。

```java
public static void main(String[] args) {
    Thread current = Thread.currentThread();
    System.out.println("当前线程名称：" + current.getName()); // 默认main
}
```

### 4. getName() / setName()：线程名称操作（实例方法）

作用：获取/自定义线程名称，方便日志排查、线程定位。

```java
Thread t = new Thread(() -> System.out.println("自定义线程执行"));
t.setName("my-work-thread");
System.out.println("线程名称：" + t.getName());
```

### 5. isAlive()：判断线程存活状态（实例方法）

作用：判断线程是否处于活跃状态（已启动、未终止）。

```java
Thread t = new Thread(() -> {
    try { Thread.sleep(1000); } catch (Exception e) {}
});
System.out.println("启动前是否存活：" + t.isAlive()); // false
t.start();
System.out.println("启动后是否存活：" + t.isAlive()); // true
```

## 二、线程阻塞与调度方法

### 6. sleep(long millis)：线程休眠（静态方法）

作用：让当前线程休眠指定毫秒，主动让出CPU时间片。

核心考点：**休眠不释放锁**、抛出InterruptedException、时间到后自动进入就绪状态。

```java
public static void main(String[] args) {
    System.out.println("开始休眠");
    try {
        Thread.sleep(2000); // 当前线程休眠2秒
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("休眠结束");
}
```

### 7. join()：线程插队等待（实例方法）

作用：当前线程阻塞，**等待调用join的子线程执行完毕**，再继续执行自身任务。

```java
public static void main(String[] args) throws InterruptedException {
    Thread t = new Thread(() -> {
        try { Thread.sleep(1000); } catch (Exception e) {}
        System.out.println("子线程执行完毕");
    });
    t.start();
    t.join(); // 主线程等待子线程完成
    System.out.println("主线程继续执行");
}
```

### 8. yield()：线程礼让（静态方法）

作用：当前线程主动让出CPU时间片，从运行态回到就绪态。

重点：**只是协商机制，不保证一定礼让成功**，可能再次抢到CPU执行权。

```java
Thread.yield();
```

## 三、线程优先级方法

### 9. setPriority() / getPriority()：设置/获取线程优先级

优先级范围：1~10，默认5

- Thread.MIN_PRIORITY = 1（最低优先级）

- Thread.NORM_PRIORITY = 5（默认优先级）

- Thread.MAX_PRIORITY = 10（最高优先级）

核心考点：优先级仅为**操作系统执行建议**，无法绝对控制线程执行顺序，不能用于业务逻辑控制。

```java
public static void main(String[] args) {
    Thread t1 = new Thread(() -> System.out.println("低优先级线程执行"));
    Thread t2 = new Thread(() -> System.out.println("高优先级线程执行"));

    t1.setPriority(Thread.MIN_PRIORITY);
    t2.setPriority(Thread.MAX_PRIORITY);

    System.out.println("t1优先级：" + t1.getPriority());
    System.out.println("t2优先级：" + t2.getPriority());

    t1.start();
    t2.start();
}
```

## 四、守护线程方法

### 10. setDaemon() / isDaemon()：设置/判断守护线程

线程分类：

- **用户线程（默认）**：JVM会等待所有用户线程执行完毕才退出

- **守护线程**：后台服务线程（如GC线程），所有用户线程结束后，JVM直接退出，不等待守护线程执行完成

硬性规则：**setDaemon()必须在start()之前调用**，启动后调用直接报错。

```java
public static void main(String[] args) throws InterruptedException {
    Thread daemonThread = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(500);
                System.out.println("守护线程后台运行中...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    // 设置为守护线程，必须在start之前
    daemonThread.setDaemon(true);
    daemonThread.start();

    System.out.println("主线程执行中...");
    Thread.sleep(2000);
    System.out.println("主线程（用户线程）结束，JVM即将退出");
}
```

## 五、线程中断方法（高频面试）

### 11. interrupt() / isInterrupted() / interrupted()

- **interrupt()**：给线程打中断标记，不会直接终止线程

- **isInterrupted()**（实例）：查询中断标记，**不清除标记**

- **interrupted()**（静态）：查询中断标记，**查询后清除标记**

重点：线程处于sleep/wait/join阻塞状态时，调用interrupt()会抛出异常并清除中断标记。

```java
public static void main(String[] args) throws InterruptedException {
    Thread t = new Thread(() -> {
        // 循环判断中断标记
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("线程正常运行");
        }
        System.out.println("线程被中断，任务结束");
    });

    t.start();
    Thread.sleep(1000);
    t.interrupt(); // 设置中断标记
}
```

## 六、高频面试易错点总结

1. **sleep()**：Thread静态方法，不释放锁；**wait()**：Object方法，释放锁，必须在同步代码块中执行

2. start()仅可调用一次，run()可无限调用但无新线程

3. 守护线程必须在start()前设置，适合后台监控、日志统计等非核心任务

4. 线程优先级仅为建议，不保证执行顺序，不可依赖优先级做业务判断

5. interrupt()仅做标记，无法强制终止线程，需自行判断标记结束任务
*/
public class Demo4 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++){
                // 获取线程名称
                System.out.println(STR."thread1线程：\{Thread.currentThread().getName()}-\{i}");
            }
        },"线程1");

        Thread thread2 = new Thread(() -> {
            //作用：当前线程主动让出CPU时间片，从运行态回到就绪态。
            //重点：只是协商机制，不保证一定礼让成功，可能再次抢到CPU执行权。
            //Thread.yield();

            try {
                // 当前线程阻塞，等待调用join的子线程执行完毕，再继续执行自身任务
                thread1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < 20; i++) {
                System.out.println(STR."thread2线程：\{Thread.currentThread().getName()}-\{i}");
            }
        },"thread2");

        // 修改线程名称
        thread1.setName("thread1");

        // 获取线程名称
        System.out.println(STR."main线程代码：\{thread1.getName()}！");

        // 获取线程优先级
        System.out.println(thread1.getPriority());
        System.out.println(thread2.getPriority());
        System.out.println(Thread.currentThread().getPriority());

        // 设置线程优先级
        // 注意：优先级仅为操作系统执行建议，无法绝对控制线程执行顺序，不能用于业务逻辑控制
        //thread1.setPriority(1);
        //thread2.setPriority(10);

        // 将 thread2 设置为守护线程
        //thread2.setDaemon(true);
        // 判断 thread2 是否为守护线程
        //System.out.println(STR."thread2线程是否为守护线程：\{thread2.isDaemon()}");

        // 启动线程
        thread1.start();
        thread2.start();
    }
}
