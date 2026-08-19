package _02collections;

import java.util.stream.IntStream;

/*
# Stream 是什么

Stream 是 Java 8 引入的**数据处理流水线**，专门用来对集合、数组做批量操作。它本身**不是集合、不存储数据**，你可以把它想象成工厂里的传送带：

```
原始数据 → 过滤工序 → 转换工序 → 排序工序 → 出口收集结果
```


## 三个核心特点

| 特点 | 说明 |
|------|------|
| 不存储数据 | Stream 只是流水线，元素仍存在原集合里 |
| 惰性求值 | 中间操作不立刻执行，必须等到终止操作才真正"开工" |
| 一次性 | Stream 用完不能复用，想再处理需要重新创建 |

## 三步公式

```
创建 Stream → 中间操作（0~N 个） → 终止操作（必须有 1 个）
```
```java
// 创建
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> s = list.stream();                    // ① 集合
String[] arr = {"a", "b", "c"};
Stream<String> s2 = Arrays.stream(arr);              // ② 数组
Stream<String> s3 = Stream.of("a", "b", "c");        // ③ 直接创建
```


## 常用操作速查表

| 分类 | 方法 | 作用 |
|------|------|------|
| 中间操作（返回 Stream，可链式） | `filter` | 过滤：留下符合条件的 |
| | `map` | 转换：把 A 变成 B |
| | `distinct` | 去重 |
| | `sorted` | 排序 |
| | `limit(n)` | 只要前 n 个 |
| | `skip(n)` | 跳过前 n 个 |
| | `peek` | 偷看流水线中间的数据（调试用） |
| 终止操作（返回最终结果） | `forEach` | 遍历 |
| | `collect` | 收集回集合（最常用） |
| | `count` | 计数 |
| | `sum` / `average` / `max` / `min` | 数值统计 |
| | `reduce` | 归约：一步步合并 |
| | `anyMatch` / `allMatch` / `noneMatch` | 判断是否符合条件 |

记住一个规律：**返回值是 Stream 的就是中间操作，可以一直 `.` 下去；返回值是别的类型（List、long、boolean 等）的就是终止操作，到这里就收尾了。**

## 完整示例（贴近实际需求）

沿用之前的 `Employee` 类，存成 `day03/src/_03/Demo6.java`：

```java
package _03;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo6 {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 8000),
                new Employee("李四", 12000),
                new Employee("王五", 6000),
                new Employee("赵六", 15000),
                new Employee("王五", 6000)   // 故意留一个重复，演示 distinct
        );

        // 需求1：找出工资 > 8000 的员工姓名，收集成新集合
        // 流水线：filter(过滤) -> map(取姓名) -> collect(收结果)
        List<String> highSalaryNames = employees.stream()
                .filter(e -> e.getSalary() > 8000)
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("1. 高薪员工：" + highSalaryNames);

        // 需求2：去重后按工资降序排列
        List<Employee> sorted = employees.stream()
                .distinct()
                .sorted((e1, e2) -> e2.getSalary() - e1.getSalary())
                .collect(Collectors.toList());
        System.out.println("2. 去重降序：" + sorted);

        // 需求3：统计总工资和平均工资
        // 注意：数值统计要先用 mapToInt 转成 IntStream
        int total = employees.stream().mapToInt(Employee::getSalary).sum();
        double avg = employees.stream().mapToInt(Employee::getSalary).average().orElse(0);
        System.out.println("3. 总工资：" + total + "，平均工资：" + avg);

        // 需求4：判断"有没有工资超 1 万的"、"是不是没人破 2 万"
        boolean hasRich = employees.stream().anyMatch(e -> e.getSalary() > 10000);
        boolean noSuperRich = employees.stream().noneMatch(e -> e.getSalary() > 20000);
        System.out.println("4. 有工资破万？" + hasRich + "，没人破两万？" + noSuperRich);

        // 需求5：找出工资最高的人
        // max 返回 Optional，用 orElse(null) 避免空指针
        Employee richest = employees.stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElse(null);
        System.out.println("5. 工资最高：" + richest);

        // 需求6：用 count 统计高薪人数
        long count = employees.stream().filter(e -> e.getSalary() > 8000).count();
        System.out.println("6. 高薪人数：" + count);

        // 需求7：演示惰性求值 —— 没有终止操作，filter 不会执行！
        employees.stream().filter(e -> {
            System.out.println("filter 执行了：" + e.getName());
            return e.getSalary() > 8000;
        });
        System.out.println("7. 上面没有打印任何 filter 行，说明没有终止操作就不开工");

        // 需求8：演示"一次性" —— 同一个 Stream 只能用一次
        Stream<Employee> stream = employees.stream();
        stream.forEach(System.out::println);
        // stream.forEach(System.out::println);   // 取消注释会报错：stream has already been operated upon or closed
    }
}
```


`distinct()` 去重要求 `Employee` 必须重写 `equals` 和 `hashCode`，在 `Employee` 类里补上即可：

```java
// 在 Employee 类中补上 equals 和 hashCode
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return salary == employee.salary && name.equals(employee.name);
}

@Override
public int hashCode() {
    return Objects.hash(name, salary);
}
```


## 两个最容易踩的坑

1. **没有终止操作 = 白写**。中间操作只是"预约"，不会真正执行，必须 `collect` / `forEach` / `count` 等收尾。
2. **Stream 是一次性的**。用完想再处理，必须重新 `list.stream()`。

## 补充：并行流

把 `.stream()` 换成 `.parallelStream()`，数据量大的时候 Java 会**自动多线程并行处理**，代码一行都不用改。但对新手来说，先掌握串行 `stream()` 就足够了。

---

简单记住一句话就够了：**Stream = 集合的批量处理流水线，`filter` 过滤、`map` 转换、`collect` 收结果，是三个最常用的搭档**。
*/
public class Demo11 {
    public static void main(String[] args) {
        // 求1-100的和
        int sum = IntStream.rangeClosed(0, 100)
                .sum();

        System.out.println(sum);
    }
}
