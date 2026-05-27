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

字符串常量池（String Constant Pool）
Java 专门给字符串开辟的一块内存区域，用来存字符串，避免重复创建，节省内存。

Java 会做 2 件事：
1. 去常量池里找有没有这个字符串
2. 有 → 直接用；没有 → 创建一个放进去，下次用时再找

1. 双引号创建的字符串 → 进字符串常量池，自动复用
2. new String 创建的 → 不进池（或单独放），不共享
3. == 比较地址，所以常量池里相同字符串会是 true
4. 比较字符串内容永远用 equals ()

*/
public class Demo3 {
    public static void main(String[] args) {
        // 直接创建（推荐）
        String s1 = "Hello";
        String s2 = "Hello";
        System.out.println(s1 == s2);           // 比较地址（相同内容复用）
        // 构造创建
        String s3 = new String("Hello");

        System.out.println(s1.length());          // 长度
        System.out.println(s1.equals("abc"));     // 比较内容（必须用）
        System.out.println(s1.contains("b"));     // 是否包含
        System.out.println(s1.substring(2));      // 截取
        System.out.println(s1.replace("a","b"));  // 替换
        System.out.println(s1.split(","));        // 分割
        System.out.println(s1.charAt(0));         // 取第1个字符
    }
}
