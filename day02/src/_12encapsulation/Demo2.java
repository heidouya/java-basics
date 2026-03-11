package _12encapsulation;

/*
    Getter/Setter：Getter和Setter是Java中用于访问和修改私有属性的公共方法，避免属性被直接操作，是封装思想的具体实现。

    getter 方法：命名规则get+属性名（首字母大写）（布尔类型可简化为is+属性名），作用是读取私有化的属性值；
    setter 方法：命名规则set+属性名（首字母大写），作用是修改私有化的属性值，可在方法内加校验逻辑。

    快速生成setter、getter方法的快捷键：Alt + Insert

    Getter/Setter的主要作用：
    1. 封装控制：控制属性的访问方式
    2. 验证逻辑：在设置值时进行校验
    3. 灵活扩展：可以在不改变接口的情况下修改内部实现
    4. 统一访问：提供标准化的属性访问方式

    注意：在Java类中，当方法内的局部变量与类的成员属性重名时，要使用 this 区分；无重名时，this 可省略。
*/
public class Demo2 {
    public static void main(String[] args) {
        BankAccount2 bankAccount = new BankAccount2();

        bankAccount.setAccountNumber("0123456789");
        bankAccount.setPassword("123456");
        bankAccount.setBalance(100);

        bankAccount.deposit(100);
        bankAccount.withdraw(50,"123456");
    }
}
