package _08enum;

// 2. 枚举可以自定义构造器、成员变量、普通方法，构造器必须私有（默认就是 private，无需显式写）：
public enum OrderStatus {
    // 常量赋值：调用构造器初始化属性
    PENDING(1, "待支付"),
    PAID(2, "已支付"),
    SHIPPED(3, "已发货");

    // 成员属性
    private final int code;
    private final String desc;

    // 私有构造器
    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 自定义方法
    public String getDesc() {
        return desc;
    }
}