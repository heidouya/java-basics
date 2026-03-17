package _14polymorphism;

/*
    Java中多态的实现原理：多态的实现基于动态绑定（也叫后期绑定或运行时绑定），即在运行时根据对象的实际类型来确定调用哪个方法，而不是在编译时。Java 多态的底层依赖方法表（Method Table）+ 动态绑定（动态方法分派），核心逻辑是：
    1. 编译期：编译器只认「父类引用的类型」，检查该类型是否有要调用的方法（保证语法合法）；
    2. 运行期：JVM 根据「对象的实际类型」，通过方法表找到子类重写后的方法并执行（动态绑定）。

    简要分析：
    1. 方法表（Method Table）—— 每个类的 “方法字典”
        JVM 会为每个类生成一张方法表，记录该类所有可调用的方法（包括继承自父类的）：
        1. 父类（如HarmonyDevice）的方法表：包含powerOn()（父类实现）；
        2.子类（如HarmonyPhone）的方法表：先继承父类方法表，再用自己重写的powerOn()替换父类的同名方法。
    2. 动态绑定（运行时才确定执行哪个方法）
        1. 编译时绑定（静态绑定）：比如静态方法、私有方法、final 方法，编译期就确定执行哪个类的方法，无多态；
        2. 运行时绑定（动态绑定）：普通成员方法，运行期才根据对象实际类型找方法，这是多态的核心。

    举例说明：
    1. 方法表
    ```java
    class Animal {
        public void makeSound() { }
        public void eat() { }
    }

    class Dog extends Animal {
        public void makeSound() { }  // 重写方法
        public void bark() { }       // 子类特有方法
    }
    ```
    JVM为每个类维护一个方法表：
    Animal类的方法表：
    [0] Object.toString()
    [1] Object.hashCode()
    [2] Object.equals()
    [3] Animal.makeSound()
    [4] Animal.eat()

    Dog类的方法表：
    [0] Object.toString()
    [1] Object.hashCode()
    [2] Object.equals()
    [3] Dog.makeSound()    ← 指向Dog的实现（重写）
    [4] Animal.eat()       ← 继承自Animal
    [5] Dog.bark()         ← 子类特有方法

    2. 方法调用过程
    ```java
    Animal animal = new Dog();
    animal.makeSound();  // 调用哪个方法？
    ```
    3. 执行步骤：
        1. 编译阶段：检查animal的声明类型（Animal），确认makeSound()方法存在
        2. 运行阶段：
            1. JVM通过animal引用找到堆中的实际对象（Dog对象）
            2. 从Dog对象中获取其类的方法表
            3. 在方法表中查找makeSound()的入口地址
            4. 调用该方法（Dog类重写的版本）

    4. 内存布局示意
         栈内存                   堆内存
    +--------+              +----------------+
    | animal |------------->| Dog对象         |
    +--------+              |----------------|
                            | 对象头          |
                            | 包含指向方法表的指针 |
                            +----------------+
                                          |
                                          v
                                    方法表 (Dog类)
                                  +----------------+
                                  | makeSound → Dog实现 |
                                  | eat → Animal实现    |
                                  | bark → Dog实现      |
                                  +----------------+

    静态绑定 vs 动态绑定：
    对比项	            静态绑定	                            动态绑定
    绑定时间	            编译期	                            运行期
    性能	                更快	                                稍有开销
    适用方法	            static方法、private方法、final方法	    普通实例方法
    构造方法	            private方法、final方法	            重写的方法
    依据	                变量声明类型	                        对象实际类型

    性能开销来源：
    1. 方法表查找
    2. 类型检查
    3. 无法内联优化
    但现代JVM做了大量优化：
    1. 内联缓存
    2. 方法内联
    3. 逃逸分析

    问题：
    1. 为什么属性无多态？
    属性是「静态绑定」，编译期就确定取父类 / 子类的属性，运行期不做动态匹配，所以父类引用访问属性始终取父类的；
    2. 为什么静态方法无多态？
    静态方法属于类，存在类的方法区，不进入对象的方法表，调用时直接按引用的类型（而非对象类型）执行；
    3. 方法重写是前提的本质？
    子类重写方法会替换方法表中父类的同名方法，若没重写，JVM 会执行父类方法表中的实现。

    总结
    多态的实现核心：
    1. 方法表：每个类都有方法表，记录方法入口地址
    2. 动态绑定：运行时根据对象类型查找方法表
    3. 虚方法调用：通过invokevirtual指令实现
    4. 继承链：方法表包含从父类继承的方法
    5. 重写覆盖：子类重写的方法替换方法表中的入口

    底层机制：
    1. 对象头包含指向类元数据的指针
    2. 类元数据中包含方法表
    3. JVM通过方法表实现动态分派
    4. 现代JVM通过内联缓存等技术优化性能

    多态底层：编译期静态检查父类方法，运行期通过方法表动态绑定对象实际类型的方法；
    核心依赖：方法表（存储类的方法）+ 动态绑定（运行时找实际方法）；
    关键边界：属性、静态方法是静态绑定，无多态；普通成员方法是动态绑定，支持多态。
*/
public class Demo6 {
    public static void main(String[] args) {
        // 1. 编译看左边，运行看右边
        Animal dog = new Dog();  // 编译时认为是 Animal，运行时是 Dog

        // 2. 可调用方法由左边决定
        dog.makeSound();  // 可以（在Animal中定义）
        // dog.bark();    // 不行（Animal中没有）

        // 3. 实际执行由右边决定
        // 通过方法表动态查找

        // 4. 属性没有多态
        System.out.println(dog.legs);  // 访问 Animal 的 legs，不是 Dog 的
    }
}

class Animal {
    public int legs = 2;
    public void makeSound() {}
}
class Dog extends Animal {
    public int legs = 4;
    @Override
    public void makeSound() {
        System.out.println("汪汪汪，我有" + legs + "腿");
    }
}