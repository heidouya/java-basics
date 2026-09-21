package _01thread;

/*
# 死锁
**定义**：多个线程互相持有对方需要的锁，同时又都在等待对方释放锁；大家谁都不肯放手，全部阻塞，程序卡死，这就是死锁。

死锁产生必须同时满足4个条件（**死锁四大必要条件**）
1. **互斥**：锁同一时间只能被1个线程持有（ReentrantLock、synchronized都满足）
2. **持有并等待**：线程拿到A锁，再去申请B锁，不释放A锁
3. **不可剥夺**：已经拿到的锁，不能被其他线程强行抢走，只能自己释放
4. **循环等待**：线程1等线程2的锁，线程2等线程1的锁，形成环路

> 只要破坏任意一条，就能避免死锁。

---

# 简洁示例（两个线程，两把锁）
```java
public class DeadLockDemo {
    // 两把独立的锁对象
    static Object lockA = new Object();
    static Object lockB = new Object();

    public static void main(String[] args) {
        // 线程1：先拿A锁，再拿B锁
        new Thread(() -> {
            synchronized (lockA) {
                System.out.println("线程1拿到lockA");
                try { Thread.sleep(500); } catch (Exception e) {}
                System.out.println("线程1等待lockB");
                synchronized (lockB) {
                    System.out.println("线程1拿到lockB");
                }
            }
        }).start();

        // 线程2：先拿B锁，再拿A锁
        new Thread(() -> {
            synchronized (lockB) {
                System.out.println("线程2拿到lockB");
                try { Thread.sleep(500); } catch (Exception e) {}
                System.out.println("线程2等待lockA");
                synchronized (lockA) {
                    System.out.println("线程2拿到lockA");
                }
            }
        }).start();
    }
}
```

## 运行现象
1. 线程1拿到 lockA，休眠
2. 线程2拿到 lockB，休眠
3. 休眠结束：
   - 线程1想要 lockB，但是 lockB在线程2手里
   - 线程2想要 lockA，但是 lockA在线程1手里
4. **两个线程永久互相等待，程序不会结束，卡死**

## 怎么破坏死锁（最简单方案）
统一**获取锁的顺序**：两个线程都先申请 lockA，再申请 lockB。
就不会出现循环等待，直接破坏第4个条件。

```java
// 线程2改成和线程1一样顺序：先A后B，死锁消失
new Thread(() -> {
    synchronized (lockA) {
        System.out.println("线程2拿到lockA");
        try { Thread.sleep(500); } catch (Exception e) {}
        System.out.println("线程2等待lockB");
        synchronized (lockB) {
            System.out.println("线程2拿到lockB");
        }
    }
}).start();
```

---

# 区分两个容易混淆的概念
1. **死锁**：多个线程互相等锁，全部卡住，代码不动。上面这个例子。
2. **锁泄漏**：线程拿到锁，代码异常/break，忘记调用unlock，锁没有释放。**只有一个锁，但锁丢了，其他线程一直等**。
> 前面示例卖票代码属于**锁泄漏**，不是死锁！

# 面试一句话总结
死锁是多个线程循环等待对方持有的锁；四个条件同时满足才会发生，最常用解决办法就是统一锁获取顺序。
*/
public class Demo12 {
    // 两把独立的锁对象
    static Object lockA = new Object();
    static Object lockB = new Object();

    public static void main(String[] args) {
        // 线程1：先拿A锁，再拿B锁
        new Thread(() -> {
            synchronized (lockA) {
                System.out.println("线程1拿到lockA");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                System.out.println("线程1等待lockB");
                synchronized (lockB) {
                    System.out.println("线程1拿到lockB");
                }
            }
        }).start();

        // 线程2：先拿B锁，再拿A锁
        new Thread(() -> {
            synchronized (lockB) {
                System.out.println("线程2拿到lockB");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                System.out.println("线程2等待lockA");
                synchronized (lockA) {
                    System.out.println("线程2拿到lockA");
                }
            }
        }).start();
    }
}
