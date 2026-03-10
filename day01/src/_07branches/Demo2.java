package _07branches;

/*
    三目运算符：[[type] [变量名] =] (条件表达式) ? 条件为真时执行的代码 : 条件为假时执行的代码
*/
public class Demo2 {
    public static void main(String[] args) {
        int year = 2026;
        String res = ((year % 4 == 0) && (year %100 != 0)) || (year % 400 == 0) ? "是闰年" : "不是闰年";
        System.out.println(year + res);
    }
}
