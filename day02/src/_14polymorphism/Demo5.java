package _14polymorphism;

/*静态方法示例1*/
public class Demo5 {
    public static void main(String[] args) {
        String str = "Hello";
        if (StringUtils.isNotEmpty(str)) {
            String reversed = StringUtils.reverse(str);
            System.out.println(reversed);
        }
    }
}
class StringUtils {
    // 工具类通常私有构造，防止实例化
    private StringUtils() { }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String reverse(String str) {
        if (isEmpty(str)) return str;
        return new StringBuilder(str).reverse().toString();
    }
}
