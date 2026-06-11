package _11functions;

/*
方法重载（Overload）是指：在同一个类中，定义多个「方法名相同、参数列表不同」的方法（参数列表不同 = 参数个数 / 类型 / 顺序不同），调用时 JVM 会根据传入的参数自动匹配对应的方法。

`System.out.println()` 就是典型的重载应用——可以传入 `int`、`String`、`double` 等不同类型的参数。

## 构成重载的条件

1. **方法名必须相同**
2. **参数列表必须不同**（满足以下任一即可）：
   - 参数**类型**不同
   - 参数**个数**不同
   - 参数**顺序**不同（不推荐，可读性差）

3. **返回值类型不参与判定**——返回值相同或不同都无所谓

## 示例

```java
public class Calculator {
    // 两个int相加
    public int add(int a, int b) {
        return a + b;
    }

    // 三个int相加（参数个数不同）
    public int add(int a, int b, int c) {
        return a + b + c;
    }

    // 两个double相加（参数类型不同）
    public double add(double a, double b) {
        return a + b;
    }
}
```

## 不构成重载的情况

```java
// ❌ 仅返回值不同，参数列表相同 → 编译报错
public int add(int a, int b) { return a + b; }
public double add(int a, int b) { return a + b; }  // 报错！

// ❌ 仅参数名不同 → 编译报错
public int add(int a, int b) { return a + b; }
public int add(int x, int y) { return x + y; }  // 报错！
```

## 调用时如何匹配

编译器根据你传入的**实参类型和个数**自动匹配对应的方法：

```java
Calculator calc = new Calculator();
calc.add(1, 2);       // 调用 add(int, int)
calc.add(1, 2, 3);   // 调用 add(int, int, int)
calc.add(1.0, 2.0);  // 调用 add(double, double)
```

## 核心要点

- 重载的本质是让**同名方法**处理**不同类型/数量的参数**，提高代码的可读性和灵活性
- 判定重载**只看参数列表**，与返回值、访问修饰符无关

## 方法重载的好处
使用方法重载的好处：
1. 简化调用：不用记多个方法名（比如不用写addInt1、addInt2、addDouble），统一用add即可，代码更简洁；
2. 适配多场景：比如开发中，一个showToast方法，既能接收字符串（showToast("提示")），也能接收字符串 + 时长（showToast("提示", 3)），适配不同的提示需求；
3. 提升代码可读性：同名方法语义一致。
*/
public class Demo2 {
    public static void main(String[] args) {
        // 调用时自动匹配
        int res1 = add(1, 2);       // 匹配第一个方法，结果3
        int res2 = add(1, 2, 3);    // 匹配第二个方法，结果6
        double res3 = add(1.5, 2.5);// 匹配第三个方法，结果4.0
    }
    // 1. 两个int相加
    public static int add(int a, int b) {
        return a + b;
    }

    // 2. 三个int相加（参数个数不同）
    public static int add(int a, int b, int c) {
        return a + b + c;
    }

    // 3. 两个double相加（参数类型不同）
    public static double add(double a, double b) {
        return a + b;
    }
}
