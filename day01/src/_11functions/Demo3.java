package _11functions;

/*
可变参数：允许一个方法接收**不定数量**的同类型参数，使用 `类型...` 语法声明。

## 示例

```java
public static int sum(int... nums) {
    int total = 0;
    for (int n : nums) {
        total += n;
    }
    return total;
}
```

## 调用方式

```java
sum();           // 传0个参数，结果：0
sum(1);          // 传1个参数，结果：1
sum(1, 2, 3);   // 传3个参数，结果：6
sum(1, 2, 3, 4, 5);  // 传5个参数，结果：15
```

## 本质

可变参数本质上是一个**数组**，编译器会自动将传入的参数封装为数组：

```java
public static void test(int... nums) {
    System.out.println(nums.length);  // 可以用 .length
    System.out.println(nums[0]);      // 可以用下标访问
}
```

也可以直接传入一个数组：

```java
int[] arr = {1, 2, 3};
sum(arr);  // ✅ 合法
```

## 使用规则

1. **一个方法最多只能有一个可变参数**

```java
// ❌ 编译报错
public void test(int... a, double... b) { }
```

2. **可变参数必须放在参数列表的最后**

```java
// ✅ 正确
public void test(String name, int... scores) { }

// ❌ 编译报错
public void test(int... scores, String name) { }
```

3. **可变参数可以和普通参数共存**

```java
public static double average(String label, double... nums) {
    double sum = 0;
    for (double n : nums) sum += n;
    System.out.println(label);
    return sum / nums.length;
}

average("成绩平均分", 90, 85.5, 92);
```

## 与方法重载的关系

可变参数方法可以和固定参数方法构成重载：

```java
public void print(int a) { ... }
public void print(int a, int b) { ... }
public void print(int... nums) { ... }
```

调用时，编译器**优先匹配固定参数**的方法：

```java
print(1);      // 调用 print(int a)
print(1, 2);   // 调用 print(int a, int b)
print(1,2,3);  // 调用 print(int... nums)
```
关于上面的代码，聪明的你可能会想到，根本不需要写三个 print 方法，只保留使用可变参数的print方法就行了。

是的，在写代码的时候，合理使用可变参数，可以简化多参数方法的定义，避免冗余的方法重载。

## 常见应用
下面是几个 Java 自带的支持可变参数的方法：
- `System.out.printf(String format, Object... args)` 格式化输出内容到控制台，支持占位符控制输出格式。
- `String.format(String format, Object... args)` 按指定格式拼接、生成格式化字符串，返回新字符串。
- `Arrays.asList(T... a)` 将数组转为固定大小的 List 集合，该集合不支持增删操作。
*/
public class Demo3 {
    public static void main(String[] args) {
        // 调用：参数数量任意
        int res1 = sum(1, 2);       // 传2个参数，结果3
        int res2 = sum(1, 2, 3, 4); // 传4个参数，结果10
        int res3 = sum();           // 传 0 个参数，结果0
    }

    // 定义可变参数方法：计算多个数的和
    public static int sum(int ...nums) {
        int total = 0;
        // 可变参数本质是数组，可直接遍历
        for (int num : nums) {
            total += num;
        }
        System.out.println("传递了" + nums.length + "个参数"); // 获取可变参数的个数
        return total;
    }
}
