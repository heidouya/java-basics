package _03comments;

public class Demo1 {
    public static void main(String[] args) {
        // 1. 单行注释：//   快捷键：ctrl + /
        int a = 1;
        int b = 1;

        // 2. 多行注释：/* 注释内容 */   快捷键：ctrl + shift + /

        /*
        我是多行注释
        我是多行注释
        我是多行注释
        */

        int c = 1;
        int d = 1;
        int e = 1;

        // 3. 文档注释：/** 注释内容 */  快捷键：在类、方法上输入 /** 然后按 Enter
        int sum = add(1, 2);
        System.out.println(sum);
    }

    /**
     * 计算两个整数的和
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 两数之和
     */
    public static int add(int a, int b) {
        return a + b;
    }
}
