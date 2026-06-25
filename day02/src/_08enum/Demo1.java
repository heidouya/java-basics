package _08enum;

import java.util.Arrays;

/*
枚举（Enum） 是 Java 5 引入的特殊数据类型，用于定义一组固定的常量。

枚举类的定义：使用 enum 关键字定义枚举类。

枚举本质是一个 final 类，默认继承自 java.lang.Enum 父类，不能被继承、不能手动 new 实例化；
枚举中定义的每一个常量，都是该枚举类 public static final 的实例（全局唯一、不可修改）。

枚举类的特点：
1. 枚举类的每个常量之间用逗号分隔
2. 枚举类的每个常量默认是 public static final 的
3. 枚举类的构造方法默认是 private 的
4. 枚举类可以实现接口
5. 枚举类可以有构造方法、成员变量、成员方法
6. 枚举类可以有静态代码块、实例代码块、抽象方法、默认方法、静态方法、实例方法

核心优势：
1. 类型安全：只能使用枚举内定义的常量，杜绝传入非法值；
2. 可读性高：用语义化名称代替魔法数字 / 字符串，代码更易懂；
3. 简洁高效：自带工具方法，无需手动定义常量组；
4. 线程安全：枚举实例全局唯一，天生适合单例模式。
*/
public class Demo1 {
    public static void main(String[] args) {
        Season season = Season.SPRING;
        System.out.println(season);

        System.out.println("--------------------------------------------------");

        OrderStatus status = OrderStatus.PENDING;
        System.out.println(status.getDesc());

        System.out.println("--------------------------------------------------");

        // 3. 自带常用方法（父类 Enum 提供）
        // 3.1 values()：返回枚举所有常量的数组；
        // 3.2 valueOf(String name)：根据名称获取对应的枚举常量；
        // 3.3 ordinal()：获取常量的定义序号（从 0 开始）。
        // 获取所有季节
        Season[] seasons = Season.values();
        // 根据名称获取常量
        Season summer = Season.valueOf("SUMMER");
        // 获取序号
        System.out.println(Season.SPRING.ordinal()); // 输出 0
        System.out.println(summer); // 输出 SUMMER
        System.out.println(Arrays.toString(seasons)); // 输出 [SPRING, SUMMER, AUTUMN, WINTER]
    }
}
