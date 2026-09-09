package _03inheritance;

// final修饰的类，禁止继承
/*
public class Child extends Parent{

}
public class Child extends Parent {
    //final修饰方法 - 禁止重写
    @Override
    public void eat() {
        super.eat();
    }
}
*/

public class Child {
    // final 修饰的成员变量在声明时赋值后，后续不能修改，在构造函数中也不行
    final int age;

    // final变量必须在声明时赋值或在构造函数中赋值
    public Child(int age) {
        // final变量可以在构造函数中赋值
        this.age = age;
    }

    public static void main(String[] args) {
        Child child = new Child(10);
        child.setAge(10);
    }
    public void setAge(int age) {
        // final变量不能被修改
        final int age2 = 1;
        //age2 = 3;
    }
}
