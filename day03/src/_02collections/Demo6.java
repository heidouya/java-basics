package _02collections;

/*
# Java Set集合详解

`Set`是 Java集合框架中的一种接口，**不允许存储重复元素**，每个元素唯一。主要实现类有三种：

---

## 一、三种常用 Set对比
|实现类 |特点 |底层结构 |元素顺序 |适用场景 |
|--------|------|----------|----------|----------|
| **HashSet** |最快、最常用 |哈希表（HashMap） | **无序** |单纯去重、快速查找 |
| **LinkedHashSet** |保留插入顺序 |哈希表 +双向链表 | **按插入顺序** |需要去重且保持顺序 |
| **TreeSet** |自动排序 |红黑树 | **自然排序 /定制排序** |需要有序且去重 |

---

## 二、常用操作
```java
import java.util.*;

public class SetDemo {
 public static void main(String[] args) {

 // =====1.创建 =====
 Set<String> hashSet = new HashSet<>();
 Set<String> linkedSet = new LinkedHashSet<>();
 Set<String> treeSet = new TreeSet<>();

 // =====2.添加元素 =====
 hashSet.add("Java");
 hashSet.add("Python");
 hashSet.add("Go");
 hashSet.add("Java"); //重复添加无效，不会报错
 System.out.println(hashSet); // [Go, Java, Python]（无序）

 // =====3.删除 =====
 hashSet.remove("Go");
 boolean removed = hashSet.remove("xxx"); //不存在返回 false

 // =====4.遍历 =====
 //增强 for
 for (String s : hashSet) {
 	System.out.println(s);
 }

 // Lambda遍历
 hashSet.forEach(System.out::println);

 // Stream API
 hashSet.stream().filter(s -> s.startsWith("J")).forEach(System.out::println);

 // =====5.常用方法 =====
 int size = hashSet.size();
 boolean empty = hashSet.isEmpty();
 boolean hasJava = hashSet.contains("Java");

 // 清空
 hashSet.clear();

 // =====6.批量操作（交集、并集、差集） =====
 Set<Integer> set1 = new HashSet<>(Arrays.asList(1,2,3,4,5));
 Set<Integer> set2 = new HashSet<>(Arrays.asList(4,5,6,7,8));

 // 并集
 Set<Integer> union = new HashSet<>(set1);
 union.addAll(set2);
 System.out.println("并集：" + union); // [1,2,3,4,5,6,7,8]

 // 交集
 Set<Integer> intersect = new HashSet<>(set1);
 intersect.retainAll(set2);
 System.out.println("交集：" + intersect); // [4,5]

 //差集（set1有而 set2没有的）
 Set<Integer> diff = new HashSet<>(set1);
 diff.removeAll(set2);
 System.out.println("差集：" + diff); // [1,2,3]
 }
}
```


---

## 三、HashSet示例 ——去重
```java
// 去除列表中的重复元素
List<Integer> list = Arrays.asList(1,2,2,3,3,3,4,5,5);
Set<Integer> set = new HashSet<>(list);
System.out.println(set); // [1,2,3,4,5]去重完成```


>去重的原理：依赖元素的 `hashCode()`和 `equals()`方法。**自定义对象放入 HashSet时，必须重写这两个方法。**

---

## 四、TreeSet示例 ——排序
```java
//自然排序（元素需实现 Comparable）
TreeSet<Integer> numbers = new TreeSet<>(Arrays.asList(5,3,8,1,9));
System.out.println(numbers); // [1,3,5,8,9]自动升序

//定制排序（倒序）
TreeSet<String> words = new TreeSet<>((a, b) -> b.compareTo(a));
words.addAll(Arrays.asList("Java", "Python", "Go"));
System.out.println(words); // [Python, Java, Go]降序

// TreeSet特有方法
System.out.println(numbers.first()); // 1 最小
System.out.println(numbers.last()); // 9 最大
System.out.println(numbers.lower(5)); // 3 小于 5 的最大值
System.out.println(numbers.higher(5)); // 8 大于 5 的最小值
System.out.println(numbers.subSet(2,7)); // [3,5] 范围 [2,7)
```


---

## 五、LinkedHashSet示例 —— 保持插入顺序
```java
LinkedHashSet <String> linkedSet = new LinkedHashSet<>();
linkedSet.add("苹果");
linkedSet.add("香蕉");
linkedSet.add("橙子");
linkedSet.add("苹果"); //重复，不加入
System.out.println(linkedSet); // [苹果,香蕉,橙子]保持插入顺序
```
---

##六、set 与 List互转
```java
// List -> Set（去重）
List<String> list = Arrays.asList("A", "B", "A", "C", "B");
Set<String> set = new HashSet<>(list);

// Set -> List
List<String> newList = new ArrayList<>(set);
```


---

##七、使用建议
|场景 |推荐使用 |
|------|----------|
|单纯去重，不关心顺序 | `HashSet`（性能最好） |
|去重 +保持插入顺序 | `LinkedHashSet` |
|去重 +自动排序 | `TreeSet` |
|需要线程安全 | `Collections.synchronizedSet()`或 `ConcurrentHashMap.newKeySet()` |
|存储自定义对象 |务必重写 `hashCode()` + `equals()` |

*/
public class Demo6 {
}
