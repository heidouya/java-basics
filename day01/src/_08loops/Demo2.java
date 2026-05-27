package _08loops;

/*
    do…while 循环格式：
    do {
      // 循环体
    } while(循环条件);

    do…while 循环和 while 循环相似，不同的是，do…while 循环至少会执行一次。
*/
public class Demo2 {
    public static void main(String[] args) {
        int a = 2;
        do {
            System.out.println(a); // 2 1
            a--;
        } while(a > 0);
    }
}
