package _02collections;

/*
普通 `for` 循环的三个部分**都可以省略**，但两个分号 `;` 必须保留。

格式：

```java
for (初始化; 条件判断; 迭代操作) {
    // 循环体
}
```


### 示例

**三部分都写：**
```java
for (int i = 0; i < 10; i++) {
    System.out.println(i);
}
```


**省略初始化：**
```java
int i = 0;
for (; i < 10; i++) {
    System.out.println(i);
}
```


**省略条件判断（默认 true，会死循环，需用 break）：**
```java
for (int i = 0; ; i++) {
    if (i >= 10) break;
    System.out.println(i);
}
```


**省略迭代操作：**
```java
for (int i = 0; i < 10; ) {
    System.out.println(i);
    i++;
}
```


**三部分全部省略（死循环）：**
```java
for (;;) {
    // 死循环，通常配合 break 或 return 退出
}
```


### 总结
- 三个部分**都能省略**；
- 两个分号 `;` **不能省略**；
- 省略条件判断时，默认是 `true`，需要自己控制退出。
*/
public class Demo3 {
}
