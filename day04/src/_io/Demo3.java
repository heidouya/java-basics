package _io;
/*
## 根目录、绝对路径、相对路径

### 根目录（Root Directory）

根目录是文件系统的最顶层目录，是所有其他文件和文件夹的起点。

- **Windows 系统**：每个磁盘分区都有一个根目录，例如 `C:\`、`D:\`、`E:\`
- **Linux/Mac 系统**：只有一个根目录，就是 `/`

例如你的项目路径 `D:\learn\code\java\JavaBasics`，其中 `D:\` 就是 D 盘的根目录。

---

### 绝对路径（Absolute Path）

从根目录开始的完整路径，能唯一确定一个文件的位置：

```
D:\learn\code\java\JavaBasics\day01\src\_02hello\Demo1.java
```

不管你当前在哪个目录下，绝对路径始终指向同一个文件。

---

### 相对路径（Relative Path）

相对于**当前工作目录**的路径，不从根目录开始。

假设你当前在 `D:\learn\code\java\JavaBasics\day01\src` 目录下：

| 相对路径 | 实际指向 |
|---------|---------|
| `_02hello\Demo1.java` | 当前目录下的 `_02hello` 文件夹中的 `Demo1.java` |
| `.\Demo1.java` | 当前目录下的 `Demo1.java`（`.` 代表当前目录） |
| `..\day02.iml` | 上一级目录的 `day02.iml`（`..` 代表上一级目录） |
| `..\..\README.md` | 上两级目录的 `README.md` |

---

### 简单总结

| 概念 | 特点 | 示例 |
|------|------|------|
| **根目录** | 文件系统的最顶层 | `C:\`、`D:\`、`/` |
| **绝对路径** | 从根目录开始，完整路径 | `D:\learn\code\java\JavaBasics` |
| **相对路径** | 从当前位置出发 | `..\day02\src`、`./Demo1.java` |

在编程中，通常推荐使用**相对路径**，因为项目换个位置（比如换台电脑）绝对路径就失效了，而相对路径只要项目内部结构不变就始终有效。
*/
public class Demo3 {
}
