package _05types;
/*
在Java中，字符串常量池里存放的是地址还是真实的字符串数据呢？

在 Java 中，字符串常量池（String Pool / String Constant Pool）**存放的是 String 对象的引用（地址）**，而不是字符串的原始字符数据（`char[]`）。

具体来说，底层机制是这样的：

### 1. 字符串常量池的位置（JDK 7+）
- **JDK 6 及之前**：常量池位于方法区（永久代 PermGen），存放的是 **String 对象实例**（包含 `char[]` 数据）。
- **JDK 7 及之后**：常量池移到了**堆内存**中，但池中**只存储引用（地址）**，真正的字符串字符数据（`char[]`）作为普通对象存在于堆的其他区域。

### 2. 实际存储结构
当你写 `String s = "hello";` 时：
1. JVM 先在堆中创建一个 String 对象，该对象的 `value` 字段指向一个 `char[]` 数组，数组中存储真实的字符数据 `['h', 'e', 'l', 'l', 'o']`。
2. 然后将这个 String 对象的**引用（地址）**注册到字符串常量池中。
3. 下次再出现 `"hello"` 字面量时，直接从常量池中取出之前的引用，避免重复创建对象。

### 3. 验证示例
```java
// 1. s1 = "hello"
//    - JVM先检查字符串常量池中是否存在"hello"的引用
//    - 常量池中不存在，于是在堆中创建一个String对象（其内部char[]存储['h','e','l','l','o']）
//    - 将该String对象的引用（地址）放入字符串常量池
//    - s1 指向常量池中的这个引用（即堆中的那个String对象）
//    【常量池内容】: {"hello"的引用 -> 堆中String对象}
String s1 = "hello";

// 2. s2 = "hello"
//    - JVM检查字符串常量池，发现已存在"hello"的引用
//    - 直接返回常量池中已有的引用给s2，不创建新对象
//    - s2 和 s1 指向堆中同一个String对象
//    【常量池内容】: {"hello"的引用 -> 堆中String对象}（不变）
String s2 = "hello";

// 3. s3 = new String("hello")
//    - "hello"字面量：常量池中已存在，不再重复创建
//    - new String(...)：在堆中强制创建一个【新的】String对象
//      该新对象的char[]内容也是['h','e','l','l','o']，但它是独立的堆对象
//    - s3 指向这个新创建的堆对象，与常量池中的引用无关
//    【常量池内容】: {"hello"的引用 -> 堆中原String对象}（不变）
//    【堆中新增】: 一个新的String对象（s3指向它）
//
//    总结：此行代码创建了1个新对象（new出来的），若常量池中无"hello"则会创建2个对象
String s3 = new String("hello");

System.out.println(s1 == s2); // true，指向常量池中同一个引用
System.out.println(s1 == s3); // false，s3 是 new 出的新对象，引用不同
System.out.println(s1 == s3.intern()); // true，intern() 返回常量池中的引用
```

**总结**：字符串常量池本质上是一个 `HashSet<String>`（引用集合），池中保存的是已经 intern 过的 String 对象的**引用地址**，而真正的字符数组数据作为对象属性存储在堆内存中。这样做的好处是节省内存：池只需要管理引用（4 或 8 字节），而非复制整个字符数组。
*/
public class Demo4 {
}
