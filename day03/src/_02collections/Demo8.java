package _02collections;

import java.util.HashMap;
import java.util.Map;

/*
# Java Map集合简介

`Map`是一种**键值对（Key-Value）**映射集合，每个键唯一，一个键对应一个值。与 Collection接口平级，是 Java集合框架的另一个核心分支。

---

##一、三种常用 Map对比
|实现类 |特点 |底层结构 |键的顺序 |适用场景 |
|--------|------|----------|----------|----------|
| **HashMap** |最常用、性能最好 |哈希表（数组+链表+红黑树） | **无序** |大部分场景首选 |
| **LinkedHashMap** |保留插入/访问顺序 |哈希表 +双向链表 | **按插入顺序** |需要有序的 Map |
| **TreeMap** |自动排序 |红黑树 | **自然排序 /定制排序** |需要按键排序 |

---

##二、基本操作
```java
import java.util.*;

public class MapDemo {
 public static void main(String[] args) {

 // =====1.创建 =====
 Map<String, Integer> map = new HashMap<>();

 // =====2.添加 /修改 =====
 map.put("Java",90);
 map.put("Python",85);
 map.put("Go",88);
 map.put("Java",95); // 键相同 →覆盖旧值，返回旧值 90

 // =====3.获取 =====
 int score = map.get("Java"); //95
 Integer notExist = map.get("C++"); // null（不存在返回 null）

 //带默认值的获取（Java8+）
 int score2 = map.getOrDefault("C++",0); //不存在返回默认值0

 // =====4.删除 =====
 map.remove("Go"); //成功返回被删的值88
 map.remove("xxx"); //不存在返回 null

 // =====5.判断 =====
 boolean hasKey = map.containsKey("Java");
 boolean hasValue = map.containsValue(95);
 boolean empty = map.isEmpty();
 int size = map.size();

 // =====6.清空 =====
 map.clear();
 }
}
```


---

##三、遍历方式
```java
Map <String, Integer> map = new HashMap<>();
map.put("Java",95);
map.put("Python",85);
map.put("Go",88);

// =====方式1：遍历 entrySet（最常用，推荐） =====
for (Map.Entry<String, Integer> entry : map.entrySet()) {
 System.out.println(entry.getKey() + " → " + entry.getValue());
}

// =====方式2：遍历 keySet + get（性能稍差） =====
for (String key : map.keySet()) {
 System.out.println(key + " → " + map.get(key));
}

// =====方式3：遍历 values（只取值） =====
for (Integer value : map.values()) {
 System.out.println(value);
}

// =====方式4：forEach + Lambda（Java8+，简洁） =====
map.forEach((key, value) -> System.out.println(key + " → " + value));
```


---

## 四、Java8+新增实用方法
```java
Map<String, Integer> map = new HashMap<>();
map.put("Java",95);

// putIfAbsent — 仅当键不存在时放入
map.putIfAbsent("Java",100); //键已存在，不覆盖
map.putIfAbsent("Python",90); //键不存在，放入

// computeIfAbsent — 键不存在时，通过函数计算并放入
map.computeIfAbsent("Go", key -> key.length()); // Go →2

// computeIfPresent — 键存在时，用函数计算新值
map.computeIfPresent("Java", (key, val) -> val +5); // Java →100

// merge — 合并值（如分组计数）
Map<String, Integer> countMap = new HashMap<>();
String[] words = {"A", "B", "A", "C", "B", "A"};
for (String w : words) {
 countMap.merge(w,1, Integer::sum);
}
System.out.println(countMap); // {A=3, B=2, C=1}
```


---

## 五、LinkedHashMap示例 ——保持顺序
```java
//按插入顺序
LinkedHashMap<String, Integer> linkedMap = new LinkedHashMap<>();
linkedMap.put("A",1);
linkedMap.put("B",2);
linkedMap.put("C",3);
System.out.println(linkedMap); // {A=1, B=2, C=3}

//按访问顺序（可实现 LRU缓存）
LinkedHashMap<String, Integer> lru = new LinkedHashMap<>(16,0.75f, true);
lru.put("A",1);
lru.put("B",2);
lru.put("C",3);
lru.get("A"); //访问 A → A被移到末尾
System.out.println(lru); // {B=2, C=3, A=1}
```


---

##六、TreeMap示例 ——排序
```java
//自然排序（键需实现 Comparable）
TreeMap<String, Integer> treeMap = new TreeMap<>();
treeMap.put("Java",95);
treeMap.put("Python",85);
treeMap.put("Go",88);
System.out.println(treeMap); // {Go=88, Java=95, Python}按字母排序

//定制排序（降序）
TreeMap<Integer, String> descMap = new TreeMap<>((a, b) -> b - a);
descMap.put(3, "C");
descMap.put(1, "A");
descMap.put(2, "B");
System.out.println(descMap); // {3=C,2=B,1=A} 降序

//特有方法
System.out.println(treeMap.firstKey()); // Go最小键
System.out.println(treeMap.lastKey()); // Python最大键
System.out.println(treeMap.lowerKey("J")); // Go小于J的最大键
System.out.println(treeMap.higherKey("J"));// Java大于J的最小键
```


---

##七、常见应用场景
###7.1计数统计
```java
String text = "hello world hello java hello world";
Map<String, Integer> freq = new HashMap<>();
for (String word : text.split(" ")) {
 freq.put(word, freq.getOrDefault(word,0) +1);
}
System.out.println(freq); // {hello=3, world=2, java=1}
```


###7.2分组
```java
List <String> names = Arrays.asList("张三", "李四", "王五", "张伟", "李华");
Map<Character, List<String>> grouped = new HashMap<>();
for (String name : names) {
 grouped.computeIfAbsent(name.charAt(0), k -> new ArrayList<>()).add(name);
}
System.out.println(grouped); // {张=[张三,张伟],李=[李四,李华],王=[王五]}
```


###7.3缓存 /映射关系
```java
Map<Integer, String> statusMap = Map.of(
200, "成功",
404, "未找到",
500, "服务器错误"
);
System.out.println(statusMap.get(404)); //未找到```


---

## 八、注意事项
|注意点 |说明 |
|--------|------|
| **键唯一** |重复 put会覆盖旧值 |
| **HashMap无序** |遍历顺序不可预测 |
| **允许 null** | HashMap允许一个 null键和多个 null值；TreeMap不允许 null键 |
| **自定义对象做键** |必须重写 `hashCode()`和 `equals()` |
| **线程不安全** |多线程用 `ConcurrentHashMap`或 `Collections.synchronizedMap()` |
| **初始容量** |预估数据量时指定容量可减少扩容开销 |

---

##选择建议
|需求 |推荐 |
|------|------|
|通用快速存取 | `HashMap` |
|需要按键排序 | `TreeMap` |
|需要保持插入顺序 | `LinkedHashMap` |
|高并发环境 | `ConcurrentHashMap` |
|不可变的静态映射 | `Map.of()`（Java9+） |
*/
public class Demo8 {
    public static void main(String[] args) {
        // ===== 1.创建 =====
        Map<String, Integer> map = new HashMap<>();

        // ===== 2.添加 /修改 =====
        map.put("Java", 90);
        map.put("Python", 85);
        map.put("Go", 88);
        map.put("Java", 95); // 键相同 →覆盖旧值，返回旧值 90

        // ===== 3.获取 =====
        int score = map.get("Java"); // 95
        Integer notExist = map.get("C++"); // null（不存在返回 null）

        //带默认值的获取（Java8+）
        int score2 = map.getOrDefault("C++", 0); //不存在返回默认值0

        // ===== 4.删除 =====
        map.remove("Go"); //成功返回被删的值 88
        map.remove("xxx"); //不存在返回 null

        // ===== 5.判断 =====
        boolean hasKey = map.containsKey("Java");
        boolean hasValue = map.containsValue(95);
        boolean empty = map.isEmpty();
        int size = map.size();

        // ===== 6.清空 =====
        //map.clear();


        System.out.println("=====方式1：遍历 entrySet（最常用，推荐） =====");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(STR."\{entry.getKey()} → \{entry.getValue()}");
        }

        System.out.println("=====方式2：遍历 keySet + get（性能稍差） =====");
        for (String key : map.keySet()) {
            System.out.println(STR."\{key} → \{map.get(key)}");
        }

        System.out.println("=====方式3：遍历 values（只取值） =====");
        for (Integer value : map.values()) {
            System.out.println(value);
        }

        System.out.println("=====方式4：forEach + Lambda（Java8+，简洁） =====");
        map.forEach((key, value) -> System.out.println(STR."\{key} → \{value}"));
    }
}
