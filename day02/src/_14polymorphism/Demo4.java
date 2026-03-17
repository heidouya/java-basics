package _14polymorphism;

import java.util.ArrayList;
import java.util.List;

/* 多态示例2 */
public class Demo4 {
    public static void main(String[] args) {
        FullTimeEmployee zhangSan = new FullTimeEmployee("zhangSan", "001", 10000);
        PartTimeEmployee liSi = new PartTimeEmployee("liSi", "002", 10, 20);
        InternEmployee wangWu = new InternEmployee("wangWu", "003", 1000);

        SalarySystem salarySystem = new SalarySystem();

        salarySystem.addEmployee(zhangSan);
        salarySystem.addEmployee(liSi);
        salarySystem.addEmployee(wangWu);

        salarySystem.calculateAllSalaries();
    }
}

abstract class Employee {
    protected String name;
    protected String id;

    public Employee(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public abstract double calculateSalary();

    public void display() {
        System.out.println(name + "的工资：" + calculateSalary());
    }
}

class FullTimeEmployee extends Employee {
    private final double monthlySalary;

    public FullTimeEmployee(String name, String id, double monthlySalary) {
        super(name, id);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;  // 固定月薪
    }
}

class PartTimeEmployee extends Employee {
    private final double hourlyRate;
    private final int hoursWorked;

    public PartTimeEmployee(String name, String id, double hourlyRate, int hoursWorked) {
        super(name, id);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;  // 按时计薪
    }
}

class InternEmployee extends Employee {
    private final double stipend;

    public InternEmployee(String name, String id, double stipend) {
        super(name, id);
        this.stipend = stipend;
    }

    @Override
    public double calculateSalary() {
        return stipend;  // 固定津贴
    }
}

// 使用多态处理所有员工
class SalarySystem {
    private final List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee emp) {
        employees.add(emp);
    }

    public void calculateAllSalaries() {
        double total = 0;
        for (Employee emp : employees) {
            emp.display();  // 多态：每个员工用自己的方式计算工资
            total += emp.calculateSalary();
        }
        System.out.println("总工资支出：" + total);
    }
}