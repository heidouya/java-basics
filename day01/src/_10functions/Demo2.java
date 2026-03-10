package _10functions;

/*
    方法重载（Overload）是指：在同一个类中，定义多个「方法名相同、参数列表不同」的方法（参数列表不同 = 参数个数 / 类型 / 顺序不同），调用时 JVM 会根据传入的参数自动匹配对应的方法。简单说：同一个 “工具名”，能处理不同类型 / 数量的 “原材料”。

    使用方法重载的好处：
    1. 简化调用：不用记多个方法名（比如不用写add2Int、add3Int、addDouble），统一用add即可，代码更简洁；
    2. 适配多场景：比如开发中，一个showToast方法，既能接收字符串（showToast("提示")），也能接收字符串 + 时长（showToast("提示", 3)），适配不同的提示需求；
    3. 提升代码可读性：同名方法语义一致。

    总结：
    1. 方法重载核心：同类、同名、参数列表不同；
    2. 关键规则：返回值不同不算重载，参数列表不同（个数 / 类型 / 顺序）才算；
    3. 核心价值：简化调用、适配多场景，是 Java 开发中提升代码规范性的基础技巧。
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
