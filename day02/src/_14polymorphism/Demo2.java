package _14polymorphism;

/*
    instanceof：instanceof是 Java 的二元运算符，核心作用是：判断一个对象是否是某个类（或其子类 / 接口）的实例，返回true或false。

    instanceof 语法：
    ```sh
    对象 instanceof 类/接口
    ```
    左边：必须是对象实例（不能是基本数据类型）；
    右边：必须是类 / 接口类型（不能是基本类型，如int）

    使用场景：
    1. 多态场景下的类型判断（最常用）：多态中父类引用指向子类对象，若需调用子类专属方法，需先通过instanceof判断类型，再向下转型（避免类型转换异常）。
    2. 避免类型转换异常（ClassCastException）：如果直接强制转型，非对应类型会报错，instanceof是 “安全锁”。

    总结：
    1. instanceof核心：判断对象是否是指定类 / 接口的实例，返回布尔值；
    2. 核心场景：多态下的类型判断、避免转型异常；
    3. 关键注意：子类实例匹配父类返回 true，null 匹配任何类都返回 false，Java14 + 支持模式匹配简化代码。
*/
public class Demo2 {
    public static void main(String[] args) {
        HarmonyDevice device1 = new HarmonyPhone();
        HarmonyDevice device2 = new HarmonyPad();

        // instanceof
        System.out.println(device1 instanceof HarmonyDevice); // true （子类实例也是父类实例）
        System.out.println(device1 instanceof HarmonyPhone); // true （本质是手机对象）
        System.out.println(device1 instanceof HarmonyPad); // false （平板不是手机）

        useDevice1(device1);
        useDevice1(device2);

        useDevice2(device1);
        useDevice2(device2);
    }

    // 多态中父类引用指向子类对象，若需调用子类专属方法，需先通过instanceof判断类型，再向下转型（避免类型转换异常）
    public static void useDevice1(HarmonyDevice device) {
        // 先判断类型，再转型调用子类专属方法
        if (device instanceof HarmonyPhone) {
            // 向下转型：父类引用转成子类类型
            HarmonyPhone phone = (HarmonyPhone) device;
            phone.takePhoto(); // 调用手机专属的拍照方法
        } else if (device instanceof HarmonyPad) {
             HarmonyPad pad = (HarmonyPad) device;
                        pad.setScreenSize(); // 调用平板专属的设置屏幕方法
        }
    }

    public static void useDevice2(HarmonyDevice device) {
        // instanceof模式匹配（简化转型），无需手动强转
        // 判断+转型一步到位
        if (device instanceof HarmonyPhone phone) {
            phone.takePhoto();
        } else if (device instanceof HarmonyPad pad) {
            pad.setScreenSize();
        }
    }
}

/*
报警告：Class 'HarmonyDevice' is exposed outside its defined visibility scope
原因：类没有加访问权限修饰符
*/
