package _io;
/*
## Java IO 流简介

Java 中的 IO（Input/Output）流是一套用于处理数据输入和输出的抽象机制，所有数据都通过"流"的方式顺序读写。

### 1. 按数据流向分类

| 类型 | 说明 | 基类 |
|------|------|------|
| **输入流** | 从数据源读取数据到程序 | `InputStream` / `Reader` |
| **输出流** | 将数据从程序写入到目标 | `OutputStream` / `Writer` |

### 2. 按处理单位分类

| 类型 | 说明 | 基类 |
|------|------|------|
| **字节流** | 以字节（8-bit）为单位，处理二进制文件（如图片、视频） | `InputStream` / `OutputStream` |
| **字符流** | 以字符（16-bit）为单位，处理文本文件 | `Reader` / `Writer` |

### 3. 按功能角色分类

| 类型 | 说明 | 示例 |
|------|------|------|
| **节点流** | 直接连接数据源/目的地，底层流 | `FileInputStream`、`FileReader` |
| **处理流** | 包装节点流，提供额外功能（缓冲、转换等） | `BufferedReader`、`InputStreamReader` |

### 4. 常用流速查表

| 场景 | 推荐使用 |
|------|---------|
| 读取文本文件 | `BufferedReader` + `FileReader` |
| 写入文本文件 | `BufferedWriter` + `FileWriter` |
| 读取二进制文件 | `BufferedInputStream` + `FileInputStream` |
| 写入二进制文件 | `BufferedOutputStream` + `FileOutputStream` |
| 字节流→字符流转换 | `InputStreamReader` / `OutputStreamWriter` |
| 对象序列化 | `ObjectInputStream` / `ObjectOutputStream` |

### 5. 核心示例（读取文本文件）

```java
try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

> **注意**：Java 7 引入了 **try-with-resources** 语法，实现了 `AutoCloseable` 的流可以自动关闭，推荐使用。

### 6. 总结

- **字节流** → 通用，处理一切文件
- **字符流** → 专为文本优化，处理中英文更方便
- **缓冲流** → 包装其他流，提升性能（减少磁盘 I/O 次数）
- **转换流** → 桥接字节流和字符流，解决编码问题
*/
public class Demo1 {
}
