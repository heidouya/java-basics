package _05types;
/*
循环内拼接，用 StringBuilder，通过append方法拼接字符串时，只有一个对象吗，不会重复创建多个对象吗?

## StringBuilder 本身 —— **只创建一个**

```java
StringBuilder sb = new StringBuilder();   // 第1次：创建 StringBuilder 对象
for (int i = 0; i < 1000; i++) {
    sb.append(i);                         // 每次 append 不会创建新 StringBuilder
}
String result = sb.toString();            // 最后创建一个 String
```


- `new StringBuilder()` 只在循环**外面**创建一次
- 每次 `append()` 只是在**同一个** `StringBuilder` 对象内部操作，不会 new 新对象

## 那内部会不会创建其他对象？

### 1. **底层 char 数组会扩容 —— 可能创建新数组**
```java
// StringBuilder 内部大致结构
class StringBuilder {
    char[] value;   // 存储字符
    int count;      // 已用长度
}
```

- 初始容量默认是 **16**
- 当 `append` 的内容超过容量时，会**扩容**：`new char[newCapacity]`，然后把旧数组内容复制过去
- 所以如果循环很大，可能触发多次扩容，创建多个数组对象

**示例：**
```java
StringBuilder sb = new StringBuilder();  // 创建 char[16]
for (int i = 0; i < 10000; i++) {
    sb.append(i);
    // 可能会触发：char[34]、char[70]、char[142]... 多次扩容
}
```


### 2. `append(int)` 内部的临时字符串
```java
sb.append(i);
```

- `append(int)` 内部会把 int 转成字符串，**会创建一个临时的 `String` 对象**（或 char 数组）
- 循环 1000 次，每次 int→String 都会产生一个临时对象

## 对比：用 `+` 在循环里
```java
String s = "";
for (int i = 0; i < 1000; i++) {
    s = s + i;   // 每次：new StringBuilder() + append + toString()
}
```


| 对象类型 | 用 `+` | 用 `StringBuilder` |
|---------|--------|-------------------|
| `StringBuilder` 本身 | 1000 个 | **1 个** |
| 临时 `String`（toString 结果） | 1000 个 | **1 个**（最后的 result） |
| 扩容产生的 `char[]` | 每个 StringBuilder 内部都可能扩容 | 几次扩容 |
| `append(int)` 内部临时 char 数组 | 每次都有 | 每次都有（两者一样） |

## 总结

**`StringBuilder` 并不会完全避免创建临时对象**（int→String 转换、数组扩容都会产生），但相比 `+`：

- **最关键的区别**：`StringBuilder` 只创建一个 `StringBuilder` 对象和一个最终的 `String` 对象，而 `+` 每次循环都创建新的 `StringBuilder` + `String`，**O(n) vs O(n²) 的内存分配**
- 如果你担心扩容，可以在知道大小时**指定初始容量**来避免：
  ```java
  StringBuilder sb = new StringBuilder(10000);  // 预先分配足够容量
  ```

*/
public class Demo8 {
}
