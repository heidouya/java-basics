# Java多线程安全问题（影院售票案例，带详细注释）

## 场景说明

电影院某一场电影，总票数：**10张**。
开启多个售票窗口（多个线程），窗口独立售票，**所有窗口共用同一份剩余票数**。

- 窗口1：卖出1张票
- 窗口2：卖出1张票
- 窗口3：卖出1张票

> 业务预期：每卖出一张，剩余票数减1；票数为0时停止售票。

### 线程安全问题产生的3个必要条件（必须同时满足）

1. **共享资源**：多个售票窗口（线程）读取修改同一个变量 `ticketNum`（剩余票数）
2. **资源可修改**：变量可读可写，不是只读常量
3. **操作非原子**：售票逻辑 = 读取剩余票数 → 判断票数>0 → 票数-1 → 返回出票。这是多步操作，CPU随时可以中断线程，切换到别的窗口。

### 并发出错流程演示

1. 窗口1读取票数：10
2. CPU切换线程，窗口2读取票数：10（窗口1还没把新票数写回去）
3. 窗口1判断：有票，票数 = 10 - 1 = 9，写回内存
4. 窗口2判断：读到的旧值是10，认为有票，票数 = 10 - 1 =9，写回内存

✅预期：卖出去2张，剩余8张
❌实际：剩余9张。**少扣了票，同一张票被两个窗口卖出，出现超卖！**

> 超卖就是典型的线程安全问题，非常贴合真实业务。

---

## 一、线程不安全版本代码（可直接运行，会出现超卖）

```java
/**
 * 影院售票 - 线程不安全版本
 * 多个售票窗口，共享同一场电影剩余票数，会发生超卖
 */
public class CinemaTicketUnsafe {
    // 共享资源：本场电影剩余票数，多个窗口共用
    private int ticketNum;

    // 构造方法：初始化总票数
    public CinemaTicketUnsafe(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    /**
     * 售票方法
     * @param windowName 窗口名称
     */
    public void sellTicket(String windowName) {
        // 1.读取票数 2.判断是否有票 3.票数减1 4.写回
        // 这几步不是原子操作，线程会被CPU打断
        if (ticketNum > 0) {
            try {
                // 模拟出票耗时：查数据库、打印电影票，放大并发冲突，更容易复现超卖
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 票数-1，读改写三步，非原子！
            ticketNum--;
            System.out.println(windowName + "售出1张票，剩余票数：" + ticketNum);
        } else {
            System.out.println(windowName + "：票已售罄！");
        }
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public static void main(String[] args) {
        // 共享的票源，一共10张票
        CinemaTicketUnsafe ticket = new CinemaTicketUnsafe(10);

        // 3个售票窗口，相当于3个线程
        Thread window1 = new Thread(() -> {
            // 循环售票，直到卖完
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口1");
            }
        });
        Thread window2 = new Thread(() -> {
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口2");
            }
        });
        Thread window3 = new Thread(() -> {
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口3");
            }
        });

        // 开启3个窗口开始售票
        window1.start();
        window2.start();
        window3.start();
    }
}
```

> 运行现象：会出现**超卖**，比如剩余票数变成负数，多个窗口同时卖出最后一张票。
> 示例输出：

```
窗口1售出1张票，剩余票数：9
窗口2售出1张票，剩余票数：9
窗口3售出1张票，剩余票数：8
......
窗口1售出1张票，剩余票数：0
窗口2售出1张票，剩余票数：-1
```

---

## 二、4种解决方案（全部带详细注释）

### 方案1：synchronized 同步锁（隐式悲观锁，JVM自动管理）

> 生活理解：售票操作需要拿到【票本锁】。窗口必须拿到锁才能执行售票整套逻辑；窗口操作完毕，锁自动释放。同一时刻**只能有一个窗口操作票本**。
> 两种写法：同步代码块（推荐，锁粒度可控）、同步方法。

