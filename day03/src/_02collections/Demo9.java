package _02collections;

/*
# Java 常见数据结构及代码示例

---

## 1. 数组（Array）

最基本的线性数据结构，长度固定，支持随机访问。

```java
public class ArrayDemo {
    public static void main(String[] args) {
        // 1. 声明并初始化
        int[] arr1 = new int[5];        // 默认值 0
        int[] arr2 = {10, 20, 30, 40, 50};

        // 2. 访问元素（随机访问 O(1)）
        System.out.println("第一个元素: " + arr2[0]);

        // 3. 遍历
        for (int i = 0; i < arr2.length; i++) {
            System.out.print(arr2[i] + " ");
        }
        System.out.println();

        // 4. 增强 for 循环
        for (int num : arr2) {
            System.out.print(num + " ");
        }
        System.out.println();

        // 5. 动态数组 ArrayList（长度可自动扩容）
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println("ArrayList: " + list);
        System.out.println("get(1): " + list.get(1));
        list.remove(1);
        System.out.println("删除下标1后: " + list);
    }
}
```


---

## 2. 链表（Linked List）

元素之间通过指针链接，插入/删除快，随机访问慢。Java 的 `LinkedList` 是双向链表。

```java
public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        // 添加元素
        list.add("A");
        list.add("B");
        list.add("C");
        list.addFirst("Head");    // 头部插入
        list.addLast("Tail");     // 尾部插入
        System.out.println("链表内容: " + list);   // [Head, A, B, C, Tail]

        // 访问元素
        System.out.println("第一个: " + list.getFirst());
        System.out.println("最后一个: " + list.getLast());
        System.out.println("下标2: " + list.get(2));

        // 删除元素
        list.removeFirst();       // 删除头
        list.removeLast();        // 删除尾
        list.remove("B");         // 删除指定元素
        System.out.println("删除后: " + list);

        // 遍历
        for (String s : list) {
            System.out.print(s + " ");
        }
        System.out.println();

        // 作为队列使用
        list.offer("D");          // 入队（尾部）
        String head = list.poll(); // 出队（头部）
        System.out.println("出队: " + head + ", 剩余: " + list);
    }
}
```


---

## 3. 栈（Stack）

**后进先出（LIFO）**，Java 推荐使用 `ArrayDeque` 替代 `Stack`。

```java
public class StackDemo {
    public static void main(String[] args) {
        // 推荐使用 ArrayDeque 作为栈
        Deque<String> stack = new ArrayDeque<>();

        // 入栈（push）
        stack.push("第1页");
        stack.push("第2页");
        stack.push("第3页");
        System.out.println("栈内容: " + stack);   // [第3页, 第2页, 第1页]

        // 查看栈顶（peek）
        System.out.println("栈顶: " + stack.peek());

        // 出栈（pop）
        String page = stack.pop();
        System.out.println("出栈: " + page);
        System.out.println("剩余: " + stack);

        // 浏览器的前进后退模拟
        Deque<String> backStack = new ArrayDeque<>();
        Deque<String> forwardStack = new ArrayDeque<>();

        // 访问页面
        visitPage(backStack, forwardStack, "首页");
        visitPage(backStack, forwardStack, "新闻");
        visitPage(backStack, forwardStack, "详情页");

        // 后退
        goBack(backStack, forwardStack);
        goBack(backStack, forwardStack);
        // 前进
        goForward(backStack, forwardStack);
    }

    static void visitPage(Deque<String> back, Deque<String> forward, String page) {
        back.push(page);
        forward.clear();    // 访问新页面时清空前进栈
        System.out.println("访问: " + page);
    }

    static void goBack(Deque<String> back, Deque<String> forward) {
        if (!back.isEmpty()) {
            forward.push(back.pop());
            System.out.println("后退到: " + back.peek());
        }
    }

    static void goForward(Deque<String> back, Deque<String> forward) {
        if (!forward.isEmpty()) {
            back.push(forward.pop());
            System.out.println("前进到: " + back.peek());
        }
    }
}
```


---

## 4. 队列（Queue）

**先进先出（FIFO）**，常用于任务调度、缓冲等场景。

```java
public class QueueDemo {
    public static void main(String[] args) {
        // ---------- 普通队列 ----------
        Queue<String> queue = new LinkedList<>();

        // 入队 offer（容量满返回 false）/ add（容量满抛异常）
        queue.offer("任务1");
        queue.offer("任务2");
        queue.offer("任务3");
        System.out.println("队列: " + queue);

        // 查看队头
        System.out.println("队头: " + queue.peek());

        // 出队 poll（空返回 null）/ remove（空抛异常）
        String task = queue.poll();
        System.out.println("处理: " + task);
        System.out.println("剩余: " + queue);

        // ---------- 双端队列 Deque ----------
        Deque<String> deque = new ArrayDeque<>();
        deque.addFirst("队头插入");
        deque.addLast("队尾插入");
        System.out.println("双端队列: " + deque);

        // ---------- 优先队列 PriorityQueue ----------
        // 默认小顶堆（自然顺序）
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(30);
        pq.offer(10);
        pq.offer(20);
        System.out.print("优先队列出队顺序: ");
        while (!pq.isEmpty()) {
            System.out.print(pq.poll() + " ");  // 10 20 30
        }
        System.out.println();

        // 大顶堆（自定义比较器）
        PriorityQueue<Integer> maxHeap =
            new PriorityQueue<>((a, b) -> b - a);
        maxHeap.offer(30);
        maxHeap.offer(10);
        maxHeap.offer(20);
        System.out.print("大顶堆出队顺序: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");  // 30 20 10
        }
        System.out.println();
    }
}
```


---

## 5. 二叉树（Binary Tree）

### 5.1 什么是二叉树

二叉树是一种**层次化**的数据结构，每个节点**最多有两个子节点**，分别称为**左子节点**和**右子节点**。

```
        1          ← 根节点
       / \
      2   3        ← 内部节点
     / \   \
    4   5   6      ← 叶子节点
```


### 5.2 基本术语

| 术语 | 含义 |
|------|------|
| 根节点 | 树顶层的节点，没有父节点 |
| 叶子节点 | 没有子节点的节点 |
| 父节点 / 子节点 | 上下相邻的节点关系 |
| 兄弟节点 | 同一个父节点的子节点 |
| 深度/高度 | 从根到某节点的层数 |
| 子树 | 某个节点及其所有后代 |

### 5.3 常见类型

- **满二叉树**：除叶子节点外，每个节点都有左右两个子节点
- **完全二叉树**：除最后一层外逐层填满，最后一层从左到右连续
- **二叉搜索树（BST）**：左子树所有节点 < 根 < 右子树所有节点，查找效率高
- **平衡二叉树**：左右子树高度差不超过 1，如 AVL 树、红黑树
- **堆（Heap）**：特殊的完全二叉树，用于实现优先队列

平衡二叉树只约束"形状"，二叉搜索树只约束"值的顺序"，二者互不包含。 只有像 AVL 树、红黑树这样的才是"既要平衡、又要有序"的组合体。

### 5.4 遍历方式（核心）

- **前序遍历**：根 → 左 → 右（`1 2 4 5 3 6`）
- **中序遍历**：左 → 根 → 右（`4 2 5 1 3 6`）——BST 中序遍历结果是升序
- **后序遍历**：左 → 右 → 根（`4 5 2 6 3 1`）
- **层序遍历**：逐层从左到右（`1 2 3 4 5 6`）

### 5.5 手动构建与遍历

```java
// 二叉树节点类
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class BinaryTreeDemo {
    public static void main(String[] args) {
        // 构建一棵二叉树
        //        1
        //       / \
        //      2   3
        //     / \   \
        //    4   5   6
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        System.out.print("前序遍历: ");
        preorder(root);
        System.out.println();

        System.out.print("中序遍历: ");
        inorder(root);
        System.out.println();

        System.out.print("后序遍历: ");
        postorder(root);
        System.out.println();

        System.out.print("层序遍历: ");
        levelOrder(root);
        System.out.println();
    }

    // 前序：根 → 左 → 右
    static void preorder(TreeNode node) {
        if (node == null) return;
        System.out.print(node.val + " ");
        preorder(node.left);
        preorder(node.right);
    }

    // 中序：左 → 根 → 右
    static void inorder(TreeNode node) {
        if (node == null) return;
        inorder(node.left);
        System.out.print(node.val + " ");
        inorder(node.right);
    }

    // 后序：左 → 右 → 根
    static void postorder(TreeNode node) {
        if (node == null) return;
        postorder(node.left);
        postorder(node.right);
        System.out.print(node.val + " ");
    }

    // 层序（BFS）：借助队列
    static void levelOrder(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }
}
```

---

### 5.6 TreeMap / TreeSet（红黑树实现）

```java
public class TreeMapDemo {
    public static void main(String[] args) {
        // TreeMap 底层是红黑树，key 自动排序
        TreeMap<String, Integer> scores = new TreeMap<>();
        scores.put("张三", 85);
        scores.put("李四", 92);
        scores.put("王五", 78);
        scores.put("赵六", 95);

        System.out.println("按姓名排序:");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("第一个key: " + scores.firstKey());
        System.out.println("最后一个key: " + scores.lastKey());
        // 范围查询（>=李四 且 <赵六）
        System.out.println("子映射: " + scores.subMap("李四", "赵六"));

        // TreeSet：去重 + 排序
        TreeSet<Integer> set = new TreeSet<>();
        set.add(5);
        set.add(3);
        set.add(8);
        set.add(1);
        System.out.println("TreeSet: " + set);   // [1, 3, 5, 8]
        System.out.println("小于等于4的最大元素: " + set.floor(4));  // 3
        System.out.println("大于5的最小元素: " + set.higher(5));      // 8
    }
}
```

### 5.7 复杂度

- 查找/插入/删除：平衡树为 **O(log n)**，最坏情况（退化成链表）为 **O(n)**
- 空间：节点数 n 个，边数为 n-1

### 5.8 应用场景

- 数据库索引（B+ 树）
- 文件系统目录结构
- 表达式解析（语法树）
- 排序（二叉搜索树、堆排序）
- 哈夫曼编码（数据压缩）

**一句话总结**：二叉树是每个节点最多有两个子节点的树形结构，通过递归思想访问，是学习树结构和后续高级数据结构（AVL、红黑树、B+ 树）的基础。

如果后续需要，我可以帮你写二叉树相关的练习题（如翻转二叉树、判断平衡、最近公共祖先等）来加深理解。

---

## 6. 哈希表（Hash Table / HashMap）

通过哈希函数将 key 映射到桶中，查找、插入、删除平均 O(1)。Java 8+ 当链表长度 > 8 时转为红黑树优化。

```java
public class HashMapDemo {
    public static void main(String[] args) {
        // ---------- HashMap ----------
        HashMap<String, Integer> map = new HashMap<>();

        // 添加元素
        map.put("苹果", 5);
        map.put("香蕉", 3);
        map.put("橘子", 8);
        map.put("苹果", 10);  // 覆盖旧值
        System.out.println("HashMap: " + map);

        // 获取元素
        System.out.println("苹果数量: " + map.get("苹果"));
        System.out.println("不存在返回null: " + map.get("西瓜"));
        System.out.println("不存在返回默认值: " +
            map.getOrDefault("西瓜", 0));

        // 遍历方式一：entrySet
        System.out.println("--- 遍历 entrySet ---");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        // 遍历方式二：keySet
        System.out.println("--- 遍历 keySet ---");
        for (String key : map.keySet()) {
            System.out.println(key + " = " + map.get(key));
        }

        // 遍历方式三：forEach（Lambda）
        System.out.println("--- Lambda 遍历 ---");
        map.forEach((k, v) -> System.out.println(k + " = " + v));

        // 检查存在
        System.out.println("包含key'香蕉'? " + map.containsKey("香蕉"));
        System.out.println("包含value 8? " + map.containsValue(8));

        // 删除
        map.remove("橘子");
        System.out.println("删除橘子后: " + map);

        // ---------- LinkedHashMap（保持插入顺序）----------
        LinkedHashMap<String, Integer> linkedMap = new LinkedHashMap<>();
        linkedMap.put("A", 1);
        linkedMap.put("B", 2);
        linkedMap.put("C", 3);
        System.out.println("LinkedHashMap(插入顺序): " + linkedMap);

        // ---------- ConcurrentHashMap（线程安全）----------
        ConcurrentHashMap<String, Integer> concurrentMap =
            new ConcurrentHashMap<>();
        concurrentMap.put("线程安全", 1);
        concurrentMap.put("并发", 2);
        System.out.println("ConcurrentHashMap: " + concurrentMap);

        // ---------- 统计单词出现次数（经典用法）----------
        String text = "apple banana apple orange banana apple";
        String[] words = text.split(" ");
        HashMap<String, Integer> countMap = new HashMap<>();
        for (String word : words) {
            // 方法一：
            countMap.put(word, countMap.getOrDefault(word, 0) + 1);
        }
        // 或者方法二（merge）：
        // countMap.merge(word, 1, Integer::sum);
        System.out.println("单词计数: " + countMap);
    }
}
```


---

## 7. 综合对比一览

```java
public class CompareAll {
    public static void main(String[] args) {
        // 不同数据结构的常用场景对比
        List<Integer> arrayList = new ArrayList<>();  // 随机访问多
        List<Integer> linkedList = new LinkedList<>();// 插入删除多
        Deque<Integer> stack = new ArrayDeque<>();     // LIFO
        Queue<Integer> queue = new LinkedList<>();     // FIFO
        Queue<Integer> priorityQueue = new PriorityQueue<>(); // 按优先级出
        Map<String, Integer> hashMap = new HashMap<>();// 快速查找
        Map<String, Integer> treeMap = new TreeMap<>();// 有序的key
        Set<Integer> hashSet = new HashSet<>();        // 去重
        Set<Integer> treeSet = new TreeSet<>();        // 去重+排序

        // 性能简单测试
        int n = 100000;

        // ArrayList vs LinkedList 尾部插入
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) arrayList.add(i);
        long end = System.nanoTime();
        System.out.println("ArrayList 尾部插入: " + (end - start) / 1e6 + "ms");

        start = System.nanoTime();
        for (int i = 0; i < n; i++) linkedList.add(i);
        end = System.nanoTime();
        System.out.println("LinkedList 尾部插入: " + (end - start) / 1e6 + "ms");

        // HashMap vs TreeMap 插入
        start = System.nanoTime();
        for (int i = 0; i < n; i++) hashMap.put(i, i);
        end = System.nanoTime();
        System.out.println("HashMap 插入: " + (end - start) / 1e6 + "ms");

        start = System.nanoTime();
        for (int i = 0; i < n; i++) treeMap.put(i, i);
        end = System.nanoTime();
        System.out.println("TreeMap 插入: " + (end - start) / 1e6 + "ms");
    }
}
```


---

## 总结：选型指南

| 需求场景 | 推荐数据结构 | 理由 |
|---------|------------|------|
| 快速随机访问 | `ArrayList` | 数组实现，下标 O(1) |
| 频繁插入/删除 | `LinkedList` | 链表操作 O(1) |
| 后进先出 | `ArrayDeque` 作为栈 | 比 `Stack` 效率更高 |
| 先进先出 | `LinkedList` 或 `ArrayDeque` | 队列操作 O(1) |
| 按优先级出队 | `PriorityQueue` | 堆实现 |
| 快速查找 key→value | `HashMap` | 哈希表 O(1) |
| key 需要排序 | `TreeMap` | 红黑树，自动排序 |
| 去重 | `HashSet` | 基于 HashMap |
| 去重+排序 | `TreeSet` | 基于 TreeMap |
| 线程安全 | `ConcurrentHashMap` / `CopyOnWriteArrayList` | 并发控制 |
*/
public class Demo9 {}
