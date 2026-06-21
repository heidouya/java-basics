package _12date;
/*
`java.time.Period` 是 Java 8 引入的时间 API，用于表示**基于日期的时间量**，以**年、月、日**为单位。

## 核心特点

- **基于日期的时间量**：处理年、月、日，不涉及时、分、秒
- **不可变且线程安全**
- 主要与 `LocalDate` 配合使用

## 常见用法

```java
// 1. 创建 Period

Period p1 = Period.ofDays(10);            // 10天
Period p2 = Period.ofMonths(3);           // 3个月
Period p3 = Period.ofYears(2);            // 2年
Period p4 = Period.of(1, 6, 15);          // 1年6个月15天
Period p5 = Period.ofWeeks(3);            // 21天（3周）

// 2. 计算两个日期之间的时长
LocalDate start = LocalDate.of(2024, 1, 1);
LocalDate end = LocalDate.of(2026, 6, 3);
Period between = Period.between(start, end);
System.out.println(between);  // P2Y5M2D

// 3. 访问各部分
int years = between.getYears();
int months = between.getMonths();
int days = between.getDays();

// 4. 加减操作
Period result = p1.plus(p2);
Period result2 = p1.multipliedBy(2);
Period result3 = p1.negated();
```

## Period 的字符串表示

ISO-8601 格式 `PnYnMnD`：
- `P2Y` — 2年
- `P3M` — 3个月
- `P10D` — 10天
- `P1Y6M15D` — 1年6个月15天

## Duration vs Period 对比

| 特性 | `Duration` | `Period` |
|---|---|---|
| **时间粒度** | 秒/纳秒 | 年/月/日 |
| **适用类** | `LocalTime`、`LocalDateTime`、`Instant` | `LocalDate` |
| **精度** | 高精度（纳秒级） | 低精度（天级） |
| **典型场景** | 计时、超时、算法耗时 | 计算年龄、到期日、日期偏移 |

简单来说：**`Period` 用于处理"多久之后"这类日历日期计算**（比如"3个月后"、"2年5天前"），而 `Duration` 用来做精确的时间差计算。
*/
public class Demo3 {
}
