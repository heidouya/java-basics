package _03inheritance;

public class Cat extends Animal {
    public Cat() {
        //super();  // 隐式调用，可以不写
        System.out.println("子类无参构造函数");
    }
    public Cat(String name, int age) {
        // 调用父类构造函数，必须是第一行
        super(name, age);
    }

    public void greet() {
        System.out.println("你好，我叫" + name + "，今年" + age + "岁了");
        super.eat();
    }

    @Override
    public void makeSound() {
        System.out.println("喵喵喵");
    }
}
