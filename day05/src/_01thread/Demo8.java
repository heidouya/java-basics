package _01thread;
/*
# sleep放在同步代码块内存在的问题

> 关键点：`sleep()` 写在了 `synchronized` 同步块**内部**！

## 拆解这段代码
```java
public void run() {
    while (true) {
        synchronized (SaleTicketThread.class) { // 进入：拿到【类锁】
            if (ticketNum < 10) {
                try {
                    Thread.sleep(100); // ✦ 在同步块里面sleep！不释放锁！
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(getName() + "正在卖第" + ++ticketNum + "张票");
            } else {
                break; // 跳出同步块，释放锁
            }
        } // 同步块结束：释放锁
    }
}
```

执行时序（假设线程窗口1抢到锁）：
1. 窗口1：进入 `synchronized`，拿到类锁
2. 判断 `ticketNum <10` → true
3. **sleep(100)，在同步块内休眠，锁不释放！窗口2、3只能堵在synchronized门口等待**
4. 休眠结束，打印出票，ticketNum+1
5. **同步块代码执行完毕，走到}`，释放锁**
6. 回到外层while循环，**立刻再次进入 synchronized，又抢到锁**
> 因为这个线程是活跃的，大概率会再次抢占到锁，继续卖下一张票。
> 整个过程，几乎不会给其他线程机会。

> 简单一句话：**同步块里面的sleep，抱着锁睡觉！**

## ✅ 想要「卖一张票就释放锁，多个窗口轮流抢」的写法
把 `sleep` 挪到 **synchronized 外面**
```java
public void run() {
    while (true) {
        synchronized (SaleTicketThread.class) {
            if (ticketNum >= 10) {
                break;
            }
            System.out.println(getName() + "正在卖第" + ++ticketNum + "张票");
        }
        // sleep放到同步块外面！释放锁之后再休眠
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
```
执行流程：
1. 线程拿到锁 → 判断票数 → 出票
2. 退出 `synchronized` → **释放锁！其他线程可以来争抢**
3. 当前线程 sleep(100)，休眠，不持有锁
4. 休眠结束，回到while，再次参与锁竞争

这时多个线程就会**交替卖票**，达到多窗口售票效果。

## 两个重点区分记忆
1. `Thread.sleep()`：**不释放监视器锁**，在哪调用，就抱着锁睡。
2. `obj.wait()`：**释放监视器锁**，等待别的线程notify唤醒。
*/
public class Demo8 {
    public static void main(String[] args) {
        SaleTicketThread2 saleTicketThread1 = new SaleTicketThread2();
        SaleTicketThread2 saleTicketThread2 = new SaleTicketThread2();
        SaleTicketThread2 saleTicketThread3 = new SaleTicketThread2();

        saleTicketThread1.setName("电影院窗口1");
        saleTicketThread2.setName("电影院窗口2");
        saleTicketThread3.setName("电影院窗口3");

        saleTicketThread1.start();
        saleTicketThread2.start();
        saleTicketThread3.start();
    }
}

class SaleTicketThread2 extends Thread {
    static int ticketNum = 0;

    @Override
    public void run() {
        while (true) {
            synchronized (SaleTicketThread2.class) {
                if (ticketNum >= 10) {
                    break;
                }

                //try {
                //    Thread.sleep(100);
                //} catch (InterruptedException e) {
                //    throw new RuntimeException(e);
                //}

                System.out.println(STR."\{getName()}正在卖第\{++ticketNum}张票");
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
