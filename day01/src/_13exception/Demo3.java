package _13exception;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// 抛出异常
/*
`throw` 和 `throws` 是 Java 异常处理中两个容易混淆但作用完全不同的关键字。

---

## 1. `throws` —— 声明异常（告诉调用者：我可能会出问题）

- **用在方法签名末尾**，跟在参数列表的右括号后面。
- 作用是**声明**该方法可能抛出哪些异常，但不处理，交给调用者处理。
- 可以跟多个异常类型，用逗号隔开。

**示例：**
```java
public void readFile(String path) throws FileNotFoundException, IOException {
    // 这个方法里可能会抛出 FileNotFoundException 或 IOException
    FileInputStream fis = new FileInputStream(path);
    fis.read();
}
```

> 🔔 当调用一个 **throws 了编译时异常** 的方法时，调用者必须处理（try-catch）或继续向上 throws。

---

## 2. `throw` —— 抛出异常（手动制造一个异常）

- **用在方法体内部**，是一个具体的**动作**。
- 作用是**主动抛出一个异常对象**（通常是自定义异常或满足条件时报错）。
- 后面必须跟一个 `Throwable` 或其子类的实例。

**示例：**
```java
public void setAge(int age) {
    if (age < 0 || age > 150) {
        // 主动制造异常
        throw new IllegalArgumentException("年龄必须在0~150之间");
    }
    this.age = age;
}
```

---

## 3. 它俩搭配使用的完整例子

```java
// 自定义异常
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

public class BankService {

    // throws 声明：这个方法可能会抛出 InsufficientBalanceException
    public void withdraw(double amount) throws InsufficientBalanceException {
        double balance = 100.0;

        if (amount > balance) {
            // throw 动作：当条件满足时，主动抛出异常
            throw new InsufficientBalanceException("余额不足，当前余额: " + balance);
        }
        System.out.println("取款成功: " + amount);
    }
}
```

**调用方：**
```java
public class Main {
    public static void main(String[] args) {
        BankService service = new BankService();
        try {
            service.withdraw(200);      // 调用时必须处理异常
        } catch (InsufficientBalanceException e) {
            System.out.println("出错了: " + e.getMessage());
        }
    }
}
```

---

## 4. 快速对比表

| 关键字 | 出现位置 | 作用 | 后面跟什么 |
|--------|----------|------|------------|
| `throws` | 方法签名末尾 | **声明**可能抛出的异常 | 异常类名（如 IOException） |
| `throw` | 方法体内部 | **实际抛出**异常 | 异常对象（如 new Exception()） |

---

## ✅ 简单记忆口诀

> **`throws` 是「声明可能会出事」，写在方法头；**
> **`throw` 是「我现在就要出事」，写在方法体。**

如果你还不太清楚**编译时异常（受检异常）** 和**运行时异常**的区别（这决定了什么时候必须用 `throws`），也可以问我，我可以继续帮你讲。
*/
public class Demo3 {
    public static void main(String[] args) {
        try {
            int age = getAge(-2);
            System.out.println(age);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            connectDB();
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
        }
    }

    // 非受检异常：运行时异常，可以不处理，但是一旦异常没有处理，程序会 crash
    public static int getAge(int age) {
        if (age < 0) throw new IllegalArgumentException("年龄不能为负数");

        return age;
    }

    // 受检异常：编译时异常，必须处理，否则编译失败
    public static void connectDB() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:111/db", "111", "111");
        // ...
    }
}
