package _13inheritance;

// 被 final 修饰的类不能被继承
/*public final class Parent {
    public void eat() {
        System.out.println("正在吃东西");
    }
}*/

// 被final 修饰的方法不能被重写
public class Parent {
    public final void eat() {
        System.out.println("正在吃东西");
    }
}
