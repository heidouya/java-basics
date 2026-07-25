package _02collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*
`Collection` 是 `List`、`Set`、`Queue` 的根接口，定义了单列集合最常用的通用方法。下面是核心方法：

### 1. 增
```java
boolean add(E e);          // 添加元素，成功返回 true
boolean addAll(Collection<? extends E> c);  // 添加另一个集合的所有元素
```

### 2. 删
```java
boolean remove(Object o);  // 删除指定元素
boolean removeAll(Collection<?> c);  // 删除当前集合中存在于 c 的元素
boolean retainAll(Collection<?> c);  // 只保留当前集合中也存在于 c 的元素（交集）
void clear();              // 清空集合
```

### 3. 查
```java
boolean contains(Object o);        // 是否包含某元素
boolean containsAll(Collection<?> c);  // 是否包含 c 中所有元素
int size();                        // 元素个数
boolean isEmpty();                 // 是否为空
```

### 4. 遍历
```java
Iterator<E> iterator();  // 获取迭代器
```

配合使用：
```java
for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
    String s = it.next();
}
```

Java 8+ 还可以用 `forEach()`：
```java
list.forEach(System.out::println);
```

### 5. 转数组
```java
Object[] toArray();
<T> T[] toArray(T[] a);  // 更常用，可指定类型
```

### 示例
```java
List<String> list = new ArrayList<>();
list.add("a");
list.add("b");
System.out.println(list.size());         // 2
System.out.println(list.contains("a"));  // true
list.remove("a");
list.clear();
```

### 小总结
| 类型 | 代表方法 |
|------|----------|
| 增 | `add`、`addAll` |
| 删 | `remove`、`removeAll`、`retainAll`、`clear` |
| 查 | `contains`、`containsAll`、`size`、`isEmpty` |
| 遍历 | `iterator`、`forEach` |
| 转换 | `toArray` |

注意：`Map` 不属于 `Collection`，所以这些方法对它不适用。`Map` 有独立的 `put`、`get`、`remove`、`keySet`、`entrySet` 等方法。
*/
public class Demo2 {
    public static void main(String[] args) {
        Collection<String> coll = new ArrayList<>();

        // 增加元素
        coll.add("a");
        coll.add("b");

        // 获取元素个数
        System.out.println(coll.size()); // 2

        // 判断是否包含某元素
        System.out.println(coll.contains("a"));

        // 删除元素
        coll.remove("a");

        System.out.println(coll);

        // 使用迭代器遍历集合
        // coll.iterator() 获取迭代器对象
        // it.hasNext() 判断是否还有下一个元素
        for (Iterator<String> it = coll.iterator(); it.hasNext();) {
            // it.next() 获取下一个元素并移动游标
            String s = it.next();
            System.out.println(s);
        }

        // 使用 while 循环遍历集合
        Iterator<String> it = coll.iterator();
        while (it.hasNext()) {
            String s = it.next();
            System.out.println(s);
        }

        // 使用 forEach 方法遍历集合
        coll.forEach(System.out::println);

        // 使用增强 for 循环遍历集合
        for (String s : coll) {
            System.out.println(s);
        }

        int[] arr = {1, 2, 3, 4, 5};
        for (int i = 0; i < arr.length;) {
            System.out.println(arr[i]);
            i++;
        }
    }
}
