package _08loops;

/*
    break：跳出循环
    continue：跳过本次循环
*/
public class Demo4 {
    public static void main(String[] args) {
        System.out.println("------------break语句-----------");
        // break
        for (int i = 0; i < 5; i++) {
            if (i == 2) break;
            System.out.println(STR."i = \{i}");
        }
        System.out.println("------------continue语句-----------");
        // continue
        for (int i = 0; i < 5; i++) {
            if (i == 2) continue;
            System.out.println(STR."i = \{i}");
        }
        System.out.println("-----使用标签（Label）跳出外层循环 break语句-----");

        outer:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 2) {
                    break outer;
                }
                System.out.println(STR."j = \{j}");
            }
        }

        System.out.println("------------while循环 break语句-----------");

        // break 跳出循环
        int i = 1;
        //while (i <= 5) {
        //    if (i == 3) {
        //        break;
        //    }
        //    System.out.println("i = " + i);
        //    i++;
        //}

        System.out.println("------------while循环 continue语句-----------");

        // continue 跳过本次循环
        int j = 1;
        while (j <= 5) {
            if (j == 3) {
                j++;
                continue;
            }
            System.out.println(STR."j = \{j}");
            j++;
        }
    }
}
