package _01oop;
/*
构造方法是一种特殊的方法，用于创建对象时初始化对象。它在 new 关键字创建对象时自动调用。

构造方法的三个规矩：
1. 方法名必须与类名完全一致
2. 没有返回值（连 void 也不能写）
3. 通过 new 关键字调用，不能手动调用
*/
public class Person {
    // 成员变量
    String name;
    int age;

    // 构造方法
    Person() {}
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 成员方法
    public void sayHi() {
        //
        System.out.println("Hi，我是" + name + "今年" + age + "岁。");
    }
}
