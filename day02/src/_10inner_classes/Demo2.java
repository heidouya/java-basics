package _10inner_classes;
/*
# 为什么需要四种内部类

这四种内部类的本质区别在于**"和外部类的关系不同"**：是否依赖外部类对象、能访问哪些成员、生命周期多大。因为实际开发中有不同的需求，Java 才设计了四种来对应。

---

## 1. 成员内部类 —— "我离不开外部类"

**场景**：两个类逻辑上强关联，且**内部类必须依赖外部类对象**才能工作。

典型例子：
- **迭代器模式**：集合类要遍历元素，比如 `ArrayList` 内部就有 `Itr` 内部类，它需要访问外部集合的数据结构和修改次数（modCount），和外部类是强绑定关系

```java
// 简化版 ArrayList 迭代器思路
class MyList {
    private String[] data;

    class MyIterator {          // 成员内部类
        int index = 0;
        boolean hasNext() {
            return index < data.length;   // 直接访问外部类的 data
        }
    }
}
```


**为什么不拆成普通类**：如果定义成独立类，就需要把集合内部的数据通过构造方法传进去，既啰嗦又暴露内部细节；内部类天然拥有外部类的引用（`Outer.this`），最合适。

---

## 2. 静态内部类 —— "我只是和外部类放在一起"

**场景**：这个类**不依赖外部类对象**，但和外部类逻辑上属于"一家人"，放一起更合理。

典型例子：
- **数据结构中的节点**：`LinkedList.Node`、`HashMap.Node` —— 节点单独用没意义，但也不需要引用外层 Map 对象
- **`Map.Entry`**：键值对条目，JDK 官方就用静态内部类设计
- **Builder 模式大量使用**：比如建造者模式中，`Builder` 常用静态内部类，这样 `User.Builder` 调用起来很优雅

```java
class User {
    private String name;

    // 建造者不依赖 User 对象，只是"配套"工具，因此用静态内部类
    static class Builder {
        private String name;
        Builder setName(String name) { this.name = name; return this; }
        User build() { return new User(name); }
    }
}
// 使用：new User.Builder().setName("小明").build()
```


**和成员内部类的区别**：静态内部类**不会持有外部类对象的引用**，不会造成内存泄漏，创建也更轻量。

---

## 3. 局部内部类 —— "只在本方法里临时用"

**场景**：这个类只在某个方法内部使用，其他地方完全用不到。

实际开发中**用得极少**，因为更好的替代方案是匿名内部类。如果逻辑复杂、需要复用多次，可以定义局部内部类；如果很简单，直接用匿名类。

```java
class Calculator {
    void process(int[] nums) {
        // 只在方法内临时使用，也算一个局部内部类的场景
        class Helper {
            int sum(int[] arr) {
                int total = 0;
                for (int n : arr) total += n;
                return total;
            }
        }
        Helper helper = new Helper();
        System.out.println(helper.sum(nums));
    }
}
```


> 注意：JDK 8 以后，局部内部类访问的局部变量必须是 **effectively final**（不再要求显式 final，但不能被修改）。

---

## 4. 匿名内部类 —— "用完就走，懒得起名"

**场景**：只需要**一次性使用**接口/抽象类的实现，代码越短越好。这是四种里**实际开发最常用**的。

典型例子：
- **事件监听**（Swing/JavaFX/Android 开发）：

```java
button.addActionListener(new ActionListener() {   // 匿名内部类
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("按钮被点击了");
    }
});
```


- **多线程**：

```java
new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("线程运行中");
    }
}).start();
```


- **排序比较器**：

```java
Collections.sort(list, new Comparator<Person>() {
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getAge() - p2.getAge();
    }
});
```


（现在这些场景用 lambda 表达式更简洁，但原理相同）

---

## 总结对比表

| 类型 | 依赖外部类对象？ | 使用频率 | 典型场景 |
|------|:---:|:---:|------|
| 成员内部类 | ✅ 依赖 | 中 | 迭代器、状态机、父子强绑定结构 |
| 静态内部类 | ❌ 不依赖 | 中 | 数据结构节点、Builder、`Map.Entry` |
| 局部内部类 | 依赖外部对象 | 低 | 方法内的临时逻辑类 |
| 匿名内部类 | 看情况 | **高** | 事件监听、回调、一次性实现接口 |

一句话总结：**需要外部类引用的用成员内部类，不需要的用静态内部类，只用一次且简短用匿名内部类，方法内独立逻辑代码多的才用局部内部类。**
*/
public class Demo2 {
}
