package _10functions;

/*
    可变参数（Varargs）是 Java 中允许方法接收数量不固定的同类型参数的语法，用 类型 ...参数名 表示（本质是数组）—— 简单说，就是让一个方法能接收 1 个、多个甚至 0 个同类型参数，不用定义多个重载方法。

    可变参数的作用：简化多参数方法的定义，避免冗余的重载

    注意：一个方法中只能指定一个可变参数，它必须是方法的最后一个参数。任何普通的参数必须在它之前声明。
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
