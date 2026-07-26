package _02collections;
/*
# Java HashSet基本用法

`HashSet`基于哈希表实现，是 Set接口最常用的实现类。特点是：**元素唯一、无序、允许 null、非线程安全**。

---

## 1.创建 HashSet
```java
// 无参构造
HashSet<String> set1 = new HashSet<>();

// 指定初始容量（减少扩容，提升性能）
HashSet<String> set2 = new HashSet<>(32);

// 指定加载因子（默认0.75，一般不需要改）
HashSet<String> set3 = new HashSet<>(16,0.8f);

//从其他集合创建
HashSet<String> set4 = new HashSet<>(Arrays.asList("A", "B", "C"));
```


---

## 2.增删改查
```java
HashSet <String> set = new HashSet<>();

// 添加
set.add("Java");
set.add("Python");
set.add("Go");
set.add("Java"); //重复添加无效，返回 false
// 删除
set.remove("Go"); // 成功返回 true
set.remove("xxx"); // 不存在返回 false
//清空
set.clear();
// 判断
boolean empty = set.isEmpty();
int size = set.size();
boolean hasJava = set.contains("Java");
```


---

## 3.遍历方式
```java
HashSet
<String> set = new HashSet<>(Arrays.asList("A", "B", "C"));

// 增强 for
for (String s : set) {
 System.out.println(s);
}

// forEach + Lambda
set.forEach(System.out::println);

// 迭代器
Iterator<String> it = set.iterator();
while (it.hasNext()) {
 System.out.println(it.next());
}

// Stream API
set.stream().filter(s -> s.startsWith("A")).forEach(System.out::println);
```


---

## 4.常用实战场景
### 4.1去重
```java
List<Integer> list = Arrays.asList(1,2,2,3,3,3,4);
HashSet<Integer> set = new HashSet<>(list);
System.out.println(set); // [1,2,3,4]
```


### 4.2快速查找
```java
HashSet <String> blacklist = new HashSet<>(Arrays.asList("user1", "user2"));
String input = "user1";
if (blacklist.contains(input)) {
 System.out.println("该用户已被拉黑");
}
```


> `contains()`时间复杂度为 O(1)，远快于 List的 O(n)

### 4.3集合运算
```java
HashSet <Integer> setA = new HashSet<>(Arrays.asList(1,2,3,4));
HashSet<Integer> setB = new HashSet<>(Arrays.asList(3,4,5,6));

//并集
HashSet<Integer> union = new HashSet<>(setA);
union.addAll(setB);
System.out.println(union); // [1,2,3,4,5,6]

//交集
HashSet<Integer> intersect = new HashSet<>(setA);
intersect.retainAll(setB);
System.out.println(intersect); // [3,4]

//差集
HashSet<Integer> diff = new HashSet<>(setA);
diff.removeAll(setB);
System.out.println(diff); // [1,2]
```


---

## 5. 自定义对象去重
自定义对象存入 HashSet**必须重写 `hashCode()`和 `equals()`方法**，否则无法正确去重。

```java
class Student {
 String name;
 int age;

 @Override public boolean equals(Object o) {
	 if (this == o) return true;
	 if (o == null || getClass() != o.getClass()) return false;
	 Student student = (Student) o;
	 return age == student.age && Objects.equals(name, student.name);
 }

 @Override public int hashCode() {
 	return Objects.hash(name, age);
 }
}

// 使用
HashSet<Student> students = new HashSet<>();
students.add(new Student("张三",18));
students.add(new Student("张三",18)); // 不会重复添加
```


> **如果不重写 hashCode/equals**，即使两个对象内容相同，也会被视为不同元素。

---

## 6.去重原理图
```
set.add("Java")
    ↓
① 调用 key.hashCode() 获取原始值，并执行扰动函数 (h ^ h>>>16)
    ↓
② 计算索引: (数组长度-1) & 扰动后的哈希值  → 定位到数组桶位
    ↓
③ 检查该桶位是否为 null？
    ├─ 是 → 直接创建节点存入，返回 true（添加成功）
    └─ 否 → 遍历桶内的链表/红黑树节点：
            │
            ├─ 比较节点哈希值 && （节点引用==新元素 || equals() 为 true）
            │       ├─ 成立 → 判定为重复元素，覆盖旧值，返回 false（添加失败）
            │       └─ 不成立 → 继续遍历下一个节点
            │
            └─ 全部遍历完均未匹配 → 尾插法挂载新节点（JDK8+），返回 true
```

---

## 7.注意事项
|注意点 |说明 |
|--------|------|
| **无序** |不保证元素的顺序，遍历顺序可能与插入顺序不同 |
| **允许 null** |最多只能存一个 null |
| **线程不安全** |多线程需用 `Collections.synchronizedSet()`或 `CopyOnWriteArraySet` |
| **初始容量** |如果能预估数据量，指定初始容量可避免频繁扩容 |
| **加载因子** |默认0.75，空间换时间的平衡值，一般不用改 |
| **哈希冲突** |虽然哈希表通过哈希函数将元素映射到数组索引，但不同的元素可能映射到相同的索引，这就是哈希冲突。HashSet通过链地址法或红黑树解决哈希冲突。|
*/
public class Demo7 {
}
