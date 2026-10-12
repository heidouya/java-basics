package _02reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Demo4 {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("_02reflection.User");

        System.out.println("-----获取所有公开的方法（包含父类中所有的公开方法）-----");
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }

        System.out.println("-----获取所有本类方法-----");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod);
        }

        System.out.println("-----获取指定方法-----");
        Method sayHello = clazz.getDeclaredMethod("sayHello", String.class);
        System.out.println(sayHello);

        System.out.println("-----获取指定方法的参数-----");
        Parameter[] parameters = sayHello.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println(parameter);
        }

        System.out.println("-----获取指定方法的返回值类型-----");
        Class<?> returnType = sayHello.getReturnType();
        System.out.println(returnType);

        System.out.println("-----调用获取到的方法-----");
        User user = new User("ff", 17);
        sayHello.setAccessible(true);
        sayHello.invoke(user, "hello");
    }
}
