package _05types;

/*
字符串是 Java 中的一种数据类型，用于表示一串文字或字符。字符串用双引号包裹。

字符串的创建：
1. 使用双引号包裹字符串内容，例如："Hello, World!"
2. 使用 String 类的构造方法，例如：new String("Hello, World!")

核心特点：
1. String 是一个类，不是基本类型
2. 字符串一旦创建，内容不能改（不可变）
3. 放在字符串常量池里，相同内容复用，节省内存
4. 用 equals() 比较内容，别用 ==

字符串常量池是 Java 堆内存中的一块特殊区域，用于缓存字符串字面量（用双引号 "..." 定义的字符串），让相同内容的字符串复用同一个对象，从而节省内存、提升性能。

Java 会做 2 件事：
1. 去常量池里找有没有这个字符串
2. 有 → 直接用；没有 → 创建一个放进去，下次用时再找

1. 双引号创建的字符串 → 进字符串常量池，自动复用
2. new String 创建的 → 不进池（或单独放），不共享
3. == 比较地址，所以常量池里相同字符串会是 true
4. 比较字符串内容永远用 equals ()

intern()：将字符串放入字符串常量池，返回池中相同内容的字符串对象，如果池中没有则创建一个。示例：
```java
String s1 = new String("Hello");
String s2 = s1.intern();
```

常用方法：
1. length()：获取字符串长度
2. equals()：比较字符串内容
3. contains()：判断是否包含某子字符串
4. substring()：截取子字符串
5. replace()：替换字符串
6. split()：分割字符串
7. charAt()：获取指定位置的字符
8. toUpperCase()：转换为大写
9. toLowerCase()：转换为小写
10. trim()：去除前后空格
11. startsWith()：判断是否以某字符串开头
12. endsWith()：判断是否以某字符串结尾
13. indexOf()：获取某字符或子字符串第一次出现的位置
14. lastIndexOf()：获取某字符或子字符串最后一次出现的位置
*/
public class Demo3 {
    public static void main(String[] args) {
        // 字符串存储
        System.out.println((int)'a'); // 97
        System.out.println((int)'我'); // 25105
        System.out.println((char)25105);

        // 直接创建（推荐）
        String s1 = "Hello";
        String s2 = "Hello";
        // 比较地址（相同内容复用）
        System.out.println(s1 == s2);   // true
        // 构造创建
        String s3 = new String("Hello");
        System.out.println(s1 == s3);   // false

        // intern()：将字符串放入字符串常量池，返回池中相同内容的字符串对象，如果池中没有则创建一个。
        String s4 = s3.intern();
        System.out.println(s1 == s4);   // true
        System.out.println(s1 == s3); // false

        // 获取字符串长度
        System.out.println(s1.length());          // 5
        // 比较字符串内容
        System.out.println(s1.equals("abc"));     // false
        // 判断是否包含某子字符串
        System.out.println(s1.contains("b"));     // false
        // 截取字符串
        System.out.println(s1.substring(2));    // "llo"
        // 替换字符串
        System.out.println(s1.replace("a","b"));  // "bello"
        // 分割字符串
        System.out.println(s1.split(","));  // ["Hello"]
        // 获取字符
        System.out.println(s1.charAt(0));   // 'H'
    }
}
