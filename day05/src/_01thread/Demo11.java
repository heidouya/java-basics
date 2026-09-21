package _01thread;

import java.util.concurrent.locks.ReentrantLock;
/*
# 上一个示例中的代码运行后，为什么会卡住呢？

先看你代码里锁的结构：
```java
while (true) {
    lock.lock();        // 加锁
    if (ticketNum >= 10) break; // 票数够了，直接break跳出循环！

    try {
        Thread.sleep(100);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
    System.out.println(STR."\{getName()}正在卖第\{++ticketNum}张票");
    lock.unlock();  // 解锁写在break后面了！
}
```
> 当 `ticketNum >=10` 执行 `break` 的时候，**直接跳出while循环，没有执行`lock.unlock()`**
> 锁被当前线程拿着，永远不释放 → 其他线程卡在 `lock.lock()` 这里一直等待，程序就卡住停不下来。

## 执行过程还原
1. 假设当前线程拿到锁，`ticketNum ==9`
2. 执行打印，`++ticketNum` → ticketNum变成10，`unlock()`释放锁
3. 下一轮循环，线程抢到锁，`lock.lock()`成功
4. 判断 `ticketNum >=10` → true，执行 `break`
5. **重点：直接跳出while，不会走到lock.unlock()**
✅ 当前线程持有锁，退出run方法，锁永远不释放。
其他两个线程一直阻塞在 `lock.lock()`，无限等待锁，程序不会结束。

> ReentrantLock必须保证**无论正常/异常退出，锁一定释放**，推荐用`try-finally`！

# 修改后的正确代码
```java
import java.util.concurrent.locks.ReentrantLock;

public class Demo10 {
    public static void main(String[] args) {
        SaleTicketThread4 saleTicket1 = new SaleTicketThread4();
        SaleTicketThread4 saleTicket2 = new SaleTicketThread4();
        SaleTicketThread4 saleTicket3 = new SaleTicketThread4();

        saleTicket1.setName("窗口1");
        saleTicket2.setName("窗口2");
        saleTicket3.setName("窗口3");

        saleTicket1.start();
        saleTicket2.start();
        saleTicket3.start();
    }
}

class SaleTicketThread4 extends Thread {
    static int ticketNum = 0;
    static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (ticketNum >= 10) {
                    break; // 在try里面break，finally会执行解锁！
                }
                Thread.sleep(100);
                System.out.println(STR."\{getName()}正在卖第\{++ticketNum}张票");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                // finally：不管代码怎么退出，一定会执行解锁
                lock.unlock();
            }
        }
    }
}
```

## 核心规则（面试常考）
> 使用`ReentrantLock`标准模板：
> ```java
> lock.lock();
> try{
>     // 临界区代码
> }finally{
>     lock.unlock(); // 保证锁一定释放
> }
> ```
> 绝对不要把`unlock()`写在try外面！break、return、异常都会跳过unlock，造成锁泄漏。
*/
public class Demo11 {
    public static void main(String[] args) {
        SaleTicketThread5 saleTicket1 = new SaleTicketThread5();
        SaleTicketThread5 saleTicket2 = new SaleTicketThread5();
        SaleTicketThread5 saleTicket3 = new SaleTicketThread5();

        saleTicket1.setName("窗口1");
        saleTicket2.setName("窗口2");
        saleTicket3.setName("窗口3");

        saleTicket1.start();
        saleTicket2.start();
        saleTicket3.start();
    }
}

class SaleTicketThread5 extends Thread {
    static int ticketNum = 0;
    static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            // sleep放在锁外面：模拟窗口准备、找客户，不占用锁
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            lock.lock();
            try {
                if (ticketNum >= 10) {
                    break;
                }

                // sleep 写在锁里面，锁持有期间休眠，并发性能很差，真实业务尽量缩小锁范围
                // 将 sleep 放到锁外面
                //Thread.sleep(100);

                System.out.println(STR."\{getName()}正在卖第\{++ticketNum}张票");
            } finally {
                // 无论break、正常执行，finally一定解锁
                lock.unlock();
            }
        }
    }
}
