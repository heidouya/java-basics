package _01thread;
/*
# sleep () 方法是静态方，怎么知道是让哪个线程休眠的

`Thread.sleep()` 是**静态方法，作用于【当前正在执行这行代码的线程】**，不是作用于你写的某个Thread对象。
> 谁调用这行`Thread.sleep(xxx)`代码，就让谁休眠。不是看前面写的线程对象。

## 1. 为什么会有这个疑惑？
静态方法属于**类本身**，不属于某个实例对象。
```java
Thread t1 = new Thread(() -> {
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {}
});
t1.start();
```
这里`Thread.sleep(1000)`写在`t1`线程的任务代码里，**执行这行代码的线程就是t1**，所以休眠t1。

> ❌ 误区：以为`t1.sleep()`是让t1休眠
> 虽然你可以写`t1.sleep(1000)`，语法不会报错！
> 但**等价于`Thread.sleep()`**，依然是**当前执行代码的线程休眠，不是t1！**
> 这是Java经典坑！静态方法用对象调用只是语法糖，编译器会提示警告。

示例演示这个坑：
```java
public class SleepTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for(int i=0;i<5;i++){
                System.out.println("子线程t1运行");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
        });
        t1.start();

        // 重点！用t1对象调用sleep，但是这行代码写在main主线程里！
        // 休眠的是main主线程，不是t1！
        t1.sleep(2000);
        System.out.println("主线程睡醒了");
    }
}
```
> `t1.sleep(2000)` 等价 `Thread.sleep(2000)`
> 执行这行代码的是main线程 → **主线程休眠，子线程t1继续跑**

## 2. 原理
JVM内部，每个线程都有自己的栈。当代码执行到`Thread.sleep(millis)`这个native方法时：
1. 获取**当前线程栈对应的线程**（`currentThread()`）
2. 通知操作系统把这个线程阻塞休眠指定时间
3. 时间到，回到就绪状态

`Thread.sleep`底层就是拿`currentThread`，所以永远休眠**正在执行该语句的线程**。

## 3. 对比区分（面试高频）
- `Thread.sleep()`：静态，休眠**当前执行代码的线程**，不释放锁
- `t.join()`：实例方法，**主线程阻塞，等待t线程执行完**，作用目标是t对象对应的线程
- `t.interrupt()`：实例方法，给**t这个线程对象**打上中断标记

## 4. 面试一句话背诵
> sleep是静态方法，休眠**执行sleep代码的当前线程**；就算用线程对象调用sleep，语法允许，但依然休眠当前线程，不是该对象代表的线程，开发中禁止使用对象调用sleep。
*/
public class Demo5 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 获取线程名称
            System.out.println(STR."我是：\{Thread.currentThread().getName()}");
        },"线程1");

        // 启动线程
        thread.start();

        // 让主线程休眠5秒
        Thread.sleep(5000);

        // 修改线程名称
        thread.setName("线程2");

        // 获取线程名称
        System.out.println(STR."我是：\{thread.getName()}！");
    }
}
