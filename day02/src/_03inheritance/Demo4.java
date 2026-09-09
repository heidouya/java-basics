package _03inheritance;

/*
# Java 中的 `final` 关键字

`final` 用于表示「不可改变」，根据修饰对象的不同，含义略有差异。

## 一、修饰变量（不可重新赋值）

```java
final int MAX = 100;   // 只能赋值一次
```


- **局部变量**：声明后必须且只能赋值一次（可先声明后赋值，即 blank final）。
- **成员变量**：必须在声明处、构造器或初始化块中赋值。
- **静态成员变量**（`static final`）：必须在声明处或静态初始化块中赋值，通常配合 `static` 定义常量（命名全大写）。
- **方法参数**：方法内不能重新赋值。
- **注意**：`final` 限制的是「引用不能变」，不保证对象内容不变。`final` 的数组或集合仍可修改元素内容。

## 二、修饰方法（不可重写）

```java
class Parent {
    final void show() { ... }   // 子类不能重写
}
```


- 被子类继承但无法 override。
- 注意：`final` 方法仍可被 **重载**（overload），只是不能重写（override）。
- **private 方法**本身就是不可重写的，再加 `final` 是冗余的。

## 三、修饰类（不可继承）

```java
final class String { ... }   // 经典例子
```


- 该类不能被任何类继承，所有方法隐式为 final。
- 典型场景：`String`、`Integer` 等包装类、工具类。

## 四、常见使用场景

| 场景 | 说明 |
|------|------|
| 定义常量 | `static final` 编译期常量，可做内联优化 |
| 防止方法被篡改 | 模板方法模式下固定算法骨架 |
| 不可变类设计 | 所有字段 `final` + 无 setter，如 `String` |
| 线程安全 | `final` 字段有 JMM 特殊保证，对象安全发布后其他线程可见 |

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

## 五、与 `finally`、`finalize()` 的区别

| 关键字 | 含义 |
|--------|------|
| `final` | 修饰符，表示不可变 |
| `finally` | try-catch 中的收尾块，总会执行 |
| `finalize()` | `Object` 中的方法，GC 回收前调用，已废弃（Java 9+） |

---

**一句话总结**：`final` 修饰**变量**（值/引用不可变）、**方法**（不可重写）、**类**（不可继承），是 Java 中实现常量、不可变对象和代码约束的重要手段。
*/
public class Demo4 {
    public static void main(String[] args) {
        Child child = new Child(10);
    }
}
