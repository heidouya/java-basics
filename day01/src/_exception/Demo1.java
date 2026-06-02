package _exception;

/*
## Java 中的异常（Exception）

异常是程序运行时出现的不正常情况，Java 通过异常处理机制让程序能优雅地处理错误，而不是直接崩溃。

### 异常体系结构

```
Throwable（可抛出的）
├── Error（错误）—— 程序无法处理的严重问题
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── ...
└── Exception（异常）—— 程序可以处理的
    ├── RuntimeException（运行时异常 / 非受检异常）
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── ArithmeticException（如除以0）
    │   └── ...
    └── 其他异常（受检异常 Checked Exception）
        ├── IOException
        ├── SQLException
        ├── FileNotFoundException
        └── ...
```

### 受检异常 vs 非受检异常

受检异常（Checked Exception）：编译器强制要求你必须处理，否则代码无法通过编译。通常用于表示外部问题，如文件不存在、网络连接失败等。

非受检异常（Unchecked Exception）：编译器不强制要求你必须处理，但建议处理。通常用于表示程序逻辑错误，如空指针、数组越界等。

| 类型 | 特点 | 处理要求 | 常见例子 |
|------|------|----------|----------|
| **受检异常**（Checked） | 编译时强制处理 | 必须 `try-catch` 或 `throws` 抛出 | `IOException`, `SQLException` |
| **非受检异常**（RuntimeException） | 运行时才可能发生 | 不强制处理，可规避 | `NullPointerException`, `ArithmeticException` |
| **Error** | 严重系统错误 | 不建议捕获 | `OutOfMemoryError` |

### 异常处理方式

```java
// 方式1：try-catch-finally
try {
    int result = 10 / 0;              // 可能抛出异常
    FileReader fr = new FileReader("test.txt");
} catch (ArithmeticException e) {     // 捕获特定异常
    System.out.println("数学运算异常：" + e.getMessage());
} catch (IOException e) {             // 可多重捕获
    e.printStackTrace();               // 打印异常堆栈（调试用）
} catch (Exception e) {               // 父类异常兜底（放最后）
    System.out.println("其他异常");
} finally {                           // 始终执行（释放资源）
    System.out.println("清理工作");
}

// 方式2：try-with-resources（Java 7+，自动关闭资源）
try (
    FileReader fr = new FileReader("test.txt");
    BufferedReader br = new BufferedReader(fr)
) {
    String line = br.readLine();
} catch (IOException e) {
    e.printStackTrace();
} // 无需 finally，资源自动关闭

// 方式3：throws 声明抛出（交给调用者处理）
public void readFile(String path) throws IOException {
    FileReader fr = new FileReader(path);
    // ...
}

// 方式4：throw 手动抛出
if (age < 0) {
    throw new IllegalArgumentException("年龄不能为负数");
}
```

### 自定义异常

```java
// 自定义受检异常
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// 自定义运行时异常
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

// 使用
public void withdraw(double amount) throws InsufficientBalanceException {
    if (amount > balance) {
        throw new InsufficientBalanceException("余额不足，当前余额：" + balance);
    }
    balance -= amount;
}
```

### 最佳实践

1. **使用具体异常类型**，不要只捕获 `Exception`
2. **不要吞掉异常**（空的 catch 块），至少打印日志
3. **尽量使用 try-with-resources** 替代手动 `finally` 关闭资源
4. **异常只用于异常情况**，不要用异常控制正常流程
5. **抛出异常时附带有用信息**：`throw new Exception("具体原因：" + detail)`
6. **避免在 finally 中 return**，会覆盖 try/catch 中的返回值

### 小结

| 要点 | 说明 |
|------|------|
| 核心思想 | 将错误处理代码与正常业务逻辑分离 |
| 体系 | `Error`（不可恢复）→ `Exception`（可处理）→ `RuntimeException`（非强制处理）|
| 处理方式 | `try-catch-finally` / `throws` / `try-with-resources` |
| 常用方法 | `getMessage()`、`printStackTrace()`、`getCause()` |
*/
public class Demo1 {
    public static void main(String[] args) {
        System.out.println("------------------Error------------------");
        // OutOfMemoryError
        //int[] arr = new int[1024 * 1024 * 1024];

        // StackOverflowError
        //main(args);

        System.out.println("------------------Exception------------------");
        // 非受检异常
        // NullPointerException
        //String str = null;
        //System.out.println(str.length());

        // 受检异常 SQLException
        //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "user", "password");
    }
}
