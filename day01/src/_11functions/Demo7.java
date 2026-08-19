package _11functions;
/*
方法引用是 Lambda 表达式的简写：当 Lambda 里只是"简单地调用一个已有方法"时，就可以用 `::` 直接引用它。Java 中一共有 4 种，下面结合实际场景给出完整可运行的示例

先记住这 4 种的语法和适用场景：

| 类型 | 语法 | 适用场景 |
|------|------|----------|
| ① 静态方法引用 | `类名::静态方法` | 方法参数和 Lambda 一致 |
| ② 实例方法引用（特定对象） | `对象::实例方法` | 调用一个已存在的对象的方法 |
| ③ 实例方法引用（任意对象） | `类名::实例方法` | 第一个参数恰好是调用方法的那个对象 |
| ④ 构造方法引用 | `类名::new` | 用来创建对象 |

---

```java
package _03;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Demo5 {

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("张三", 8000),
                new Employee("李四", 12000),
                new Employee("王五", 6000)
        );

        // ==================== 1. 静态方法引用  类名::静态方法 ====================
        // 需求：按工资从低到高排序
        // Lambda 写法：employees.sort((e1, e2) -> Employee.compareBySalary(e1, e2));
        employees.sort(Employee::compareBySalary);
        System.out.println("1. 按工资排序：" + employees);

        // ==================== 2. 实例方法引用（某个对象）  对象::实例方法 ====================
        // 需求：用现成的打印机对象，打印每个员工
        // 关键点：printer 这个对象已经存在，直接引用它
        Printer printer = new Printer();
        // Lambda 写法：employees.forEach(e -> printer.print(e));
        employees.forEach(printer::print);

        // 注意：System.out.println 也是这种用法 —— out 就是个现成对象
        employees.forEach(System.out::println);

        // ==================== 3. 任意对象的实例方法引用  类名::实例方法 ====================
        // 需求：把员工姓名转为大写后打印
        // 关键点：没有现成对象，调用方法的对象来自 Lambda 的第一个参数
        // map(Employee::getName)     等价于  e -> e.getName()
        // map(String::toUpperCase)   等价于  s -> s.toUpperCase()
        employees.stream()
                .map(Employee::getName)
                .map(String::toUpperCase)
                .forEach(System.out::println);

        // 再举一个：统计所有员工工资总和
        int total = employees.stream()
                .mapToInt(Employee::getSalary)
                .sum();
        System.out.println("3. 工资总和：" + total);

        // ==================== 4. 构造方法引用  类名::new ====================
        // 需求：定义一个"员工工厂"，随时调用 get() 就能造出一个新员工
        // Lambda 写法：() -> new Employee()
        Supplier<Employee> factory = Employee::new;
        Employee newEmp = factory.get();
        System.out.println("4. 工厂造出的新员工：" + newEmp);
    }
}

class Employee {

    private String name;
    private int salary;

    public Employee() {                     // 无参构造，供构造方法引用使用
    }

    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    // 静态方法：比较两个员工的工资（供第 1 种方法引用使用）
    public static int compareBySalary(Employee e1, Employee e2) {
        return e1.getSalary() - e2.getSalary();
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return name + "(" + salary + ")";
    }
}

class Printer {

    // 实例方法（供第 2 种方法引用使用）
    public void print(Employee emp) {
        System.out.println("[打印机] " + emp.getName() + " 的工资是 " + emp.getSalary());
    }
}
```


### 理解要点

1. **静态方法引用** `Employee::compareBySalary`
   排序的 Comparator 要求"传两个员工，返回一个 int"，而 `compareBySalary` 正好也是"两个员工 → int"，签名完全吻合，直接引用。

2. **实例方法引用（特定对象）** `printer::print`
   打印机对象是代码里已经 new 出来的，`forEach` 每次传进来的员工会**自动作为 `print` 的参数**。

3. **实例方法引用（任意对象）** `String::toUpperCase`
   和上面最大的区别：这里**没有现成对象**。`map` 每次传进来的字符串 `s` 就是后面调用 `toUpperCase` 的那个对象，即 `(String s) -> s.toUpperCase()`。
   **快速判断口诀**：Lambda 里是 `x -> x.方法(参数)`，就把 `x.方法` 改成 `类型::方法`。

4. **构造方法引用** `Employee::new`
   `Supplier` 的 `get()` 方法不接收参数、返回一个对象，正好对应 `Employee` 的无参构造。适合用来做"对象工厂"。

注意：第 2 种写 `对象::方法`，第 3 种写 `类型::方法`，这是最容易混淆的地方。前者强调"调用谁的方法"，后者强调"谁来调用方法"。
*/
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Demo7 {

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("张三", 8000),
                new Employee("李四", 12000),
                new Employee("王五", 6000)
        );

        // ==================== 1. 静态方法引用  类名::静态方法 ====================
        // 需求：按工资从低到高排序
        // Lambda 写法：employees.sort((e1, e2) -> Employee.compareBySalary(e1, e2));
        employees.sort(Employee::compareBySalary);
        System.out.println(STR."1. 按工资排序：\{employees}");

        // ==================== 2. 实例方法引用（某个对象）  对象::实例方法 ====================
        // 需求：用现成的打印机对象，打印每个员工
        // 关键点：printer 这个对象已经存在，直接引用它
        Printer printer = new Printer();
        // Lambda 写法：employees.forEach(e -> printer.print(e));
        employees.forEach(printer::print);

        // 注意：System.out.println 也是这种用法 —— out 就是个现成对象
        employees.forEach(System.out::println);

        // ==================== 3. 任意对象的实例方法引用  类名::实例方法 ====================
        // 需求：把员工姓名转为大写后打印
        // 关键点：没有现成对象，调用方法的对象来自 Lambda 的第一个参数
        // map(Employee::getName)     等价于  e -> e.getName()
        // map(String::toUpperCase)   等价于  s -> s.toUpperCase()
        employees.stream()
                .map(Employee::getName)
                .map(String::toUpperCase)
                .forEach(System.out::println);

        // 再举一个：统计所有员工工资总和
        int total = employees.stream()
                .mapToInt(Employee::getSalary)
                .sum();
        System.out.println(STR."3. 工资总和：\{total}");

        // ==================== 4. 构造方法引用  类名::new ====================
        // 需求：定义一个"员工工厂"，随时调用 get() 就能造出一个新员工
        // Lambda 写法：() -> new Employee()
        Supplier<Employee> factory = Employee::new;
        Employee newEmp = factory.get();
        System.out.println(STR."4. 工厂造出的新员工：\{newEmp}");
    }
}

class Employee {
    private String name;
    private int salary;

    public Employee() {                     // 无参构造，供构造方法引用使用
    }

    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    // 静态方法：比较两个员工的工资（供第 1 种方法引用使用）
    public static int compareBySalary(Employee e1, Employee e2) {
        return e1.getSalary() - e2.getSalary();
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return STR."\{name}(\{salary})";
    }
}

class Printer {

    // 实例方法（供第 2 种方法引用使用）
    public void print(Employee emp) {
        System.out.println(STR."[打印机] \{emp.getName()} 的工资是 \{emp.getSalary()}");
    }
}
