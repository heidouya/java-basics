package _io;

import java.io.File;
import java.io.IOException;

/*
## Java File 类简介

`java.io.File` 是 Java 中用于**表示文件和目录路径名**的抽象表示，但它**不代表文件的内容**，只是文件或目录的元数据描述。

### 1. 核心概念

File 对象可以表示：
- **文件**（如 `test.txt`）
- **目录**（如 `D:/data/`）
- **不存在的路径**（只是路径名，不检查是否存在）

### 2. 常用构造方法

```java
File f1 = new File("test.txt");                    // 相对路径
File f2 = new File("D:/data", "test.txt");         // 父路径 + 子路径
File f3 = new File(new File("D:/data"), "test.txt");// File对象 + 子路径
```

### 3. 常用方法速查

| 分类 | 方法 | 说明 |
|------|------|------|
| **判断** | `exists()` | 文件/目录是否存在 |
| | `isFile()` | 是否为文件 |
| | `isDirectory()` | 是否为目录 |
| **获取信息** | `getName()` | 获取文件名 |
| | `getPath()` | 获取路径 |
| | `getAbsolutePath()` | 获取绝对路径 |
| | `length()` | 获取文件大小（字节） |
| | `lastModified()` | 最后修改时间戳 |
| **操作** | `createNewFile()` | 创建新文件 |
| | `mkdir()` / `mkdirs()` | 创建目录（`mkdirs` 创建多级） |
| | `delete()` | 删除文件或空目录 |
| | `renameTo(File)` | 重命名/移动 |
| **遍历目录** | `list()` | 返回子文件/目录名数组（String[]） |
| | `listFiles()` | 返回子文件/目录对象数组（File[]） |

### 4. 示例代码

```java
File file = new File("D:/learn/hello.txt");

// 判断与信息
System.out.println("是否存在：" + file.exists());
System.out.println("是否是文件：" + file.isFile());
System.out.println("文件名：" + file.getName());
System.out.println("绝对路径：" + file.getAbsolutePath());
System.out.println("文件大小：" + file.length() + " 字节");

// 遍历目录
File dir = new File("D:/learn/code/java/JavaBasics/day04");
File[] files = dir.listFiles();
if (files != null) {
    for (File f : files) {
        System.out.println(f.getName() + (f.isDirectory() ? " [目录]" : ""));
    }
}
```

### 5. File 的局限性

随着 Java 版本发展，`File` 的一些不足逐渐显现：

| 问题 | 说明 |
|------|------|
| **不支持符号链接** | 无法处理软链接等高级文件系统特性 |
| **方法不抛出异常** | `delete()` 失败时返回 false，但不抛出异常，难以排查 |
| **性能一般** | 遍历大目录时效率不高 |
| **只能操作路径元数据** | 不能直接读写文件内容 |

### 6. Java 7+ 的改进：Path & Files（NIO.2）

Java 7 引入了 `java.nio.file.Path` 和 `java.nio.file.Files`，推荐在新项目中使用：

```java
Path path = Paths.get("D:/learn/hello.txt");
boolean exists = Files.exists(path);
long size = Files.size(path);
Files.createFile(path);
Files.delete(path);
```

### 总结

| 场景 | 推荐使用 |
|------|---------|
| 简单文件属性判断 | `File`（够用且简洁） |
| 遍历目录、文件操作 | `File` + `listFiles()` |
| 复杂文件系统操作、性能要求高 | `Path` + `Files`（NIO.2） |

> `File` 是传统方式，简单直观，适合入门学习；`Path`/`Files` 更强大、更安全，是官方推荐的新方向。
*/
public class Demo2 {
    public static void main(String[] args) {
        File file = new File("hello.txt");

        boolean exists = file.exists();
        // 判断与信息
        System.out.println(STR."是否存在：\{exists}");

        if (!exists) {
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                System.out.println(STR."创建文件失败：\{e.getMessage()}");
            }
        }

        System.out.println(STR."是否是文件：\{file.isFile()}");
        System.out.println(STR."文件名：\{file.getName()}");
        System.out.println(STR."绝对路径：\{file.getAbsolutePath()}");
        System.out.println(STR."文件大小：\{file.length()} 字节");
        // 相对路径是相对于进程的工作目录，不是源代码文件的位置。
        System.out.println(STR."当前工作目录：\{System.getProperty("user.dir")}");

        System.out.println("------------------listFiles 遍历目录--------------");

        // 遍历目录
        File dir = new File("D:/learn/code/java/JavaBasics/day04/src/_io");
        // listFiles() 返回子文件/目录对象数组（File[]）
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                System.out.println(f.getName() + (f.isDirectory() ? " [目录]" : ""));
            }
        }

        System.out.println("------------------list 遍历目录--------------");

        // list() 返回子文件/目录名数组（String[]
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }
    }
}
