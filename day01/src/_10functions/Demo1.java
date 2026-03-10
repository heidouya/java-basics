package _10functions;

/*
    方法（Method，也叫函数 Function）是一段封装好的、可重复调用的代码块，用来完成特定功能 —— 你可以把它理解成「编程里的 “工具”」：提前造好工具，需要时直接用，不用重复造轮子。

    方法的格式：
    修饰符 返回值类型 方法名([参数类型 参数名1,...]){
        ...
        方法体
        ...
        return 返回值;
    }

    使用方法的好处：复用代码、简化逻辑、便于维护、解耦分工；
*/
public class Demo1 {
    public static void main(String[] args) {
        int a = 1, b = 2;
        int c = a + b;
        System.out.println(c);

        int d = 3, e = 4;
        int f = d + e;
        System.out.println(f);

        int g = add(5, 6);
        System.out.println(g);

        int h = add(7, 8);
        System.out.println(h);
    }

    public static int add(int a, int b) {
        return a + b;
    }
}
