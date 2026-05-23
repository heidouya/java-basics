package _05static;

/*静态变量示例1 - 单例模式*/
public class Demo2 {
    public static void main(String[] args) {
        Singleton obj1 = Singleton.getInstance();
        Singleton obj2 = Singleton.getInstance();
        System.out.println(obj1 == obj2);  // true
    }
}

class Singleton {
    // 静态变量保存唯一实例
    private static Singleton instance;

    // 私有构造
    private Singleton() {
        System.out.println("单例创建");
    }

    // 静态方法获取实例
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // 静态方法业务逻辑
    public static void doSomething() {
        System.out.println("执行操作");
    }
}
