package _03comments;

//注释是给程序员看的，编译器会忽略注释

//注释的作用：
//1. 说明代码的作用
//2. 临时关闭某些代码，让它不执行，可以不用删除源码进行调试

// 注释的分类：
// 1. 单行注释：// 注释内容      快捷键：ctrl + /
// 2. 多行注释：/* 注释内容 */   快捷键：ctrl + shift + /
// 3. 文档注释：/** 注释内容 */  快捷键：在类、方法上输入 /** 然后按 Enter

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
        // 计算两个整数的和
        int sum = add(1, 2);
        System.out.println(sum);
        //System.out.println(sum);
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
