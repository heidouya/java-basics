package _07branches;

/*
    if(条件表达式) {
       //如果条件表达式为true时要执行的代码
    }

    if(条件表达式){
       //如果条件表达式的值为true时要执行的代码
    }else{
       //如果条件表达式的值为false时要执行的代码
    }

    if(条件表达式 1){
       //如果条件表达式 1的值为true时要执行的代码
    }else if(条件表达式 2){
       //如果条件表达式 2的值为true时要执行的代码
    }else if(条件表达式 3){
       //如果条件表达式 3的值为true时要执行的代码
    }else {
       //如果以上条件表达式都不为true时要执行的代码
    }
*/
public class Demo1 {
    public static void main(String[] args) {
        int a = 1;
        // if
        if(a < 2) {
            System.out.println("if");
        }

        // if...else...
        if(a == 1) {
            System.out.println("a == 1");
        }else {
            System.out.println("a != 1");
        }

        //if... else if... else...
        if(a == 0) {
            System.out.print("a is 0");
        } else if(a == 1){
            System.out.println("a is 1");
        } else if(a == 2){
            System.out.println("a is 2");
        } else{
            System.out.println("else");
        }

        // 判断一个年份是不是闰年：这个年份能被4整除，但不能被100整除或者这个年份能被400 整除
        int year = 2026;
        if (((year % 4 == 0) && (year %100 != 0)) || (year % 400 == 0)) {
            System.out.println(year + "年是闰年");
        } else {
            System.out.println(year + "年不是闰年");
        }
    }
}
