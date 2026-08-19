package _11functions;

/*
# Java 函数式接口简介

**函数式接口**是指**只包含一个抽象方法**的接口。它是 Lambda 表达式的基础——Lambda 本质上就是函数式接口的快速实现。

```java
@FunctionalInterface
public interface MyInterface {
    void sayHello();      // 唯一的抽象方法
}
```


## 关键特点

- **只能有一个抽象方法**，这是核心约束
- `@FunctionalInterface` 注解用于**校验**，如果写了多个抽象方法会编译报错（不写也可以，但建议写上）
- 可以有 `default` 方法、`static` 方法、以及 `Object` 类的公共方法（如 `equals`、`toString`），它们都不算抽象方法

```java
@FunctionalInterface
interface Calculator {
    int calc(int a, int b);                    // 唯一抽象方法

    default void show() { }                    // default 方法，允许
    static void info() { }                     // 静态方法，允许
    boolean equals(Object obj);                // Object 方法，允许
}
```


## JDK 内置的四大核心函数式接口

`java.util.function` 包下提供了大量现成的接口，最常用的四个是：

| 接口 | 方法 | 作用 | 示例 |
|------|------|------|------|
| `Predicate<T>` | `boolean test(T t)` | 判断真假 | 过滤集合 |
| `Function<T, R>` | `R apply(T t)` | 转换：入一种类型出另一种 | 类型转换 |
| `Consumer<T>` | `void accept(T t)` | 消费：接收参数无返回 | 打印元素 |
| `Supplier<T>` | `T get()` | 供给：不接收参数返回一个值 | 工厂方法 |

```java
// Predicate：判断
Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4));          // true

// Function：转换
Function<String, Integer> strToLen = s -> s.length();
System.out.println(strToLen.apply("hello")); // 5

// Consumer：消费
Consumer<String> printer = s -> System.out.println(s);
printer.accept("你好");                       // 你好

// Supplier：供给
Supplier<Double> random = () -> Math.random();
System.out.println(random.get());
```


## 其他常见的函数式接口

- `Runnable`：`void run()` —— 无参无返回，用于创建线程
- `Comparator<T>`：`int compare(T o1, T o2)` —— 排序比较
- `Callable<V>`：`V call()` —— 有返回值的任务
- `BiFunction<T, U, R>`：接收两个参数，返回一个结果

```java
// Runnable 配合 Lambda（最经典的用法之一）
new Thread(() -> System.out.println("线程执行中")).start();

// Comparator 配合 Lambda 排序
list.sort((p1, p2) -> p1.getAge() - p2.getAge());
```


## 和 Lambda 的关系

Lambda 表达式的**目标类型必须是函数式接口**，写法上会自动匹配接口的抽象方法：

```java
// 传统写法：匿名内部类
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("传统写法");
    }
};

// Lambda 写法：简洁得多
Runnable r2 = () -> System.out.println("Lambda写法");
```


## 一句话总结

函数式接口是"**只有一个抽象方法的接口**"，它让 Lambda 表达式和行为参数化（把行为当作参数传递）成为可能，也是 `Stream` 流式编程的基础——像 `filter`、`map`、`forEach` 这些 Stream 方法的参数全都是函数式接口。
*/
public class Demo5 {
}
