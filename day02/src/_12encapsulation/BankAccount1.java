package _12encapsulation;

public class BankAccount1 {
    public String accountNumber;
    public double balance;
    public String password;


    // 私有属性，外部无法直接访问

    //public BankAccount(String accountNumber, double balance, String password) {
    //    this.accountNumber = accountNumber;
    //    this.balance = balance;
    //    this.password = password;
    //}

    // 提供公共的访问方法
    public double getBalance() {
        return balance;
    }

    // 带验证的存款方法
    public void deposit(double amount) {
        // 验证逻辑
        if (amount <= 0) {
            System.out.println("存款金额必须大于0");
            return;
        }

        // 执行存款
        balance = balance + amount;
        System.out.println("存款成功，余额：" + balance);
    }

    // 带验证的取款方法
    public void withdraw(double amount, String pwd) {
        if (!pwd.equals(password)) {
            System.out.println("密码错误");
            return;
        }
        if (amount <= 0) {
            System.out.println("取款金额必须大于0");
            return;
        }
        if (amount > balance) {
            System.out.println("余额不足");
            return;
        }
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("取款成功，当前余额：" + balance);
        }
    }
}
