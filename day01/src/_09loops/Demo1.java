package _09loops;

/*
循环语句的主要作用是让一段代码按照指定条件重复执行，避免编写大量重复代码。用有限的代码完成无限次（或大量次）的重复任务，是计算机自动化处理的基础。例如：打印 1~100 的数字，无需写 100 行 System.out.println，一个循环即可解决。

for循环格式：
for (初始化; 循环条件; 迭代表达式) {
    // 循环体
}

快捷键：fori 和 变量.fori 和 变量.forr

Java中的增强 for 循环格式：
for(元素类型 变量 : 数组或集合) {
   // 循环体
}

选用建议：
场景                          推荐
已知次数                       for
条件控制、次数不定              while
至少执行一次                  do-while
遍历数组/集合                  增强for
*/
public class Demo1 {
    public static void main(String[] args) {
        System.out.println(1);
        System.out.println(2);
        System.out.println(3);
        System.out.println(4);
        System.out.println(5);
        //...省略
        System.out.println(100);

        // for循环
        for (int i = 1; i <= 100; i++) {
            System.out.println(i);
        }

        // 正序
        for (int i = 0; i < 3; i++) {
            System.out.println(STR."你好\{i}");
        }

        // 倒序
        for (int i = 2; i >= 0; i--) {
            System.out.println(STR."你好\{i}");
        }

        // 使用增强 for 循环遍历数组
        int[] arr = {1, 2, 3};
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
