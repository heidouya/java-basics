package _07interface;

// 2. 类实现接口
// 一个类可以实现多个接口：class 类名 implements 接口1, 接口2
public class Dog implements Animal, Runnable {
    public Dog() {
        //Cannot assign a value to final variable 'TYPE'
        //Animal.TYPE = "";
        System.out.println(STR."狗是\{Animal.TYPE}");
    }

    // 必须重写所有抽象方法
    @Override
    public void eat() {
        System.out.println("狗吃骨头");
    }

    @Override
    public void run() {
        System.out.println("狗四处跑");
    }
}