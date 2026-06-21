package _01generics;

/*
泛型类     暂做了解即可，后续聊完面向对象，再回来看下就懂啦

在类定义时通过类型参数（如 <T>）声明，使得该类可以操作多种类型的数据，而不必为每种类型单独写一个类

类型参数 T 在实例化时才确定具体类型
*/
public class Demo2 {
    public static void main(String[] args) {
        // 类型参数 T 在实例化时才确定具体类型
        Box<String> strBox = new Box<>();
        strBox.set("hello");

        Box<Integer> intBox = new Box<>();
        intBox.set(100);
    }
}

class Box<T> {
    private T content;

    public void set(T content) {
        this.content = content;
    }

    public T get() {
        return content;
    }
}
