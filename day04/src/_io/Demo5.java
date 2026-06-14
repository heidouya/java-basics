package _io;

import java.io.*;

/*
# Java 序列化与反序列化

## 概念

- **序列化**：将 Java 对象转换为字节流（便于存储或网络传输）
- **反序列化**：将字节流还原为 Java 对象

## 基本使用

### 1. 实现 `Serializable` 接口

```java
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L; // 版本号
    private String name;
    private int age;

    // 构造器、getter/setter...
}
```

### 2. 序列化（对象 → 文件）

```java
import java.io.*;

User user = new User("张三", 18);

try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream("user.dat"))) {
    oos.writeObject(user);
}
```

### 3. 反序列化（文件 → 对象）

```java
try (ObjectInputStream ois = new ObjectInputStream(
        new FileInputStream("user.dat"))) {
    User user = (User) ois.readObject();
    System.out.println(user.getName()); // 张三
}
```

## 关键点

| 要点 | 说明 |
|------|------|
| `Serializable` | 标记接口，必须实现，否则抛 `NotSerializableException` |
| `serialVersionUID` | 版本号，保证序列化兼容性，建议手动声明 |
| `transient` | 修饰的字段**不参与**序列化（如密码） |
| `static` 字段 | 属于类，不参与序列化 |

```java
private transient String password; // 不会被序列化
```

## 常见应用场景

- 对象持久化到文件/数据库
- 网络传输对象（RPC、Socket）
- 深拷贝对象（序列化后再反序列化）
- 分布式系统中的数据传递（如 Redis 缓存对象）
*/
public class Demo5 {
    public static void main(String[] args) {
        User user1 = new User("张三", "123456");
        writeFile("./hello.txt", user1);

        System.out.println("-------------------------------------------------");

        User user2 = (User) readFile("./hello.txt");
        if (user2 != null) {
            System.out.println(user2);
        }
    }

    public static void writeFile(String src, Object content) {
        try (
                FileOutputStream fos = new FileOutputStream(src);
                ObjectOutputStream oos = new ObjectOutputStream(
                fos)
        ) {
            oos.writeObject(content);
            System.out.println("对象已写入文件");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object readFile(String src) {
        try (
                FileInputStream fis = new FileInputStream(src);
                ObjectInputStream ois = new ObjectInputStream(
                fis)
        ) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // 版本号
    private final String name;
    private final transient String password; // 不会被序列化

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String toString() {
        return STR."User{name='\{name}', password=\{password}}";
    }
}
