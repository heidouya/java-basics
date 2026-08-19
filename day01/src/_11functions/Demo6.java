package _11functions;

/*
方法引用

四种形式：
- 静态方法引用：`类名::静态方法`
- 实例方法引用：`对象::实例方法`（如 `System.out::println`）
- 特定类型任意对象的实例方法：`类名::实例方法`（如 `String::toUpperCase`）
- 构造器引用：`类名::new`

`list.forEach(System.out::println)`，这个 `::` 语法就是**方法引用**。

## 什么是方法引用

方法引用是 **lambda 表达式的一种简写形式**：当 lambda 体只是"调用一个现成的方法"时，可以直接用 `类名或对象 :: 方法名` 来代替。它本质上是同一个东西，只是写起来更简洁。

前提：被引用的方法的参数列表、返回值，要和函数式接口的抽象方法匹配。

看你的代码，三种写法效果完全一样：

```java
// 1. 匿名内部类
list.forEach(new Consumer<Integer>() {
    @Override
    public void accept(Integer integer) {
        System.out.println(integer);
    }
});

// 2. lambda 表达式
list.forEach(i -> System.out.println(i));

// 3. 方法引用（最简洁）
list.forEach(System.out::println);
```


编译器会根据函数式接口的抽象方法签名，自动匹配到 `System.out`（PrintStream 对象）的 `println` 方法。

## 四种形式

### 1. 静态方法引用：`类名::静态方法`

```java
// lambda
list.filter(i -> Math.abs(i) > 10);
// 方法引用
list.filter(Math::abs);
```


### 2. 实例方法引用：`对象::实例方法`（你的 `System.out::println` 就是这种）

```java
Printer printer = new Printer();
list.forEach(i -> printer.print(i));   // lambda
list.forEach(printer::print);          // 方法引用
```


### 3. 特定类型的任意对象的实例方法：`类名::实例方法`

这种比较特殊：lambda 的第一个参数作为方法的调用者，其余参数作为方法的参数。

```java
// lambda：参数 s 是调用者，length() 无参
list.stream().map(s -> s.length());
// 方法引用
list.stream().map(String::length);

// 更明显的例子：compareTo 需要两个参数
// (s1, s2) -> s1.compareTo(s2)  →  String::compareTo
```


### 4. 构造器引用：`类名::new`

```java
// lambda
Supplier<User> s = () -> new User();
// 构造器引用
Supplier<User> s = User::new;
```


## 什么情况下能用

一个简单的判断标准：**lambda 体只有一行，且这一行只是调用某个方法、没有其他逻辑**，就可以换成方法引用。比如 `i -> i % 2 == 0` 这种带运算的就不行，必须写成 lambda。

`System.out::println` 就是把 `i -> System.out.println(i)` 简写成了方法引用，两者完全等价。
*/
public class Demo6 {
}
