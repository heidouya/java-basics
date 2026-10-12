package _02reflection;

import java.lang.reflect.Field;

public class Demo3 {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("_02reflection.User");

        System.out.println("-----获取所有公开的成员变量-----");
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        System.out.println("-----获取所有成员变量-----");
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }

        System.out.println("-----获取指定的成员变量-----");
        Field name = clazz.getDeclaredField("name");
        System.out.println(name);

        // 获取成员变量存储的值
        User user = new User("dddd", 23);
        String o = (String) name.get(user);
        System.out.println(o);

        // 修改成员变量的值
        name.set(user, "ff");
        System.out.println(user);
    }
}
