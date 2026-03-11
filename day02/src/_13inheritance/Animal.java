package _13inheritance;

// 父类
public class Animal {
    protected String name;
    protected int age;

    public Animal() {
        System.out.println("父类无参构造函数");
    }
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void eat() {
        System.out.println(name + "正在吃东西");
    }

    public void sleep() {
        System.out.println(name + "正在睡觉");
    }

    public void makeSound() {
        System.out.println("动物发出声音");
    }
}


