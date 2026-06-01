package _05types;
/*
字符串拼接

# Java 中的模板字符串

Java 并没有像 JavaScript（`${}`）、Python（f-string）或 Kotlin 那样的原生模板字符串语法，但有以下几种实现方式：

## 1. 传统字符串拼接（不推荐）
```java
String name = "小明";
int age = 18;
String msg = "我叫" + name + "，今年" + age + "岁。";
```

## 2. `String.format()`（推荐）
```java
String msg = String.format("我叫%s，今年%d岁。", name, age);
```

## 3. `MessageFormat`（适合复杂模板）
```java
String msg = MessageFormat.format("我叫{0}，今年{1}岁。", name, age);
```


## 4. `StringBuilder`（大量拼接时）
```java
String msg = new StringBuilder()
    .append("我叫").append(name)
    .append("，今年").append(age).append("岁。")
    .toString();
```

## 5. Java 21 预览特性：**字符串模板**
Java 21 引入了 `STR` 处理器作为预览特性（JDK 23 仍未转正）：
```java
String msg = STR."我叫\{name}，今年\{age}岁。";
```

- 使用 `STR` 模板处理器
- 内嵌表达式用 `\{ }` 语法
- 目前仍是预览功能，需 `--enable-preview` 开启

---

**实际项目中最常用的是** `String.format()` 和 `MessageFormat`。如果你想要像 JS/Python 那种 `"Hello ${name}"` 的体验，目前 Java 还没有稳定的原生支持，需要等字符串模板特性正式转正。
*/
public class Demo4 {
}
