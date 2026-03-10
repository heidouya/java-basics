package _08loops;

/*
    while循环格式：
    while(条件表达式) {
      // 条件表达式为真时，需要循环执行的代码
    }
*/
public class Demo1 {
    public static void main(String[] args) {
        System.out.println("你好");
        System.out.println("你好");
        System.out.println("你好");


        // 打印三次你好
        int count = 0;
        while (count < 3) {
            System.out.println("你好");
            count++;
        }

        // 求1到10的和
        int i = 1, sum = 0;
        while (i <= 5) {
            sum += i;
            i++;
        }
        System.out.println(sum); // 15
    }
}
