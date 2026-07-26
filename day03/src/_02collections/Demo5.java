package _02collections;

import java.util.ArrayList;
import java.util.Collections;

/*
# ArrayList常用使用方式

`ArrayList`是 Java集合框架中最常用的类之一，底层基于**动态数组**实现，支持自动扩容。以下是它的常用操作：

---

##1.创建 ArrayList
```java
// 无参构造，默认容量10
ArrayList<String> list1 = new ArrayList<>();

// 指定初始容量（可减少扩容次数，提高性能）
ArrayList<String> list2 = new ArrayList<>(20);

// 通过其他集合创建
ArrayList<String> list3 = new ArrayList<>(Arrays.asList("A", "B", "C"));

// Java9+ 不可变列表（后续不能增删改）
List<String> list4 = List.of("X", "Y", "Z");
```


---

##2.添加元素
```java
ArrayList <String> list = new ArrayList<>();

// 尾部追加list.add("苹果");
list.add("香蕉");

// 指定位置插入（后续元素后移）
list.add(1, "橙子"); // [苹果,橙子,香蕉]

// 批量添加
list.addAll(Arrays.asList("葡萄", "西瓜"));
```


---

##3.删除元素
```java
//按索引删除（返回被删元素）
String removed = list.remove(2);

// 按对象删除（删除首次出现的匹配元素，返回 boolean）
boolean ok = list.remove("苹果");

// 条件删除（Java8+）
list.removeIf(s -> s.startsWith("西"));

// 清空全部list.clear();
```


---

##4.修改和查找
```java
// 修改指定位置的元素
list.set(0, "草莓");

// 获取指定位置的元素
String fruit = list.get(1);

// 获取大小
int size = list.size();

// 判断是否为空
boolean empty = list.isEmpty();

// 判断是否包含某元素
boolean hasApple = list.contains("苹果");

// 查找元素第一次出现的索引（不存在返回 -1）
int index = list.indexOf("香蕉");

// 查找元素最后一次出现的索引
int lastIndex = list.lastIndexOf("香蕉");
```


---

##5.遍历方式
```java
ArrayList <String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

// 方式1：for循环
for (int i =0; i < list.size(); i++) {
 System.out.println(list.get(i));
}

// 方式2：增强 for循环（推荐，简洁）
for (String s : list) {
 System.out.println(s);
}

// 方式3：迭代器
Iterator<String> it = list.iterator();
while (it.hasNext()) {
 System.out.println(it.next());
}

// 方式4：forEach + Lambda（Java8+）
list.forEach(System.out::println);

// 方式5：Stream API（Java8+）
list.stream().filter(s -> s.startsWith("A")).forEach(System.out::println);
```


---

##6.排序
```java
// 自然排序（元素需实现 Comparable）
Collections.sort(list);

// 定制排序
list.sort((a, b) -> b.compareTo(a)); //降序

// 方式2：Comparator
list.sort(Comparator.reverseOrder());
```


---

##7.与数组互转
```java
// ArrayList -> 数组
String[] arr = list.toArray(new String[0]); //推荐写法

// 数组 -> ArrayList（注意：返回的是固定大小的 List）
List<String> fixedList = Arrays.asList(arr);

// 真正可变的 ArrayList
ArrayList<String> mutableList = new ArrayList<>(Arrays.asList(arr));
```


---

## 8.常用特性注意点
|特性 |说明 |
|------|------|
| **线程不安全** |多线程下需使用 `Collections.synchronizedList()`或 `CopyOnWriteArrayList` |
| **扩容机制** |每次扩容约1.5倍，频繁插入大量数据时建议指定初始容量 |
| **允许 null** |可存储 null值 |
| **有序** |保持元素的插入顺序 |
| **非同步** |性能优于 `Vector` |

---

##完整示例
```java
import java.util.*;

public class ArrayListDemo {
	public static void main(String[] args) {
		// 创建
		ArrayList<String> list = new ArrayList<>();
		// 添加
		list.add("Java");
		list.add("Python");
		list.add("Go");
		// 遍历输出
		System.out.println("编程语言：");
		list.forEach(System.out::println);
		// 修改
		list.set(1, "JavaScript");
		// 删除
		list.remove("Go");
		// 排序
		Collections.sort(list);
		// 输出最终结果
		System.out.println("最终列表：" + list);
	}
}
```


输出：
```
编程语言：
JavaPythonGo最终列表：[Java, JavaScript]
```
*/
public class Demo5 {
    public static void main(String[] args) {
        //创建
        ArrayList<String> list = new ArrayList<>();

        //添加
        list.add("Java");
        list.add("Python");
        list.add("Go");

        // 指定位置插入（后续元素后移）
        list.add(3, "PHP");

        // 判断是否为空
        boolean empty = list.isEmpty();
        System.out.println(STR."列表是否为空：\{empty}");

        // 获取指定位置的元素
        System.out.println(STR."获取索引为2的元素：\{list.get(2)}");

        //遍历输出
        System.out.println("编程语言：");
        list.forEach(System.out::println);

        //修改
        list.set(1, "JavaScript");

        //删除
        list.remove("Go");

        //排序
        Collections.sort(list);

        //输出最终结果
        System.out.println(STR."最终列表：\{list}");
    }
}
