package _05types;

// 基本数据类型
/*
类型            位数               范围
byte           8 位         (-128) ~ (127)
short          16 位        (-32768) ~ (32767)
int            32 位        (-2147483648) ~ (2147483647)
long           64 位        (-9223372036854775808) ~ (9223372036854775807)
float          32 位        (-3.4028235E+38) ~ (3.4028235E+38)
double         64 位        (-1.7976931348623157E+308) ~ (1.7976931348623157E+308)
*/
public class Demo1 {
    public static void main(String[] args) {
        // 整型
        byte a = 100;
        short b = 1000;
        int c = 10000;
        long d = 10000000000L;

        // 浮点类型
        float e = 0.1f; // 单精度
        double f = 0.1; // 双精度

        // 字符
        char g = 'e';
        // 字符串它是引用数据类型，后面聊
        String h = "fff";

        // 布尔
        boolean flag1 = true;
        boolean flag2 = false;
    }
}
