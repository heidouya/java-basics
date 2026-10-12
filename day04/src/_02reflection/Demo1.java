package _02reflection;

import java.lang.reflect.InvocationTargetException;

/*
# Java反射简介（常用知识点）

## 一、什么是反射
**反射(Reflection)**：程序在**运行期间**，拿到一个类的信息（类名、构造器、成员变量、成员方法、注解），并且可以动态创建对象、读写属性、调用方法。

正常编码：编译期就确定要 new 哪个类、调用哪个方法。
反射：**运行时才知道类是谁**，动态操作。

> 前提：类加载后，JVM会为这个类生成一个`Class`对象，这个对象就是反射的入口。

## 二、获取Class对象的3种常用方式
1. `类名.class` （编译期就确定是哪个类，不触发类初始化，即不执行静态代码块）
2. `对象.getClass()` （已有实例；返回**运行时实际类型**，多态下拿到的是子类的Class）
3. `Class.forName("全类名")` （最核心！框架用的最多，字符串指定类，会触发**类初始化**）

> 补充：`类名.class` 和 `Class.forName` 都会触发类**加载**，区别在于是否触发类**初始化**（静态代码块）。
> 只想加载不想初始化：`Class.forName(name, false, loader)` 或 `ClassLoader.loadClass(name)`。

## 三、反射能干的常用操作
1. 获取构造方法 → 创建对象
2. 获取成员变量Field → 读取/修改属性值（包括private）
3. 获取成员方法Method → 调用方法（包括private）
4. 获取类上、方法上、属性上的注解（注意：只有 `@Retention(RUNTIME)` 的注解才能被反射拿到）

> 重要：访问私有成员，需要`setAccessible(true)`，**开启暴力访问**。

## 四、反射的缺点
1. 性能比直接调用低
2. 编译期无法校验，写错类名/方法名，运行时报错
3. 破坏封装，可访问私有成员
4. Java9+模块化会限制暴力反射（对JDK内部类可能抛 `InaccessibleObjectException`）

## 五、完整可运行示例代码
```
java
import java.lang.reflect.Field;
import java.lang.reflect.Method;

// 目标实体类（示例放同一文件的默认包；实际项目必须写全类名，如 com.xxx.User）
class User {
    private String name;
    public int age;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private void sayHello(String msg) {
        System.out.println("私有方法sayHello：" + msg + "，我的名字：" + name);
    }
}

public class ReflectDemo {
    public static void main(String[] args) throws Exception {
        // 1. 获取Class对象（字符串指定全类名，此处为默认包下的User）
        // 没有package 就是默认包
        //Class<?> clazz = Class.forName("User");

        // 如果User放在某个包下，必须【包名.类名】
        // 复制包名+类名的方式：1. 工程目录选中类文件，单击鼠标右键 2. Copy Path/Reference... 3.Copy Reference
        Class<?> clazz = Class.forName("_02reflection.User");

        // 2. 反射创建对象（Class.newInstance() 已过时，推荐通过构造器创建）
        Object userObj = clazz.getDeclaredConstructor().newInstance();

        // 3. 反射操作【私有成员变量name】
        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true); // 暴力访问，关闭权限检查
        nameField.set(userObj, "张三"); // 设置值
        System.out.println("反射获取name：" + nameField.get(userObj));

        // 4. 反射操作【公有成员变量age】
        Field ageField = clazz.getField("age");
        ageField.set(userObj, 20);
        System.out.println("反射获取age：" + ageField.get(userObj));

        // 5. 反射调用【私有方法sayHello】
        Method helloMethod = clazz.getDeclaredMethod("sayHello", String.class);
        helloMethod.setAccessible(true);
        helloMethod.invoke(userObj, "测试反射调用");
    }
}
```
> ⚠️ 注意：
> - `getDeclaredXXX`：获取本类**所有访问级别**（public/protected/默认/private）
> - `getXXX`（不带Declared）：**只能拿public，包括父类public**
> - **例外：构造器不继承**，`getConstructor` 和 `getDeclaredConstructor` 都只查**本类**构造器，区别只在访问级别
> - `getDeclaredField`：本类所有属性（含private）
> - `getField`：仅public属性（含父类）
> - `getDeclaredMethod`：本类所有方法（含private）
> - `getMethod`：仅public方法（含父类）
> - `getDeclaredConstructor`：本类所有构造器
> - `getConstructor`：仅public构造器

## 六、经典场景（初学练手）
读取`properties`配置文件，拿到类全名，反射创建实例（Spring IOC的简易原型）
```
properties
# config.properties
# 实际项目必须写全类名（包名.类名），此处示例为默认包
className=User
java
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ReflectConfigDemo {
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        try (InputStream in = new FileInputStream("config.properties")) {
            prop.load(in);
        }
        String className = prop.getProperty("className");

        Class<?> clazz = Class.forName(className);
        Object obj = clazz.getDeclaredConstructor().newInstance();
        System.out.println(obj);
    }
}
```
## 七、初学必记异常
1. `ClassNotFoundException`：Class.forName找不到这个类（类名写错/漏了包名）
2. `NoSuchFieldException`：没有这个属性
3. `NoSuchMethodException`：没有这个方法（参数类型不匹配也会抛，如 int.class ≠ Integer.class）
4. `IllegalAccessException`：权限不足，没写 setAccessible(true)
5. `InstantiationException`：抽象类/接口无法实例化
6. `InvocationTargetException`：被反射调用的方法**内部自己抛了异常**，会被包装成它；用 `e.getCause()` 取原始异常
7. `InaccessibleObjectException`（Java 9+）：模块化限制，无法暴力反射 JDK 内部类
*/
public class Demo1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        // 方式1：类名.class 不需要字符串，直接写类名，编译期校验
        Class<User> c1 = User.class;

        // 方式2：对象.getClass()，已有实例，不用写类名字符串
        User user = new User();
        Class<?> c2 = user.getClass();

        // 方式3：Class.forName("全类名字符串")，必须【包名.类名】，框架最常用
        Class<?> c3 = Class.forName("_02reflection.User");
    }
}
