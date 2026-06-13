package _io;

import java.io.FileInputStream;
import java.io.IOException;

/*
## FileReader 与 FileInputStream 的区别

### 核心区别：字符 vs 字节

| 特性 | `FileReader` | `FileInputStream` |
|------|-------------|-------------------|
| 数据单位 | **字符**（char，2字节） | **字节**（byte，1字节） |
| 父类 | `Reader`（字符流） | `InputStream`（字节流） |
| 适用场景 | 读取**文本文件** | 读取**任意文件**（图片、音频、二进制等） |
| 编码处理 | 自动进行字节→字符转换 | 不处理编码，原始字节 |
| 默认编码 | 使用系统默认编码 | 无编码概念 |

### 编码问题

`FileReader` 使用平台默认编码（Windows 上常为 GBK），**无法手动指定编码**，可能导致乱码。若需指定编码，应使用：

```java
// 推荐：可指定编码
InputStreamReader reader = new InputStreamReader(new FileInputStream("file.txt"), StandardCharsets.UTF_8);
```

### 使用示例对比

```java
// FileReader - 读文本，按字符读
FileReader fr = new FileReader("hello.txt");
int ch;
while ((ch = fr.read()) != -1) {
    System.out.print((char) ch);
}

// FileInputStream - 读任意文件，按字节读
FileInputStream fis = new FileInputStream("image.png");
int b;
while ((b = fis.read()) != -1) {
    // 处理字节数据
}
```
### 总结

- 读**文本**且不关心编码 → 可用 `FileReader`（简单快捷）
- 读**文本**且需指定编码 → 用 `InputStreamReader` 包装 `FileInputStream`
- 读**二进制文件**（图片、视频等）→ 必须用 `FileInputStream`
*/
public class Demo4 {
    public static void main(String[] args) {


        System.out.println("-------------------------------------------------");

        // FileInputStream - 读任意文件，按字节读
        try (
                FileInputStream fis = new FileInputStream("hello.txt")
        ){
            int b;
            while ((b = fis.read()) != -1) {
                // 处理字节数据
                System.out.println((char) b);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
