package _05types;
/*
## Java 正则表达式简介

正则表达式（Regular Expression）是一种用于匹配、查找和替换字符串的强大工具。Java 从 1.4 版本开始通过 `java.util.regex` 包提供支持。

### 核心类

| 类 | 用途 |
|---|------|
| `Pattern` | 编译正则表达式，表示一个模式对象 |
| `Matcher` | 对字符串执行匹配操作的引擎 |
| `PatternSyntaxException` | 正则表达式语法错误时的异常 |

### 常用方法

```java
// 1. 匹配 —— 判断是否完全匹配
boolean isMatch = Pattern.matches("\\d+", "123"); // true

// 2. 查找 —— 使用 Pattern 和 Matcher
Pattern p = Pattern.compile("a.b");           // a开头、b结尾，中间任意字符
Matcher m = p.matcher("a2b aXb acb");
while (m.find()) {
    System.out.println(m.group());             // 输出 a2b aXb acb
}

// 3. 替换
String s = "Hello 123 World 456";
String result = s.replaceAll("\\d+", "数字");  // "Hello 数字 World 数字"

// 4. 分割
String[] parts = "a,b,c".split(",");          // ["a", "b", "c"]
```

### 常用正则语法

| 语法 | 含义 | 示例 |
|------|------|------|
| `.` | 任意字符（除换行） | `a.b` 匹配 `a1b` |
| `\d` | 数字 `[0-9]` | `\d{3}` 匹配三位数 |
| `\w` | 单词字符 `[a-zA-Z0-9_]` | `\w+` 匹配单词 |
| `\s` | 空白字符 | 空格、制表符、换行 |
| `*` | 0次或多次 | `a*` 匹配 `""`, `"a"`, `"aaa"` |
| `+` | 1次或多次 | `\d+` 匹配至少一位数字 |
| `?` | 0次或1次 | `colou?r` 匹配 `color`, `colour` |
| `{n,m}` | n到m次 | `\d{2,4}` 匹配2~4位数字 |
| `^` | 行的开头 | `^Hello` 匹配行首的 Hello |
| `$` | 行的结尾 | `end$` 匹配行尾的 end |
| `[]` | 字符集 | `[aeiou]` 匹配任意元音 |
| `()` | 分组捕获 | `(\\w+)@(\\w+)` 可提取用户名和域名 |

### 分组捕获示例

```java
Pattern p = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");
Matcher m = p.matcher("日期：2026-06-01");
if (m.find()) {
    System.out.println(m.group(0));  // 2026-06-01（整个匹配）
    System.out.println(m.group(1));  // 2026（年）
    System.out.println(m.group(2));  // 06（月）
    System.out.println(m.group(3));  // 01（日）
}
```

### 注意事项

- `.` 默认不匹配换行符，`Pattern.DOTALL` 标志可改变
- 在 Java 字符串中，反斜杠需要转义，如 `\d` 要写成 `"\\d"`
- 大量重复匹配时，`String.matches()` 内部每次都会重新编译 Pattern，建议复用
- 常用标志：`Pattern.CASE_INSENSITIVE`（忽略大小写）、`Pattern.MULTILINE`（多行模式）

正则表达式是文本处理的利器，掌握后可以极大提升字符串操作的效率和代码简洁性。
*/
public class Demo9 {
    public static void main(String[] args) {
        // 验证手机号
        String phone = "13800138000";
        String regex1 = "1[3-9]\\d{9}";
        System.out.println(phone.matches(regex1));

        // 验证邮箱
        String email = "example@example.com";
        String regex2 = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        System.out.println(email.matches(regex2));

        // 验证身份证
        String idCard = "11010519491231002X";
        String regex3 = "[1-9]\\d{5}(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]$";
        System.out.println(idCard.matches(regex3));
    }
}
