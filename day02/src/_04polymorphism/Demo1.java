package _04polymorphism;

/*
    多态：多态是面向对象编程的三大特性之一（封装、继承、多态），指同一个行为具有多个不同表现形式或形态的能力。简单说就是：同一个方法调用，由于对象不同可能会产生不同的行为。核心是：父类引用指向子类对象，调用方法时自动执行子类的重写实现。

    多态的三个必要条件：
    1. 继承（或实现接口）
    2. 方法重写
    3. 父类引用指向子类对象

    多态的两种常见形式：
    1. 编译时多态（方法重载）
    2. 运行时多态（方法重写）

    多态中的类型转换：
    1. 向上转型（隐式）
        ```java
        Dog dog = new Dog();
        Animal animal = dog;  // 向上转型，自动完成
        animal.makeSound();   // 可以调用被子类重写的方法
        // animal.bark();     // 不能调用子类特有方法
        ```
    2. 向下转型（显式）
        ```java
        Animal animal = new Dog();
        if (animal instanceof Dog) {  // 先判断类型
            Dog dog = (Dog) animal;   // 向下转型
            dog.bark();               // 可以调用子类特有方法
        }

        // 错误的转型会导致运行时异常
        Animal animal2 = new Cat();
        // Dog dog2 = (Dog) animal2;  // 运行时：ClassCastException
        ```

    避坑：
    1. 多态只针对方法，不针对属性：父类引用访问属性时，始终访问父类的属性（方法才会执行子类重写的）；
    2. 静态方法无多态：静态方法属于类，不是对象，调用时按引用的类型执行，而非对象类型；
    3. 向下转型：若需调用子类专属方法（如手机的takePhoto()），需强转：((HarmonyPhone) device1).takePhoto()。

    多态的优缺点：
                优点	                            缺点
    可替换性：   子类可以替换父类	                性能：动态绑定有一定开销
    可扩展性：   新增子类不影响现有代码	            理解难度：代码执行流程不易追踪
    灵活性：    方法参数使用父类，可以接收任意子类	    类型转换：向下转型可能有风险
    简化接口：   统一处理不同对象	                限制：只能访问父类中定义的方法

    总结
    多态的核心要点：
    1. 实现条件：继承/接口 + 方法重写 + 父类引用指向子类对象
    2. 动态绑定：运行时确定调用哪个方法
    3. 向上转型：自动、安全，但只能调用父类方法
    4. 向下转型：需要强制转换，可能抛出异常
    5. instanceof：类型检查，避免转型错误
    多态的价值：
    1. 简化代码，避免冗余判断：不用写if (device instanceof Phone) { ... } else if (device instanceof Pad) { ... }，父类引用统一调用，自动适配子类逻辑；
    2. 解耦：降低类之间的耦合度
    3. 扩展：易于添加新的子类，如新增 “鸿蒙手表” 子类时，只需重写powerOn()，原有调用代码无需修改（符合 “开闭原则”）；
    4. 复用：统一处理不同类型对象
    5. 灵活：代码更灵活、可维护
*/

// 4. 多态核心：父类引用指向子类对象
public class Demo1 {
    public static void main(String[] args) {
        // 父类引用 → 手机对象
        HarmonyDevice device1 = new HarmonyPhone();
        device1.powerOn(); // 执行手机的开机逻辑

        // 父类引用 → 平板对象
        HarmonyDevice device2 = new HarmonyPad();
        device2.powerOn(); // 执行平板的开机逻辑
    }
}



