package _04variables;

public class Demo1 {
    public static void main(String[] args) {
        /*
        变量格式：[type] [变量名] [= 初始值]
        命名规范：
        1. Java标识符由字母、数字、下划线（_）和美元符号（$）组成
        2. 不能以数字开头，必须以字母、下划线或美元符号开头。
        3. 标识符要使用有意义的英文单词表示，尽量不要出现特殊符号。
        4. 类名采用驼峰格式：即所有单词首字母大写，其它字母小写。例如，MathTeacher
        5. 变量和方法名采用驼峰格式，但首个单词字母小写。例如：lastName
        6. 变量名区分大小写
        7. 变量名不能使用Java关键字、保留字

        类型推断：var
        var 不是万能的，只能用于：
        ✅ 方法内的局部变量
        ✅ for/foreach 循环变量
        ❌ 不能用于：成员变量、方法参数、方法返回值、构造方法参数
        */
        int a;
        int b = 1;
        var c = 2;

        //System.out.println(a); 变量使用前需要赋值，否则会报错
        System.out.println(b);
        System.out.println(c);
    }
}
