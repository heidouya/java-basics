package _08loops;

/*
    for循环格式：
    for(初始化; 条件表达式; 更新) {
        // 代码语句
    }

    快捷键：fori 和 变量.fori 和 变量.forr

    Java中的增强 for 循环格式：
    for(声明语句 : 表达式) {
       //代码句子
    }
*/
public class Demo3 {
    public static void main(String[] args) {
        // 正序
        for (int i = 0; i < 3; i++) {
            System.out.println("你好" + i);
        }

        // 倒序
        for (int i = 2; i >= 0; i--) {
            System.out.println("你好" + i);
        }

        // 使用增强 for 循环遍历数组
        int[] arr = {1, 2, 3};
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
