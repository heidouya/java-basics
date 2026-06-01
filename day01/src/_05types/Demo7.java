package _05types;
/*

StringBuilder是干什么用的呢？创建字符串不是可以用String构造函数吗？

## String 的不可变性

```java
String s = "Hello";
s = s + " World";  // ❌ 并没有修改 "Hello"
```

实际上发生了什么：
1. 原来的 `"Hello"` 对象**没变**，仍然存在内存中
2. 创建了一个**全新的** `"Hello World"` 字符串对象
3. 变量 `s` 指向了这个新对象，旧的 `"Hello"` 被丢弃

每次拼接都产生一个新 String 对象，旧的变成垃圾等待回收。

## StringBuilder 是可变的

```java
StringBuilder sb = new StringBuilder("Hello");
sb.append(" World");   // ✅ 直接在同一个对象内部修改
```

- `StringBuilder` 内部维护一个**可变**的 `char[]` 数组
- `append()` 只是在数组后面追加字符，**不创建新对象**
- 最终调用 `toString()` 一次性生成 String

## 打个比方

| | String | StringBuilder |
|--|--------|--------------|
| 像什么 | 一张写好的**纸** | 一块**白板** |
| 修改 | 只能重新抄一张 | 可以直接擦写 |
| 拼接 | 每次抄一份新的 | 直接往后面写 |

## 那 String 构造函数呢？

```java
String s1 = "Hello";           // 字面量
String s2 = new String("Hello"); // 构造函数
```

- `new String("Hello")` 也是创建了一个**新的 String 对象**，但一旦创建完就不能改了
- String 构造函数的用途不是用来"拼接"，而是从其他类型转换（如 `char[]` `byte[]`）

## 直观对比

```java
// ❌ 用 String 拼接（循环里性能灾难）
String s = "";
for (int i = 0; i < 3; i++) {
    s = s + i;
    // 第1次：创建 "" + "0" → "0"
    // 第2次：创建 "0" + "1" → "01"
    // 第3次：创建 "01" + "2" → "012"
    // 共产生了 3 个新 String 对象
}

// ✅ 用 StringBuilder 拼接
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 3; i++) {
    sb.append(i);  // 始终操作同一个对象
}
String result = sb.toString();  // 只最后创建一次
```

## 总结

- **String** 不可变 → 每次修改都产生新对象 → 适合**不经常修改**的文本
- **StringBuilder** 可变 → 同一个对象内部修改 → 适合**频繁拼接/修改**的场景
- String 构造函数只是创建 String 的**入口之一**，不是用来替代 StringBuilder 的
*/
public class Demo7 {
}
