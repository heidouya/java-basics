package _02collections;

/*
Java 集合主要用于**批量存储和操作对象**，位于 `java.util` 包下，核心架构分两大类：

### 1. Collection 接口（单列集合）
存储单个元素，常见子接口：

- **List**：有序、可重复
  - `ArrayList`：底层是数组，查询快、增删慢，最常用
  - `LinkedList`：底层是链表，增删快、支持队列操作
  - `Vector`：线程安全但较老，一般不推荐
- **Set**：无序、不可重复
  - `HashSet`：基于哈希表，去重效率高
  - `LinkedHashSet`：保留插入顺序
  - `TreeSet`：可排序，基于红黑树
- **Queue**：队列，先进先出
  - `LinkedList`、`PriorityQueue`、`ArrayDeque`

### 2. Map 接口（双列集合）
存储键值对 `key-value`，key 不能重复：

- `HashMap`：最常用，基于哈希表，key 可为 `null`
- `LinkedHashMap`：保留插入顺序
- `TreeMap`：按键排序
- `Hashtable`：线程安全但已不常用
- `ConcurrentHashMap`：高并发场景下的线程安全选择

### 3. 常用工具类
- `Collections`：提供排序 `sort()`、反转 `reverse()`、查找 `binarySearch()`、同步包装等方法
- `Arrays`：用于数组与集合之间的转换等

### 简单对比
| 特点 | List | Set | Map |
|------|------|-----|-----|
| 元素重复 | 允许 | 不允许 | key 不允许重复 |
| 有序性 | 有序 | 一般无序 | 一般无序 |
| 存储结构 | 单列 | 单列 | 键值对 |

初学者建议重点掌握 `ArrayList`、`HashSet`、`HashMap` 的使用和遍历方式，后续再深入了解泛型、迭代器和线程安全。
*/
public class Demo1 {
}
