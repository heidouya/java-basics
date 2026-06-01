package _05types;

/*
## Java 中的 `null`

`null` 是 Java 中的一个特殊**字面量**，表示引用类型变量**不指向任何对象**。

### 基本特性

- **只能赋给引用类型**（对象、数组、String 等），不能赋给基本类型（`int`、`boolean` 等）
- **默认值**：类中未初始化的引用类型字段默认值为 `null`
- `null` 本身没有类型，但可以转型为任何引用类型：`(String) null`

### 常见错误：NullPointerException

```java
String s = null;
s.length();   // ❌ 抛出 NullPointerException（简称 NPE）
```

贸然调用 `.` 的代码中，只要前面的引用为 `null`，就会触发 NPE。这是 Java 中最常见的运行时异常。

### 如何安全处理 null

```java
// 1. 显式判空
if (str != null) {
    System.out.println(str.length());
}

// 2. 三元运算符
int len = (str == null) ? 0 : str.length();

// 3. 使用 Objects 工具类（Java 7+）
int len = Objects.requireNonNull(str, "str 不能为 null").length();  // 仍会抛 NPE，但提示更明确
// 或提供默认值
int len = Objects.toString(str, "默认值");

// 4. Optional 类（Java 8+）
Optional<String> opt = Optional.ofNullable(str);
int len = opt.map(String::length).orElse(0);
```

### 常见陷阱

```java
// 陷阱1：== 判空正确，equals() 会抛 NPE
String s = null;
s.equals("abc");   // ❌ NPE
"abc".equals(s);   // ✅ false（推荐写法）

// 陷阱2：包装类型自动拆箱
Integer num = null;
int n = num;       // ❌ NPE（自动拆箱时抛出）

// 陷阱3：数组元素默认值
int[] arr = new int[3];      // 基本类型数组，默认 0
String[] strs = new String[3]; // 引用类型数组，默认 null
strs[0].length();            // ❌ NPE
```

### 小结

| 要点 | 说明 |
|------|------|
| 本质 | 引用不指向任何对象的"空指针" |
| 避免 NPE | 访问前判空，多用 `Optional` 和 `Objects` 工具类 |
| 方法调用 | `"常量".equals(var)` 优于 `var.equals("常量")` |
| 自动拆箱 | 小心包装类型为 `null` 时赋值给基本类型 |
*/
public class Demo5 {
    public static void main(String[] args) {
        String s = null;
        System.out.println(s.length());
    }
}
