package _13inheritance;

/*
    final：final是Java中的一个关键字，表示"不可变的"或"最终的"。它可以用来修饰类、方法、变量，在不同场景下有不同含义。

    final的核心作用：
    1. 定义常量：static final组合创建类常量
    2. 安全控制：防止类被继承、方法被重写
    3. 设计约束：明确哪些是不可变的部分
    4. 性能优化：编译器可以进行优化
    5. 线程安全：final字段在构造后不可变，天然线程安全

    使用建议：
    1. 常量：使用public static final
    2. 工具类：使用final class防止继承
    3. 核心方法：使用final method防止修改
    4. 内部类访问：使用final或effectively final变量
*/
public class Demo4 {
    public static void main(String[] args) {
        Child child = new Child(10);
    }
}
