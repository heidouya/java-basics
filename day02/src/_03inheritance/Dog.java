package _03inheritance;

// 子类使用extends关键字继承父类
public class Dog extends Animal {
    public void bark() {
        System.out.println(name + "汪汪叫");
    }

    @Override  // 注解，推荐加上
    public void makeSound() {
        System.out.println("汪汪汪");  // 重写父类方法
    }

    //@Override
    //public Dog test() throws Throwable {
    //    return new Dog(); // Changed from return "a";
    //}
}