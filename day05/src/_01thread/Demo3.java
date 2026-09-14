package _01thread;
/*
# 使用匿名内部类创建线程

匿名类本质还是 **Runnable / Thread**，只是不用单独写一个类，直接在代码里临时定义，JDK8之前lambda没出来时经常用。

## 方式1：Thread的匿名内部类
```java
public class ThreadAnonymousDemo {
    public static void main(String[] args) {
        // 继承Thread的匿名内部类
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread匿名类：" + Thread.currentThread().getName());
            }
        };
        t1.setName("线程A");
        t1.start();
    }
}
```

## 方式2：Runnable匿名内部类（更常用）
```java
public class ThreadAnonymousDemo {
    public static void main(String[] args) {
        // Runnable 匿名内部类，传给Thread
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable匿名类：" + Thread.currentThread().getName());
            }
        }, "线程B");
        t2.start();
    }
}
```

## 对比：Lambda 是匿名内部类的语法糖（JDK8+）
```java
// 等价于上面Runnable匿名类
Thread t3 = new Thread(() -> {
    System.out.println("Lambda写法");
}, "线程C");
t3.start();
```

# 面试小要点
1. 匿名内部类**只是写法**，不属于新的创建线程方式，底层依然是：
   - 要么继承Thread
   - 要么实现Runnable
2. 所以之前说的**4种创建线程方式不变**，匿名类只是语法形式，不算第5种。
3. 匿名类可以访问外部局部变量，但变量需要 `final` 或者**有效final**。

> 一句话：匿名内部类可以创建线程，只是简化写法，本质还是Thread和Runnable。
*/
public class Demo3 {
    public static void main(String[] args) {
        // 继承Thread的匿名内部类
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println(STR."Thread匿名类：\{Thread.currentThread().getName()}");
            }
        };
        t1.setName("线程A");
        t1.start();

        // Runnable匿名内部类，传给Thread
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(STR."Runnable匿名类：\{Thread.currentThread().getName()}");
            }
        }, "线程B");
        t2.start();

        // Lambda 是匿名内部类的语法糖
        Thread t3 = new Thread(() -> {
            System.out.println("Lambda写法");
        }, "线程C");
        t3.start();
    }
}
