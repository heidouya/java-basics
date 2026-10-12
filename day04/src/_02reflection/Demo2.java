package _02reflection;

import java.lang.reflect.Constructor;

public class Demo2 {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("_02reflection.User");

        System.out.println("-----获取所有的构造方法-----");
        // 获取所有的构造方法
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            System.out.println(declaredConstructor);
        }

        System.out.println("-----获取所有公开的构造方法-----");
        // 获取所有公开的构造方法
        Constructor<?>[] constructors = clazz.getConstructors();

        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }

        System.out.println("-----获取指定的构造方法-----");
        // 获取无参构造方法
        Constructor<?> declaredConstructor1 = clazz.getDeclaredConstructor();
        System.out.println(declaredConstructor1);
        // 获取参数是字符串的构造方法
        Constructor<?> declaredConstructor2 = clazz.getDeclaredConstructor(String.class);
        System.out.println(declaredConstructor2);
        // 获取参数是整数的构造方法
        Constructor<?> declaredConstructor3 = clazz.getDeclaredConstructor(int.class);
        System.out.println(declaredConstructor3);
        // 获取参数是整数的构造方法
        Constructor<?> declaredConstructor4 = clazz.getDeclaredConstructor(String.class, int.class);
        System.out.println(declaredConstructor4);

        // 获取相应构造函数的修饰符对应的数值
        int modifiers = declaredConstructor4.getModifiers();
        System.out.println(modifiers);

        // 获取相应构造函数的参数数量
        int parameterCount = declaredConstructor4.getParameterCount();
        System.out.println(parameterCount);

        // 使用相应构造方法创建对象
        User user1 = (User) declaredConstructor4.newInstance("a", 12);
        System.out.println(user1);

        // 取消权限校验后，才能使用使用私有构造函数创建对象
        declaredConstructor2.setAccessible(true);
        User user2 = (User) declaredConstructor2.newInstance("b");
        System.out.println(user2);
    }
}
