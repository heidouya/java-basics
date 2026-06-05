package _14date;
/*
`java.time.Duration` 是 Java 8 引入的时间 API 的一部分，用于表示**两个时间点之间的时间量**，精确到**纳秒**级别。

## 核心特点

- **基于时间的时间量**：表示秒和纳秒，适合处理时、分、秒、毫秒、纳秒
- **不可变且线程安全**
- 与 `LocalTime`、`LocalDateTime`、`Instant` 等配合使用

## 常见用法

```java
// 1. 创建 Duration

Duration d1 = Duration.ofSeconds(30);        // 30秒
Duration d2 = Duration.ofMinutes(5);          // 5分钟
Duration d3 = Duration.ofHours(2);            // 2小时
Duration d4 = Duration.ofDays(1);             // 1天
Duration d5 = Duration.ofMillis(500);         // 500毫秒
Duration d6 = Duration.ofNanos(1000000);      // 1000000纳秒

// 2. 计算两个时间点之间的时长
LocalTime start = LocalTime.of(10, 30);
LocalTime end = LocalTime.of(14, 45);
Duration between = Duration.between(start, end);
System.out.println(between);  // PT4H15M

// 3. 访问 Duration 的各部分
long seconds = between.getSeconds();   // 总秒数
int nano = between.getNano();          // 纳秒部分
long minutes = between.toMinutes();    // 总分钟数
long hours = between.toHours();        // 总小时数

// 4. 加减操作
Duration result = d1.plus(d2);
Duration result2 = d1.multipliedBy(3);
Duration result3 = d1.dividedBy(2);
Duration result4 = d1.negated();
```

## Duration 的字符串表示

遵循 ISO-8601 格式 `PnDTnHnMn.nS`：
- `PT5M` — 5分钟
- `PT2H30M` — 2小时30分钟
- `PT1M10.5S` — 1分钟10.5秒

## Duration vs Period

| 类 | 时间粒度 | 适用场景 |
|---|---|---|
| `Duration` | 秒/纳秒 | 处理时、分、秒等机器时间 |
| `Period` | 年/月/日 | 处理日期（日历时长） |

简单来说：**`Duration` 适合做精确的时间计算**（如计时、超时控制），而不要用它来处理"几天后"这类日历日期问题（此时用 `Period` 或 `LocalDate` 更合适）。
*/
public class Demo2 {
}
