package _08loops;

/*
    break：跳出循环
    continue：跳过本次循环
*/
public class Demo4 {
    public static void main(String[] args) {
        // break 跳出循环
        int i = 1;
        //while (i <= 5) {
        //    if (i == 3) {
        //        break;
        //    }
        //    System.out.println("i = " + i);
        //    i++;
        //}

        // continue 跳过本次循环
        int j = 1;
        while (j <= 5) {
            if (j == 3) {
                j++;
                continue;
            }
            System.out.println("j = " + j);
            j++;
        }
    }
}
