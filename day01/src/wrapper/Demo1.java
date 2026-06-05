package wrapper;

/*
## Java 包装类（Wrapper Classes）

Java 中有 **8 种基本数据类型**，但它们是“值类型”，不是对象，无法参与面向对象的操作（如放入集合 `Collection`、调用方法等）。为此，Java 为每种基本类型都提供了对应的**包装类**，将基本类型“包装”成一个对象。

基本类型是"值"，包装类是把它装进一个对象"盒子"里，从而可以操作对象、传 null、用工具方法等。Java 5+ 还支持自动装箱拆箱，写起来和基本类型一样自然。

### 基本类型 ↔ 包装类对照表

| 基本类型 | 包装类 | 示例 |
|---------|--------|------|
| `byte` | `Byte` | `Byte b = 127;` |
| `short` | `Short` | `Short s = 1000;` |
| `int` | `Integer` | `Integer i = 42;` |
| `long` | `Long` | `Long l = 999L;` |
| `float` | `Float` | `Float f = 3.14f;` |
| `double` | `Double` | `Double d = 2.718;` |
| `char` | `Character` | `Character c = 'A';` |
| `boolean` | `Boolean` | `Boolean b = true;` |

### 核心作用

1. **将基本类型转为对象** — 例如放入 `List<Integer>`、`Map<String, Double>`
2. **提供常量与工具方法** — 如 `Integer.MAX_VALUE`、`Integer.parseInt("123")`
3. **支持 null 值** — 基本类型不能为 null，包装类可以
4. **与字符串互转** — `Integer.valueOf("100")`、`String.valueOf(100)`

### 自动装箱与拆箱（Java 5+）

```java
// 自动装箱：int → Integer
Integer i = 100;          // 等价于 Integer.valueOf(100)

// 自动拆箱：Integer → int
int n = i;                // 等价于 i.intValue()
```

编译器会自动插入 `valueOf()` 和 `xxxValue()` 方法调用，写起来和基本类型一样自然。

### 注意事项

- **`==` 比较陷阱**：包装类用 `==` 比较的是对象引用，值相等应使用 `.equals()`
- **缓存机制**：`Integer` 默认缓存 `-128 ~ 127`，在此范围内的自动装箱复用同一对象
- **性能开销**：频繁装箱拆箱会产生额外对象，大量循环中应优先使用基本类型

总的来说，包装类是基本类型与面向对象世界之间的**桥梁**，日常开发中非常常用。
*/
public class Demo1 {
    public static void main(String[] args) {
        // 不要使用 new Integer() 创建包装类对象
        // 使用 Integer.valueOf() 创建包装类对象
        //Integer a = new Integer(1);
        Integer a = Integer.valueOf(1);

        // 自动装箱：int → Integer
        Integer b = 1;

        // 自动拆箱：Integer → int
        int c = b;

        // 包装类对象可以为 null，表示“无值”，不能调用方法，拆箱会报 NullPointerException
        //Integer d = null;
        //int e = d; // NullPointerException

        // Integer 默认缓存 `-128 ~ 127`，在此范围内的自动装箱复用同一对象
        Integer f = 1;
        Integer g = 1;
        System.out.println(f == g); // true

        Integer h = 128;
        Integer i = 128;
        System.out.println(h == i); // false

        System.out.println(h.equals(i)); // true
    }
}
