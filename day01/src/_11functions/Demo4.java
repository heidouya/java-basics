package _11functions;

import java.util.Arrays;

/*
# Java 中的 Lambda 表达式

Lambda 表达式是 Java 8 引入的一个重要特性，本质上是一个**匿名函数**，可以让代码更简洁。

## 基本语法

```java
(参数列表) -> { 方法体 }
```

## 常见形式

```java
// 无参数
() -> System.out.println("Hello")

// 单个参数（可省略括号）
x -> x * 2

// 多个参数
(x, y) -> x + y

// 多条语句需要大括号
(x, y) -> {
    int sum = x + y;
    return sum;
}
```

## 核心前提：函数式接口

Lambda 表达式只能用于**函数式接口**（只有一个抽象方法的接口）：

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

// 使用 Lambda 实现
Calculator add = (a, b) -> a + b;
Calculator multiply = (a, b) -> a * b;

System.out.println(add.calculate(3, 5));       // 8
System.out.println(multiply.calculate(3, 5));  // 15
```

## 实际应用场景

**1. 集合排序**
```java
List<String> names = Arrays.asList("张三", "李四", "王五");

// 传统写法
Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return a.compareTo(b);
    }
});

// Lambda 写法
Collections.sort(names, (a, b) -> a.compareTo(b));
```

**2. 遍历集合**
```java
names.forEach(name -> System.out.println(name));
```

**3. 线程创建**
```java
// 传统写法
new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("线程运行中");
    }
}).start();

// Lambda 写法
new Thread(() -> System.out.println("线程运行中")).start();
```

**4. Stream API 配合使用**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

List<Integer> evenNumbers = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());
// 结果: [2, 4]
```

## Java 内置的常用函数式接口

| 接口 | 方法 | 用途 |
|------|------|------|
| `Predicate<T>` | `boolean test(T t)` | 判断条件 |
| `Function<T,R>` | `R apply(T t)` | 转换数据 |
| `Consumer<T>` | `void accept(T t)` | 消费数据 |
| `Supplier<T>` | `T get()` | 提供数据 |

## 总结

Lambda 表达式的优势：
- **代码更简洁**：省去匿名内部类的模板代码
- **可读性更强**：关注"做什么"而非"怎么写"
- **便于函数式编程**：与 Stream API 配合实现链式操作

简单记忆：Lambda 就是把"接口的实现"用一行箭头函数代替了。
*/
public class Demo4 {
    public static void main(String[] args) {
        // 创建一个字符串列表
        var names = Arrays.asList("张三", "李四", "王五");
        // 遍历列表并打印每个元素
        names.forEach(name -> System.out.println(name));
        // 使用方法引用简化代码
        //names.forEach(System.out::println);
    }
}
