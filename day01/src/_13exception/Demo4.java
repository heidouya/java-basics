package _13exception;

import java.io.IOException;

// 自定义异常
public class Demo4 {
    double balance = 1000.0;

    public static void main(String[] args) {
        Demo4 demo = new Demo4();

        // 自定义编译时异常
        try {
            demo.withdraw1(2000.0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 自定义受检异常
        try {
            demo.withdraw2(2000.0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 自定义运行时异常
        try {
            demo.withdraw3(2000.0);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void withdraw1(double amount) throws IOException {
        if (amount > balance) {
            throw new IOException(STR."IOException 余额不足，当前余额：\{balance}");
        }
        balance -= amount;
    }

    public void withdraw2(double amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException(STR."InsufficientBalanceException 余额不足，当前余额：\{balance}");
        }
        balance -= amount;
    }

    public void withdraw3(double amount) throws BusinessException {
        if (amount > balance) {
            throw new BusinessException(STR."BusinessException 余额不足，当前余额：\{balance}");
        }
        balance -= amount;
    }
}
