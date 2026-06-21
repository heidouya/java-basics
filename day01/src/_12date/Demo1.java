package _12date;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/*
# Java 中时间与日期处理（简要介绍）

Java 中日期时间 API 经历了几个重要阶段：

---

## 一、早期 API（Java 8 之前）—— 不推荐使用

### 1. `java.util.Date`
- 表示一个特定的时间戳（精确到毫秒）
- 大部分方法已废弃，设计混乱，**不推荐直接使用**

### 2. `java.util.Calendar` + `GregorianCalendar`
- 比 `Date` 稍微好用一点，可以获取年、月、日等字段
- 但月份从 `0` 开始（0=1月），容易出错
- 线程不安全，可变对象

### 3. `java.text.SimpleDateFormat`
- 用于 `Date` 与字符串之间的格式化/解析
- **线程不安全**，多线程环境下需加锁或每次新建实例

```java
// 旧 API 示例（仅作了解）
Date now = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String str = sdf.format(now);   // Date -> String
Date date = sdf.parse("2026-06-03 10:30:00"); // String -> Date
```

---

## 二、新 API（Java 8+）—— 强烈推荐 `java.time` 包

灵感来源于 Joda-Time，设计更合理、线程安全、不可变。

### 核心类：

| 类名 | 用途 | 特点 |
|------|------|------|
| `LocalDate` | 日期（年-月-日） | 不含时间和时区 |
| `LocalTime` | 时间（时:分:秒.纳秒） | 不含日期和时区 |
| `LocalDateTime` | 日期+时间 | 不含时区 |
| `ZonedDateTime` | 带时区的日期时间 | 用于跨时区场景 |
| `Instant` | 时间戳（从1970-01-01T00:00:00Z开始的纳秒数） | 机器时间 |
| `Duration` | 时间间隔（时、分、秒、纳秒） | 基于秒/纳秒 |
| `Period` | 日期间隔（年、月、日） | 基于日期 |
| `DateTimeFormatter` | 格式化/解析 | **线程安全**，可复用 |

### 常用操作示例：

```java
// 获取当前日期/时间
LocalDate today = LocalDate.now();             // 2026-06-03
LocalTime now = LocalTime.now();               // 10:30:00.123
LocalDateTime dt = LocalDateTime.now();        // 2026-06-03T10:30:00.123

// 创建指定日期
LocalDate date = LocalDate.of(2026, 6, 3);
LocalDateTime dt2 = LocalDateTime.of(2026, 6, 3, 10, 30);

// 日期操作（返回新对象，原对象不变）
LocalDate tomorrow = today.plusDays(1);
LocalDate lastMonth = today.minusMonths(1);
boolean isBefore = date1.isBefore(date2);

// 获取字段
int year = today.getYear();
int month = today.getMonthValue();  // 1~12，不会犯旧 API 的错误
DayOfWeek dow = today.getDayOfWeek(); // TUESDAY

// 格式化（线程安全！）
DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
String str = dt.format(fmt);                   // -> "2026-06-03 10:30:00"
LocalDateTime parsed = LocalDateTime.parse("2026-06-03 10:30:00", fmt);

// 时间戳
Instant instant = Instant.now();               // 当前UTC时间戳
long epochMillis = instant.toEpochMilli();     // 转毫秒
```

---

## 三、新旧 API 转换

```java
// Date -> Instant -> LocalDateTime
Date oldDate = new Date();
Instant instant = oldDate.toInstant();
LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

// LocalDateTime -> Instant -> Date
LocalDateTime ldt2 = LocalDateTime.now();
Instant instant2 = ldt2.atZone(ZoneId.systemDefault()).toInstant();
Date newDate = Date.from(instant2);
```

---

## 四、最佳实践总结

1. **永远优先使用 `java.time`（Java 8+ 新 API）**
2. 如果需要与旧代码兼容才使用 `Date`/`Calendar`，并尽快转换
3. 格式化使用 `DateTimeFormatter`（线程安全），不要用 `SimpleDateFormat`
4. 数据库中存储日期时间，建议使用 `TIMESTAMP` + UTC，应用的 `ZonedDateTime` 做时区转换
5. 时间间隔计算用 `Duration`（短时间）或 `Period`（日期跨度），而不是手动计算毫秒差

---
*/
public class Demo1 {
    public static void main(String[] args) {
        System.out.println("-------------Date（旧 API，不推荐）-------------");


        // 创建 Date 对象，表示当前时间
        Date date1 = new Date();
        // 直接打印 Date 对象（调用 toString()）
        System.out.println(STR."date1 = \{date1}");

        
        // 从 Date 对象中提取年、月、日、时、分、秒
        // 注意：以下方法均来自 Java 8 之前的旧 API，已被标记为 @Deprecated，不推荐继续使用
        // getYear() 返回的是“年份 - 1900”的偏移量，getMonth() 返回 0~11（0 表示一月），容易出错
        int year1 = date1.getYear();
        int month1 = date1.getMonth();
        int day1 = date1.getDate();
        int hour1 = date1.getHours();
        int minute1 = date1.getMinutes();
        int second1 = date1.getSeconds();
        System.out.println(STR."year1 = \{year1}, month1 = \{month1}, day1 = \{day1}, hour1 = \{hour1}, minute1 = \{minute1}, second1 = \{second1}");

        // 获取时间戳（自1970年1月1日0时0分0秒以来的毫秒数）
        long time = date1.getTime();
        System.out.println(STR."time = \{time}");

        // 获取当前时间戳（自1970年1月1日0时0分0秒以来的毫秒数）
        System.out.println(STR."currentTimeMillis = \{System.currentTimeMillis()}");

        // 创建日期格式化器，指定格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将 Date 格式化为字符串
        String str = sdf.format(date1);

        System.out.println(STR."str = \{str}");

        try {
            // 将字符串解析为 Date 对象
            Date date2 = sdf.parse("2026-06-03 10:30:00");
            System.out.println(STR."date2 = \{date2}");
        } catch (Exception e) {
            System.out.println(STR."Error: \{e.getMessage()}");
        }


        System.out.println("-------------java.time（新 API，推荐）-------------");


        // 获取当前日期（年月日），如 2026-06-03
        LocalDate today = LocalDate.now();
        // 获取当前时间（时分秒纳秒），如 10:30:00.123
        LocalTime now = LocalTime.now();
        // 获取当前日期+时间，如 2026-06-03T10:30:00.123
        LocalDateTime dt = LocalDateTime.now();

        int year = dt.getYear();
        int month = dt.getMonthValue();
        int day = dt.getDayOfMonth();
        int hour = dt.getHour();
        int minute = dt.getMinute();
        int second = dt.getSecond();
        int nano = dt.getNano();
        System.out.println(STR."year = \{year}, month = \{month}, day = \{day}, hour = \{hour}, minute = \{minute}, second = \{second}, nano = \{nano}");

        System.out.println(STR."today = \{today}");
        System.out.println(STR."now = \{now}");
        System.out.println(STR."dt = \{dt}");


        System.out.println("-------------LocalDate（日期操作）-------------");


        // 创建指定日期：2026年6月3日
        LocalDate date2 = LocalDate.of(2026, 6, 3);
        // 创建指定日期时间：2026年6月3日10点30分
        LocalDateTime dt2 = LocalDateTime.of(2026, 6, 3, 10, 30);

        System.out.println(STR."date2 = \{date2}");
        System.out.println(STR."dt2 = \{dt2}");

        // 日期操作（返回新对象，原对象不变，体现了不可变性）
        // 当前日期加 1 天，得到明天
        LocalDate tomorrow = today.plusDays(1);
        // 当前日期减 1 个月，得到上个月同一天
        LocalDate lastMonth = today.minusMonths(1);
        // 判断 today 是否在 tomorrow 之前
        boolean isBefore = today.isBefore(tomorrow);

        System.out.println(STR."tomorrow = \{tomorrow}");
        System.out.println(STR."lastMonth = \{lastMonth}");
        System.out.println(STR."isBefore = \{isBefore}");

        // 获取年份，如 2026
        int year2 = today.getYear();
        // 获取月份 1~12（注意：不是 0~11，比旧 API 更直观）
        int month2 = today.getMonthValue();
        // 获取星期几，返回枚举 DayOfWeek（如 TUESDAY）
        DayOfWeek dow = today.getDayOfWeek();

        System.out.println(STR."year2 = \{year2}, month2 = \{month2}, dow = \{dow}");


        System.out.println("-------------LocalTime（时间操作）-------------");  // 打印分隔线


        // 创建格式化器，指定格式
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将 LocalDateTime 格式化为字符串，如 "2026-06-03 10:30:00"
        String dtStr = dt.format(fmt);

        System.out.println(STR."dtStr = \{dtStr}");

        // 将字符串解析为 LocalDateTime 对象
        LocalDateTime parsed = LocalDateTime.parse("2026-06-03 10:30:00", fmt);

        System.out.println(STR."parsed = \{parsed}");


        System.out.println("-------------Instant（时间戳）-------------");


        // 获取当前 UTC 时间戳
        Instant instant = Instant.now();
        // 将 Instant 转换为从1970-01-01T00:00:00Z开始的毫秒数
        long epochMillis = instant.toEpochMilli();

        System.out.println(STR."instant = \{instant}");
        System.out.println(STR."epochMillis = \{epochMillis}");
    }
}
