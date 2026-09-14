package _01thread;
/*
# Java 创建线程的4种方式

> 面试常考：**4种方式本质都是实现`Runnable`，Callable只是带返回值的Runnable变种**

## 方式1：继承 Thread 类

重写`run()`，调用`start()`启动线程

```
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

## 方式2：实现 Runnable 接口（推荐，无单继承限制）

```
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

## 方式3：Callable + FutureTask（支持返回值、抛出异常）

Runnable的run无返回值，Callable的`call()`有返回值

```
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

## 方式4：线程池（生产环境首选，复用线程）

```
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

# 简单对比总结

1. 继承Thread：不能再继承其他类，耦合高，不推荐
2. Runnable：无返回值，避免单继承限制，常用
3. Callable+FutureTask：**有返回值，可捕获异常**
4. 线程池：项目实际开发使用，减少创建销毁开销

> 面试一句话：**创建线程只有一种本质，就是new Thread，传入任务；Runnable、Callable、线程池都只是任务的不同形式**
*/
public class Demo2 {
}
