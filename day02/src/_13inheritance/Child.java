package _13inheritance;

// final修饰的类，禁止继承
/*public class Child extends Parent{
}*/

/*
public class Child extends Parent {
    //final修饰方法 - 禁止重写
    @Override
    public void eat() {
        super.eat();
    }
}
*/

public class Child {
    final int age;

    public Child(int age) {
        // final变量可以在构造函数中赋值
        this.age = age;
    }

    public void setAge(int age) {
        //this.age = age;
    }
}
