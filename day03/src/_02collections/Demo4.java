
package _02collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
/*
在 while 循环中删除元素“d”，代码运行正常，但是删除"b"等元素，会报错：ConcurrentModificationException，这是为什么呢？

这是因为`ArrayList`迭代器中的**`ConcurrentModificationException`**检查机制导致的。

## 原因分析

关键在于 `hasNext()` 和 `next()` 的检查时机不同：

- `hasNext()` 只判断 `cursor != size`，**不检查** `modCount`
- `next()` 会先检查 `modCount` 是否被修改，如果被修改则抛出 `ConcurrentModificationException`

### 删除"d"（倒数第二个元素）为什么成功？

```
集合内容: [a, b, c, d, e]
遍历到"d"时：cursor = 4，size = 5
执行 coll.remove("d") 后：集合变为 [a, b, c, e]，size = 4
此时 cursor = 4，size = 4 → hasNext() 返回 false → 循环结束，不再调用 next()
```

**没有机会触发 `ConcurrentModificationException`，所以"看似"正常。**

### 删除"b"等元素为什么会报错？

```
集合内容: [a, b, c, d, e]
遍历到"b"时：cursor = 2，size = 5
执行 coll.remove("b") 后：集合变为 [a, c, d, e]，size = 4
此时 cursor = 2，size = 4 → hasNext() 返回 true → 调用 next()
next() 发现 modCount 已改变 → 抛出 ConcurrentModificationException
```

删除"b"之后还有元素要遍历，`hasNext()` 返回 `true`，紧接着 `next()` 就检测到集合被修改了，直接抛出异常。

---

## 正确做法

不要在迭代时直接用 `coll.remove()`，而应该用迭代器的 `remove()` 方法：

```java
Iterator<String> iterator = coll.iterator();
while (iterator.hasNext()) {
    String s = iterator.next();
    if (s.equals("d")) {
        iterator.remove();  // 通过迭代器删除，会同步更新 expectedModCount
    }
}
```

或者使用 Java 8+ 的 `removeIf` 更简洁：

```java
coll.removeIf(s -> s.equals("d"));
```
*/
public class Demo4 {
    public static void main(String[] args) {
        Collection<String> coll = new ArrayList<>();

        coll.add("a");
        coll.add("b");
        coll.add("c");
        coll.add("d");
        coll.add("e");

        Iterator<String> iterator = coll.iterator();
        // 报错：ConcurrentModificationException
        // ConcurrentModificationException 是 Java 集合框架中的运行时异常，常见于迭代集合时结构被修改的场景。它基于 fail-fast（快速失败）机制：Iterator
        // 在创建时会记录集合的 modCount（修改次数），每次调用 next() 时会检查是否与 expectedModCount 一致，不一致则抛出异常。
        //while (iterator.hasNext()) {
        //    String s = iterator.next();
        //    if (s.equals("b")) {
        //        coll.remove(s);
        //    }
        //}

        iterator.hasNext();
        String s1 = iterator.next();

        iterator.hasNext();
        String s2 = iterator.next();

        iterator.hasNext();
        String s3 = iterator.next();

        iterator.hasNext();
        String s4 = iterator.next();
        //coll.remove(s4);
        // 用迭代器的 `remove()` 方法
        iterator.remove();

        iterator.hasNext();
        String s5 = iterator.next();

        System.out.println(coll);
    }
}
