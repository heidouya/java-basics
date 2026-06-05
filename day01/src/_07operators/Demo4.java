package _07operators;

/*
    逻辑运算符：
    &&：逻辑与
        规则：条件都为真，结果才为真
        特点：左边为假，则右边代码不会执行
    ||：逻辑或
        规则：条件有一个为真，结果就为真
        特点：左边为真，则右边代码不会执行
    !：逻辑非
        规则：条件为真，结果为假；条件为假，结果为真。简单来说就是计算结果取反。
*/
public class Demo4 {
    public static void main(String[] args) {
        int a = 1, b = 2;

        System.out.println(a < b && a > b); // false
        System.out.println(a < b || a < b); // true
        System.out.println(!(a < b)); // false
    }
}
