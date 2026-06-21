package _01generics;
/*
# Java 泛型（Generics）简要介绍

## 什么是泛型？

泛型是 JDK 5 引入的特性，**允许在定义类、接口、方法时使用类型参数（Type Parameter）**，将类型抽象化，让代码可以操作不同类型的对象，同时**在编译期保证类型安全**。

---

## 为什么需要泛型？

来看一个没有泛型的问题：

```java
List list = new ArrayList();
list.add("hello");
list.add(123);           // 编译通过，运行隐患

String s = (String) list.get(1);  // 运行时 ClassCastException！
```

用泛型后：

```java
List<String> list = new ArrayList<>();
list.add("hello");
list.add(123);           // 编译报错，提前拦截错误

String s = list.get(1);  // 不需要强转
```

**核心好处：**
1. **类型安全** —— 编译期检查类型，避免运行时异常
2. **消除强制转换** —— 代码更简洁、可读性更强
3. **代码复用** —— 一套代码可处理多种类型

---

## 泛型的基本用法

### 1. 泛型类

```java
public class Box<T> {           // T 是类型参数
    private T data;

    public void set(T data) { this.data = data; }
    public T get() { return data; }
}

// 使用
Box<String> box = new Box<>();
box.set("hello");
String s = box.get();           // 无需强转
```

常见的类型参数命名约定：
| 参数 | 含义 |
|------|------|
| `T` | Type（任意类型） |
| `E` | Element（集合元素，如 List<E>） |
| `K` / `V` | Key / Value（键值对） |
| `N` | Number（数值） |
| `?` | 通配符（未知类型） |

### 2. 泛型方法

```java
public class Utils {
    // 在方法返回类型前声明类型参数
    public static <T> T getMiddle(T... arr) {
        return arr[arr.length / 2];
    }
}

// 使用（编译器自动推断类型）
String mid = Utils.getMiddle("a", "b", "c");
```

### 3. 泛型接口

```java
public interface Comparable<T> {
    int compareTo(T o);
}

public class Person implements Comparable<Person> {
    private int age;

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.age, o.age);
    }
}
```

---

## 类型通配符 `?`

### 上界通配符 `extends`

```java
// 接收 Number 及其子类的集合
public static double sum(List<? extends Number> list) {
    double total = 0;
    for (Number n : list) total += n.doubleValue();
    return total;
}

// 可传入 List<Integer>、List<Double> 等
```

### 下界通配符 `super`

```java
// 接收 Integer 及其父类的集合
public static void addNumbers(List<? super Integer> list) {
    list.add(1);   // 可以写入
    list.add(2);
}
```

---

## 类型擦除（Type Erasure）

Java 的泛型是在**编译期实现**的——**运行时泛型信息会被擦除**（替换为原始类型或上界）。

```java
// 编译后
List<String> list1 = new ArrayList<>();
List<Integer> list2 = new ArrayList<>();

// 运行时都是 raw type（原始类型）
System.out.println(list1.getClass() == list2.getClass()); // true
```

这意味着：
- 不能 `new T()`（无法实例化类型参数）
- 不能 `new T[10]`（无法创建泛型数组）
- `instanceof` 不能用于泛型类型（如 `obj instanceof List<String>` 不允许）

---

## 常见限制与注意事项

| 限制 | 原因 |
|------|------|
| `static` 字段不能用类型参数 | 类型擦除导致所有实例共享同个字段 |
| 不能 `new T()` | 运行时不知道具体类型 |
| 不能 `new T[10]` | 泛型数组存在类型安全问题 |
| 不能与基本类型一起使用 | 必须用包装类：`List<Integer>` 而非 `List<int>` |
| 异常类不能是泛型 | `catch` 语句无法处理泛型异常 |

---

## 核心要点对比

| 特性 | 泛型类 | 泛型接口 | 泛型方法 |
|------|--------|----------|----------|
| 类型参数声明位置 | 类名后 `<T>` | 接口名后 `<T>` | 方法返回类型前 `<T>` |
| 类型确定时机 | 创建实例时 | 实现接口时 | 调用方法时 |
| 适用范围 | 整个类 | 整个接口 | 仅该方法 |

**泛型的核心价值**：类型安全 + 代码复用——在编译期就检查类型错误，避免运行时 `ClassCastException`，同时减少重复代码。

## 一句话总结

> **泛型 = 类型的模板化**，让代码在不牺牲类型安全的前提下实现通用性，是 Java 集合框架的设计基石，也是现代 Java 编程的基本功。
*/
public class Demo1 {
    public static void main(String[] args) {
        String mid = getMiddle(new String[]{"a", "b", "c"});
        System.out.println(mid);

        printPair("name", 42);
    }

    // <T> 是方法级别的类型参数，与类无关
    // 注意 静态方法不能使用类的泛型参数
    public static <T> T getMiddle(T[] array) {
        return array[array.length / 2];
    }

    // 多个类型参数
    public static <K, V> void printPair(K key, V value) {
        System.out.println(STR."\{key} = \{value}");
    }
}
