package _01thread;

/*
# 生产者消费者模型（等待唤醒机制）
## 核心概念
生产者：负责**生产数据**，数据满了就等待，不再生产。
消费者：负责**消费数据**，数据空了就等待，不再消费。
等待唤醒机制：线程之间通过 `wait()` / `notify()` / `notifyAll()` 通信，协调生产和消费，防止：
1. 生产过多（队列溢出）
2. 消费空了（取不到数据）

> wait()：释放锁，进入等待状态
> notify()：随机唤醒一个等待线程
> notifyAll()：唤醒所有等待线程（开发推荐，避免信号丢失）

> ⚠️ wait、notify 必须写在 synchronized 同步代码块里面，否则抛异常！

## 业务场景简化
仓库最多存1个产品。
- 仓库有产品 → 生产者等待，通知消费者消费
- 仓库空 → 消费者等待，通知生产者生产

## 完整简洁代码示例
```java
// 仓库
class Store {
    private boolean hasProduct = false; // 是否有商品

    // 生产者放商品
    public synchronized void produce() throws InterruptedException {
        // 有商品，生产者等待
        while (hasProduct) {
            this.wait();
        }
        // 没有商品，开始生产
        System.out.println("生产者：生产1件商品");
        hasProduct = true;
        // 唤醒消费者
        this.notifyAll();
    }

    // 消费者取商品
    public synchronized void consume() throws InterruptedException {
        // 没有商品，消费者等待
        while (!hasProduct) {
            this.wait();
        }
        // 有商品，开始消费
        System.out.println("消费者：消费1件商品");
        hasProduct = false;
        // 唤醒生产者
        this.notifyAll();
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) {
        Store store = new Store();

        // 生产者线程
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    store.produce();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "生产者").start();

        // 消费者线程
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    store.consume();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者").start();
    }
}
```

### 输出效果
```
生产者：生产1件商品
消费者：消费1件商品
生产者：生产1件商品
消费者：消费1件商品
生产者：生产1件商品
消费者：消费1件商品
生产者：生产1件商品
消费者：消费1件商品
生产者：生产1件商品
消费者：消费1件商品
```
生产、消费交替执行，不会连续生产，也不会连续消费。

## ✅ 重点考点：为什么用 while 判断，不用 if？
> wait被唤醒之后，**不会重新获取锁再判断条件**，直接往下执行。
> 如果多生产者多消费者场景，if会出现**虚假唤醒**，导致逻辑错乱。
> 规范写法：`while(条件){wait();}`

## 两个版本对比
1. `synchronized + wait + notifyAll`：传统等待唤醒（上面例子）
2. `ReentrantLock + Condition`：Lock版本，可以分开唤醒生产者、消费者，粒度更细

## 一句话总结面试版
生产者消费者模型是线程间通信模型，生产者生产，消费者消费；通过等待唤醒控制速率，**必须while循环判断条件防止虚假唤醒**。
*/
public class Demo13 {
    public static void main(String[] args) {
        Store store = new Store();
        // 生产者线程
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    store.produce();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "生产者").start();
        
        // 消费者线程
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    store.consume();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者").start();
    }
}

class Store {
    private boolean hasProduct = false; // 是否有商品

    // 生产者放商品
    public synchronized void produce() throws InterruptedException {
        // 有商品，生产者等待
        while (hasProduct) {
            this.wait();
        }
        // 没有商品，开始生产
        System.out.println("生产者：生产1件商品");
        hasProduct = true;
        // 唤醒消费者
        this.notifyAll();
    }

    // 消费者取商品
    public synchronized void consume() throws InterruptedException {
        // 没有商品，消费者等待
        while (!hasProduct) {
            this.wait();
        }
        // 有商品，开始消费
        System.out.println("消费者：消费1件商品");
        hasProduct = false;
        // 唤醒生产者
        this.notifyAll();
    }
}
