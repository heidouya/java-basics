package _12encapsulation;

/*
    封装：封装是面向对象编程的三大特性之一（封装、继承、多态），指将数据（属性）和操作数据的方法（行为）绑定在一起，并隐藏内部实现细节，只对外提供必要的访问接口。

    封装的核心价值：
    1. 安全：保护数据不被随意修改
    2. 简化：隐藏复杂实现，提供简单接口
    3. 灵活：内部修改不影响外部调用
    4. 可维护：代码组织清晰，易于维护和扩展

    封装的设计原则：
    1. 最小暴露原则：尽可能隐藏内部实现
    2. 单一职责：一个类只封装一种业务逻辑
    3. 高内聚：相关的数据和方法放在一起
    4. 低耦合：减少类之间的依赖

    怎么实现封装？
    1. 将数据（属性）和行为（方法）绑定在一起，形成一个类
    2. 通过访问控制符（private、protected、public）来实现信息的隐藏
    3. 对内部实现细节进行隐藏，只对外提供必要的访问接口
       3.1 对于成员变量，对外提供Getter和Setter方法来操作成员变量
       3.2 成员变量初始化时，使用构造函数完成初始化

    被private、protected、public修饰的信息，什么地方可以访问呢？
    修饰符	    本类	    同包	    子类	    任何地方
    private	    ✓	    ✗	    ✗	    ✗
    默认	        ✓	    ✓	    ✗	    ✗
    protected	✓	    ✓	    ✓	    ✗
    public	    ✓	    ✓	    ✓	    ✓

    总结：
    1. 封装核心：私有属性 + 公共方法 + 逻辑校验，隐藏细节、保护数据；
    2. 对比优势：封装让数据更安全、代码更易维护 / 扩展，而面向过程无统一管控，易出错、难修改；
    3. 实用价值：降低耦合，提高程序的可维护性、可复用性
*/

// 面向过程：属性直接暴露，任何人可随意修改
// 问题：属性无保护，外部可随意赋值非法数据，逻辑分散，出错后难排查。
//public class Demo1 {
//    // 全局变量，任何地方都可以直接访问和修改
//    public static String accountNumber;
//    public static double balance;
//    public static String password;
//
//    public static void main(String[] args) {
//        // 初始化账户
//        accountNumber = "123456";
//        balance = 1000;
//        password = "123456";
//
//        // 使用函数操作
//        withdraw(200, "123456");
//        withdraw(5000, "123456");  // 会提示余额不足
//
//        // 存在的问题：
//        // 1. 可以直接绕过函数修改数据
//        balance = balance - 100;  // 仍然可以直接操作
//
//        // 2. 需要在每个函数中都写验证逻辑
//        // 3. 所有函数都是全局的，容易命名冲突
//        // 4. 数据和操作分离，维护困难
//    }
//
//    // 取款函数
//    public static void withdraw(double amount, String pwd) {
//        // 验证逻辑
//        if (!pwd.equals(password)) {
//            System.out.println("密码错误");
//            return;
//        }
//        if (amount <= 0) {
//            System.out.println("取款金额必须大于0");
//            return;
//        }
//        if (amount > balance) {
//            System.out.println("余额不足");
//            return;
//        }
//
//        // 执行取款
//        balance = balance - amount;
//        System.out.println("取款成功，余额：" + balance);
//    }
//
//    // 存款函数
//    public static void deposit(double amount) {
//        // 验证逻辑
//        if (amount <= 0) {
//            System.out.println("存款金额必须大于0");
//            return;
//        }
//
//        // 执行存款
//        balance = balance + amount;
//        System.out.println("存款成功，余额：" + balance);
//    }
//}

public class Demo1 {
    public static void main(String[] args) {
        BankAccount1 bankAccount = new BankAccount1();

        bankAccount.accountNumber = "0123456789";
        bankAccount.balance = 100;
        bankAccount.password = "123456";

        bankAccount.deposit(100);
        bankAccount.withdraw(50,"123456");
    }
}
