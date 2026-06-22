package _01generics;

import java.util.ArrayList;
import java.util.List;

// 泛型通配符
public class Demo4 {
    public static void main(String[] args) {
        // 可传入 List<Integer>、List<Double> 等
        System.out.println(sum(List.of(1, 2, 3)));

        addNumbers(new ArrayList<>());
    }

    // 接收 Number 及其子类的集合
    public static double sum(List<? extends Number> list) {
        double total = 0;

        for (Number n : list) total += n.doubleValue();

        return total;
    }

    // 接收 Integer 及其父类的集合
    public static void addNumbers(List<? super Integer> list) {
        list.add(1);   // 可以写入
        list.add(2);

        System.out.println(list);
    }
}