```java
/**
 * synchronized同步代码块版本，解决售票超卖
 */
public class CinemaTicketSync {
    // 共享剩余票数
    private int ticketNum;

    public CinemaTicketSync(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    public void sellTicket(String windowName) {
        // synchronized(锁对象)：多个窗口竞争同一个锁对象this（当前票本对象）
        synchronized (this) {
            if (ticketNum > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticketNum--;
                System.out.println(windowName + "售出1张票，剩余票数：" + ticketNum);
            } else {
                System.out.println(windowName + "：票已售罄！");
            }
        }
        // 代码块结束，JVM自动释放锁；就算代码抛出异常，锁也会自动释放
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public static void main(String[] args) {
        CinemaTicketSync ticket = new CinemaTicketSync(10);

        Thread window1 = new Thread(() -> {
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口1");
            }
        });
        Thread window2 = new Thread(() -> {
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口2");
            }
        });
        Thread window3 = new Thread(() -> {
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口3");
            }
        });

        window1.start();
        window2.start();
        window3.start();
    }
}
```

同步方法写法（等价于`synchronized(this)`）

同步方法格式：
修饰符 synchronized 返回值类型 方法名(方法参数) {// 方法体}

```java
// 在方法上加synchronized
public synchronized void sellTicket(String windowName) {
    // 售票业务代码
}
```
同步方法的特点：
1. 同步方法是锁住方法里面所有的代码
2. 锁对象不能自己指定，修饰非静态方法锁对象是this，修饰静态方法，锁对象是当前类的字节码文件对象

✅ synchronized特点

1. **可重入锁**：同一个窗口（线程）可以多次拿到同一把锁，不会自己把自己堵死
2. 自动上锁、自动释放锁，代码简单
3. 默认是非公平锁：窗口抢锁不一定按先来后到顺序

---

### 方案2：ReentrantLock 显式锁（代码层面悲观锁）

> 生活理解：手动锁。窗口自己手动开锁`lock()`，售票完成手动关锁`unlock()`。
> ⚠️必须写在finally中释放锁！如果代码发生异常，没有释放锁，其他窗口永久拿不到锁，死锁。

```Java
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock显式锁版本，解决售票超卖
 */
public class CinemaTicketLock {
    private int ticketNum;
    // 创建可重入锁对象，所有窗口共用这一把锁
    private final ReentrantLock lock = new ReentrantLock();

    public CinemaTicketLock(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    public void sellTicket(String windowName) {
        // 手动上锁，拿不到锁的窗口阻塞等待
        lock.lock();
        try {
            // 临界区：保护共享票数的业务代码
            if (ticketNum > 0) {
                Thread.sleep(100);
                ticketNum--;
                System.out.println(windowName + "售出1张票，剩余票数：" + ticketNum);
            } else {
                System.out.println(windowName + "：票已售罄！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // finally：无论正常执行还是抛出异常，一定会执行，保证锁释放
            lock.unlock();
        }
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public static void main(String[] args) {
        CinemaTicketLock ticket = new CinemaTicketLock(10);

        Thread window1 = new Thread(() -> {
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口1");
            }
        });
        Thread window2 = new Thread(() -> {
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口2");
            }
        });
        Thread window3 = new Thread(() -> {
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口3");
            }
        });

        window1.start();
        window2.start();
        window3.start();
    }
}
```

✅ ReentrantLock额外能力

- `new ReentrantLock(true)`：开启公平锁，窗口排队，先来先服务
- `tryLock()`：尝试拿锁，拿不到可以不阻塞，直接返回，可以做业务降级
- `lockInterruptibly()`：等待锁的时候，可以响应中断

---

### 方案3：AtomicInteger CAS原子类（乐观无锁方案）

> 生活理解：窗口售票不加锁。先读取当前票数，准备出票的时候，核对一下票本上的值还是不是刚才读到的值。
> 如果没变：出票成功；如果已经被别的窗口改了：放弃本次操作，重新读取票数，再次尝试售票。
> CAS：Compare And Swap，比较并交换。适合**单个数字变量**的增减，并发竞争不激烈场景。

```java
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger CAS原子类版本，解决售票超卖
 */
public class CinemaTicketAtomic {
    // 原子整数，底层CAS，保证单变量操作原子性
    private AtomicInteger ticketNum;

    public CinemaTicketAtomic(int ticketNum) {
        this.ticketNum = new AtomicInteger(ticketNum);
    }

    public void sellTicket(String windowName) {
        // 自旋循环：CAS失败就不断重试售票
        while (true) {
            // 获取当前内存中的票数旧值
            int oldTicket = ticketNum.get();
            if (oldTicket <= 0) {
                System.out.println(windowName + "：票已售罄！");
                return;
            }
            // CAS：如果内存票数 == oldTicket，则更新为 oldTicket -1；否则更新失败
            boolean success = ticketNum.compareAndSet(oldTicket, oldTicket - 1);
            if (success) {
                System.out.println(windowName + "售出1张票，剩余票数：" + ticketNum.get());
                break;
            }
            // CAS失败：其他窗口抢先卖了票，循环重试
        }
    }

    public int getTicketNum() {
        return ticketNum.get();
    }

    public static void main(String[] args) {
        CinemaTicketAtomic ticket = new CinemaTicketAtomic(10);

        Thread window1 = new Thread(() -> {
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口1");
            }
        });
        Thread window2 = new Thread(() -> {
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口2");
            }
        });
        Thread window3 = new Thread(() -> {
            while (ticket.getTicketNum() > 0) {
                ticket.sellTicket("窗口3");
            }
        });

        window1.start();
        window2.start();
        window3.start();
    }
}
```

⚠️ CAS缺点

1. 高并发大量竞争时，自旋循环不断重试，消耗CPU
2. ABA问题（可以使用AtomicStampedReference带版本号解决）
3. **只能保证单个变量原子性**，无法保护多个变量的复合业务逻辑

---

### 方案4：ThreadLocal 数据隔离（不是锁！不能解决售票超卖）

> 生活理解：每个售票窗口，单独有一份自己的票本副本，窗口之间票本互不干扰。
> ❗重点：**不适合售票场景！** 因为每个窗口看到的票数是自己本地副本，不是全局真实票数，无法共享票源。
> 使用场景：线程私有数据，例如保存当前登录用户信息。

```java
/**
 * ThreadLocal演示，不能用来解决售票超卖
 */
public class CinemaTicketThreadLocal {
    // ThreadLocal：每个线程拥有独立副本
    private static ThreadLocal<Integer> ticketNum = ThreadLocal.withInitial(() -> 10);

    public void sellTicket(String windowName) {
        int currentTicket = ticketNum.get();
        if (currentTicket > 0) {
            currentTicket--;
            ticketNum.set(currentTicket);
            System.out.println(windowName + "售出1张票，当前窗口副本票数：" + ticketNum.get());
        } else {
            System.out.println(windowName + "：票已售罄！");
        }
    }

    public static void main(String[] args) {
        CinemaTicketThreadLocal ticket = new CinemaTicketThreadLocal();

        Thread window1 = new Thread(() -> {
            while (ticketNum.get() > 0) {
                ticket.sellTicket("窗口1");
            }
        });
        Thread window2 = new Thread(() -> {
            while (ticketNum.get() > 0) {
                ticket.sellTicket("窗口2");
            }
        });

        window1.start();
        window2.start();
    }
}
```

运行结果：窗口1卖10张，窗口2也卖10张，各自独立，完全不符合真实售票业务。

---

# 三、volatile关键字补充（面试高频坑点）

volatile **不能保证原子性！只能保证可见性、禁止指令重排**。

- 可见性：一个窗口修改volatile票数，其他窗口立刻读到最新值。
- 但是 `ticketNum--` 依旧是 读-改-写 三步，无法保证原子性。
  👉 只给ticketNum加volatile修饰，售票代码依旧会超卖！

volatile适用场景：**状态标记开关**，示例：

```java
public class VolatileFlagDemo {
    // volatile修饰停止标记
    private static volatile boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (!stop) {
                // 模拟持续售票任务
            }
            System.out.println("售票任务收到停止信号");
        }).start();

        Thread.sleep(1000);
        stop = true;
    }
}
```

# 四、方案选型总结

| 方案 | 锁类型 | 适用场景 |
| --- | --- | --- |
| synchronized | 悲观锁 | 中等并发，业务逻辑复杂，代码简单稳定 |
| ReentrantLock | 悲观锁 | 需要公平锁、tryLock、中断等待锁等高级特性 |
| Atomic原子类 | CAS乐观锁 | 单个数字增减，并发竞争不大，追求高性能 |
| ThreadLocal | 数据隔离 | 线程私有数据，不共享资源，**不能解决超卖** |