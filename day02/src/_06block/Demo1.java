package _06block;
/*
什么是代码块？代码块是用大括号 {} 包裹的一段代码，可以用于限制变量的作用域，也可以用于实现一些特殊的功能。

代码块分为以下几种：
类型              位置                            执行时机                          常见用途
静态代码块      类体内（方法外），加 static          类加载时执行一次                    初始化静态变量、加载资源
构造代码块      类体内（方法外）                    每次创建对象前（构造器之前执行）       提取多个构造器的公共初始化逻辑
普通代码块      方法内、构造器内、语句中              调用时顺序执行                     分支、循环、局部作用域控制
同步代码块      方法内，加 synchronized (锁对象)     线程执行时                         用于线程同步，确保线程安全

作用域控制：代码块内声明的变量，在块外不可见（减少命名冲突）

执行顺序：
静态代码块 → 构造代码块 → 构造器 → 普通/同步代码块
*/
public class Demo1 {
    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();  // 输出：静态代码块 -> 构造代码块
        demo1.method();

        System.out.println("-------------------------------------------------");

        Demo1 demo2 = new Demo1();  // 输出：构造代码块（静态不再执行）
        demo2.method();
    }

    public Demo1() {
        System.out.println("构造器");
    }

    // 1. 静态代码块（类加载时执行一次）
    static {
        System.out.println("静态代码块");
    }

    // 2. 构造代码块（每次 new 对象前执行）
    {
        System.out.println("构造代码块");
    }

    public void method() {
        // 3. 普通代码块
        {
            int x = 10;  // 只在当前 {} 内有效
            System.out.println("普通代码块");
        }
        // System.out.println(x);  // 编译错误！x 超出作用域

        // 4. 同步代码块
        synchronized (this) {
            System.out.println("同步代码块");
        }
    }
}
