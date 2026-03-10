package _05types;

// 类型转换
public class Demo2 {
    public static void main(String[] args) {
        byte a = 1;

        // 隐式类型转换
        int b = a + 2;

        System.out.println(b); // 3

        int c = 1;

        // 强制类型转换
        byte d = (byte)c;

        System.out.println(d);// 1

    }
}
