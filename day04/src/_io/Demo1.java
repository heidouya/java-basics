package _io;

import java.io.*;

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
    public static void main(String[] args) {
        writeFile1("你好，FileWriter");
        writeFile2("你好，BufferedWriter");
        writeFile3("你好，FileOutputStream");

        System.out.println("----------FileReader---------");
        readFile1();
        System.out.println("----------FileReader---------");
        readFile2();

        System.out.println("----------BufferedReader---------");
        readFile3();

        System.out.println("----------FileInputStream---------");
        readFile4();
        System.out.println("----------FileInputStream---------");
        readFile5();
    }

    public static void writeFile1(String content) {
        try (
                FileWriter fileWriter = new FileWriter("./hello.txt")
        ) {
            fileWriter.write(content);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeFile2(String content) {
        try (
                FileWriter fileWriter = new FileWriter("./hello.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            bufferedWriter.write(content);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeFile3(String content) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("./hello.txt")
        ) {
            fileOutputStream.write(content.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readFile1() {
        // FileReader - 读文本，按字符读
        try (
                FileReader fr = new FileReader("./hello.txt")
        ){
            int len;
            while ((len = fr.read()) != -1) {
                System.out.println((char) len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readFile2() {
        // FileInputStream - 读二进制，按字节读
        try (
                FileReader fr = new FileReader("./hello.txt")
        ){
            char[] buf = new char[50];
            int len;
            while ((len = fr.read(buf)) != -1) {
                System.out.println(new String(buf, 0, len));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readFile3() {
        try (BufferedReader br = new BufferedReader(new FileReader("./hello.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readFile4() {
        try (
                FileInputStream fileInputStream = new FileInputStream("./hello.txt")
        ) {
            int data = fileInputStream.read();
            while (data != -1) {
                System.out.println((char) data);
                data = fileInputStream.read();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readFile5() {
        try (
                FileInputStream fileInputStream = new FileInputStream("./hello.txt")
        ) {
            byte[] buf = new byte[50];
            int len;
            while ((len = fileInputStream.read(buf)) != -1) {
                System.out.println(new String(buf, 0, len));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
