package _01thread;
/*
# main 线程会比其他线程先执行完吗
**不一定！main线程只是最先被JVM创建启动，但不代表它一定先跑完，也不代表它的代码每一行都优先执行。**

## 1. JVM启动流程
JVM启动 → 创建**main线程**，执行main()方法里面的代码。
当你在main里面执行 `t.start()`：
- `start()` 只是告诉JVM去新建子线程，**子线程什么时候抢到CPU，由操作系统调度决定**
- `start()` 调用完成之后，main线程和子线程**进入公平竞争CPU时间片的状态**，谁先跑看CPU调度

### 示例代码
```java
public class MainThreadTest {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("子线程执行");
        });
        t.start();
        System.out.println("main线程执行");
    }
}
```
✅ 运行结果**两种都有可能**：
1. main线程先打印：`main线程执行` 再 `子线程执行`（最常见）
2. 少数情况：子线程先打印，再main输出

> 为什么大多时候main先输出？
> `t.start()` 需要操作系统创建线程、分配资源，有一点点开销。main线程执行完start之后，继续往下跑打印，经常抢先一步。但**这只是概率现象，不是保障！**

## 2. 重要区分
1. **代码顺序 ≠ 执行顺序**
`start()` 之后，两个线程并发，执行顺序由操作系统调度，**没有固定先后**。

2. main线程也是普通用户线程
JVM要等**所有用户线程**执行完毕才退出。
> 如果main线程执行完main方法，main线程结束；但只要还有其他用户线程在跑，JVM不会退出。

3. 如果想要固定顺序怎么办？
想让main等子线程执行完，用 `t.join()`
```java
public static void main(String[] args) throws InterruptedException {
    Thread t = new Thread(() -> {
        System.out.println("子线程执行");
    });
    t.start();
    t.join(); // main阻塞，等待子线程结束
    System.out.println("main线程执行");
}
```
加join之后，**顺序固定：子线程先执行，main后执行**

## 面试一句话背诵
JVM最先创建main线程执行main方法，调用子线程`start()`之后，main线程和子线程**由操作系统调度竞争CPU，执行顺序不固定；只是main大概率先输出，不能作为确定性规则。**
*/
public class Demo6 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                // 获取线程名称
                System.out.println(STR."thread线程：\{Thread.currentThread().getName()}");
            }
        },"线程1");

        // 启动线程
        thread.start();

        // 修改线程名称
        thread.setName("thread");

        for (int i = 0; i < 500; i++) {
            // 获取线程名称
            System.out.println(STR."main线程：\{thread.getName()}！");
        }
    }
}
