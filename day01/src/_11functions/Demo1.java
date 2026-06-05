package _11functions;

/*
方法（Method，也叫函数 Function）是一段封装好的、可重复调用的代码块，用来完成特定功能 —— 你可以把它理解成「编程里的 “工具”」：提前造好工具，需要时直接用，不用重复造轮子。

方法的格式：
修饰符 返回值类型 方法名([参数类型 参数名1,...]){
    ...
    方法体
    ...
    return 返回值;
}

组成部分                        说明                                                  示例
1. 修饰符              控制访问权限和行为                                               public static
2. 返回值类型           方法执行后返回的数据类型；不返回任何值用 void                        int
3. 方法名              方法的标识，遵循小驼峰命名（动词或动宾短语）                          add
4. 参数列表            调用时传入的数据（可以为空）                                       (int a, int b)
5. 方法体              大括号 {} 里的代码，是方法的真正逻辑                                {return a + b;}
6. return 返回值;      方法执行后返回的数据；如果返回值类型是 void，则不需要 return 语句      return a + b;

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
