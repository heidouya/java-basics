package _06operators;

/*
    算数运算符：
    +：加
    -：减
    *：乘
    /：除
    ++：自增
    --：自减
*/
public class Demo1 {
    public static void main(String[] args) {
        int a = 11, b = 2;

        // 加、减、乘、除
        int c = a + b;
        int d = a - b;
        int e = a * b;
        int f = a / 2;
        System.out.println(f); // 5

        // 自增 ++
        // 前自增 先给这个变量加1，然后再使用这个变量
        // 后自增 先使用这个变量，再给这个变量加一
        int g = 1;
        System.out.println(g++); // 1 相当于 g = g + 1

        int h = 1;
        System.out.println(++h); // 2
        // h = h + 1

        // 自减 --
        // 前自减 先给这个变量减1，然后再使用这个变量
        // 后自减 先使用这个变量，再给这个变量减一
        int i = 2;
        System.out.println(i--); // 2 相当于 i = i - 1

        int j = 2;
        System.out.println(--j); // 1
    }
}
