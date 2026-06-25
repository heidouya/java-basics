package _07interface;

// 1. 定义接口
public interface Animal {
    // 常量（默认 public static final）
    String TYPE = "生物";

    // 抽象方法（默认 public abstract）
    void eat();

    // 默认方法
    default void breathe() {
        System.out.println("用氧气呼吸");
    }
}
