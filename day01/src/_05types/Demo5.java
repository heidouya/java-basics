package _05types;
/*
为什么不推荐 + 用于字符串拼接呢?

这个问题其实需要**分情况讨论**，Java 编译器对 `+` 做了优化，并非绝对不推荐。

## 核心原因：性能问题

### 1. 在循环中使用 `+` —— **绝对不推荐**
```java
// ❌ 糟糕：每次循环都创建新 StringBuilder/StringBuffer
String result = "";
for (int i = 0; i < 1000; i++) {
    result = result + i;  // 每次循环 new 一个 StringBuilder，再 toString()
}
```

- 每次 `+` 都会隐式创建 `StringBuilder`，调用 `append()`，再 `toString()` 转回 `String`
- 循环 1000 次，就创建了 1000 个临时的 `StringBuilder` 对象和 1000 个 `String` 对象
- **O(n²) 的复杂度**，频繁 GC，性能极差

应改为：
```java
// ✅ 推荐：显式使用 StringBuilder
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);
}
String result = sb.toString();
```

### 2. 一次性拼接少量字符串 —— **没关系**
```java
// ✅ 编译器会优化成 StringBuilder
String msg = "Hello" + ", " + name + "!";
```

Java 编译器（javac）会将这种**编译期已知的少量拼接**自动优化为 `new StringBuilder().append("Hello").append(", ").append(name).append("!").toString()`。**性能损失可以忽略不计**。

## 那为什么还是很多人说不推荐？

| 场景 | 推荐程度 | 原因 |
|------|---------|------|
| 循环内拼接 | ❌ 不推荐 | 性能差，产生大量临时对象 |
| 少量常量拼接 | ✅ 没问题 | 编译器已优化 |
| 格式化需求（插变量） | ⚠️ 可用但不够直观 | 可读性不如 `format()` |
| 需要多行/复杂模板 | ⚠️ 可读性差 | 建议用 `format()` 或 `MessageFormat` |

## 总结
- **循环内拼接 → 用 `StringBuilder`**
- **少量简单拼接 → 用 `+` 完全 OK**，代码更简洁
- **复杂模板格式化 → 用 `String.format()`**，可读性更好
- **性能问题**：每次拼接都会创建一个新的字符串对象，导致大量临时对象的创建和垃圾回收，影响性能。
- **可读性问题**：对于复杂的字符串拼接，使用+号会使得代码变得混乱和难以阅读。
- **功能限制**：+号不能实现字符串的格式化，而String.format()则可以。

网上很多"不推荐 `+`"的说法其实是以偏概全，核心问题是**不要在循环里用**，日常拼接几个字符串完全没有问题。
*/
public class Demo5 {
}
