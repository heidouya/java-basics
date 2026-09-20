package _01thread;
/*
# 为啥注销掉 sleep 相关代码，线程基本不切换呢？

去掉`sleep`之后，线程**释放锁的瞬间马上又进入下一轮while，再次争抢锁**。
当前线程刚释放锁，CPU还在它手里，它会优先再次抢到锁，反复执行，其他线程很难获得CPU时间片，所以看起来几乎永远是同一个线程在卖票。

> 注意：不是锁机制不让别的线程抢，是**操作系统线程调度**的问题。synchronized是非公平锁（Java默认）。

## 分步拆解代码
```java
@Override
public void run() {
    while (true) {
        synchronized (SaleTicketThread.class) {
            if (ticketNum >= 10) {
                break;
            }
            System.out.println(getName() + "正在卖第" + ++ticketNum + "张票");
        }
        // 这里没有sleep！执行完同步块，立刻回到while循环开头
    }
}
```
执行流程：
1. 线程A拿到锁 → 卖一张票 → 退出`synchronized`，**释放锁**
2. 代码继续往下走，没有sleep，直接回到`while(true)`循环头部
3. 立刻再次尝试进入`synchronized`抢锁
4. **此时线程A还处于运行状态，CPU时间片还没耗尽**，大概率再次抢到锁
5. 重复：A卖票 → 释放锁 → 马上抢锁 → 继续卖票

线程B、C虽然也在等待锁，但是**刚释放锁的时候，当前正在运行的线程会优先参与竞争**（非公平锁特性）。
线程A几乎不会让出CPU，其他线程很难被调度执行。

## 什么是【非公平锁】（synchronized内置锁就是非公平）
> 公平锁：锁释放后，优先交给排队等待最久的线程；
> 非公平锁：锁释放后，新过来抢锁的线程（当前正在运行的线程）和排队线程一起竞争，新来的有优势。

你这个场景：线程A释放锁之后，**立刻再次发起抢锁请求**，它不是阻塞在锁上的等待线程，属于“新来竞争者”，非公平锁下优势巨大。
所以大概率一直是A连续卖票，B、C几乎没机会。

> 不是永远一定是A，只是概率极高。极端情况下，CPU发生时间片切换，B也能抢到一两张。多跑几次main方法，偶尔能看到切换。

## 加上sleep为什么就容易切换？
```java
synchronized{ ... }
Thread.sleep(100); // 重点
```
1. 线程A卖完票，释放锁
2. 执行`sleep(100)`：**线程A进入阻塞状态，主动放弃CPU**
3. 在这100ms内，A不参与锁竞争
4. JVM调度器就可以唤醒等待队列里B或C，去抢锁卖票

sleep相当于**强制让当前线程歇一会**，把CPU机会留给其他线程。

## 补充一个容易混淆的点
很多同学会误以为：
> synchronized释放锁之后，就会自动切换到别的线程

❌ 错误。
**释放锁 ≠ CPU切换上下文**。
释放锁只是让锁变成可用状态，至于哪个线程拿到锁，由操作系统+锁的公平性共同决定。如果当前线程还在运行，它会继续参与竞争。

## 小实验验证
你可以把票总数改大一点（比如1000张），多运行几次：
- 不带sleep：绝大多数情况一个窗口一口气卖完；偶尔某次运行中间会切换一次线程
- 带sleep：多个窗口交替卖票，很均衡

## 拓展：如果想用公平锁实现轮流卖票（了解即可）
`synchronized`没法改成公平锁；如果用`ReentrantLock(true)`公平锁，就算去掉sleep，锁释放后优先交给排队的线程，就会出现线程交替执行。
```java
static Lock lock = new ReentrantLock(true); // true=公平锁
@Override
public void run() {
    while (true) {
        lock.lock();
        try {
            if (ticketNum >= 10) break;
            System.out.println(getName() + "正在卖第" + ++ticketNum + "张票");
        } finally {
            lock.unlock();
        }
    }
}
```
> 公平锁会带来额外的线程切换开销，性能比非公平差，所以`synchronized`默认是非公平。

# 总结记忆点
1. 无sleep：线程释放锁后立刻再次抢锁，非公平锁下当前线程极易持续抢到锁，看起来单线程执行；
2. 加sleep：线程主动阻塞，让出CPU，其他线程才有机会抢到锁；
3. synchronized是**非公平内置锁**；释放锁不会自动切换线程，切换依赖操作系统的时间片调度。
*/
public class Demo9 {
    public static void main(String[] args) {
        SaleTicketThread3 saleTicketThread1 = new SaleTicketThread3();
        SaleTicketThread3 saleTicketThread2 = new SaleTicketThread3();
        SaleTicketThread3 saleTicketThread3 = new SaleTicketThread3();

        saleTicketThread1.setName("电影院窗口1");
        saleTicketThread2.setName("电影院窗口2");
        saleTicketThread3.setName("电影院窗口3");

        saleTicketThread1.start();
        saleTicketThread2.start();
        saleTicketThread3.start();
    }
}

class SaleTicketThread3 extends Thread {
    static int ticketNum = 0;

    @Override
    public void run() {
        while (true) {
            synchronized (SaleTicketThread3.class) {
                if (ticketNum >= 10) {
                    break;
                }

                System.out.println(STR."\{getName()}正在卖第\{++ticketNum}张票");
            }

            //try {
            //    Thread.sleep(100);
            //} catch (InterruptedException e) {
            //    throw new RuntimeException(e);
            //}
        }
    }
}
