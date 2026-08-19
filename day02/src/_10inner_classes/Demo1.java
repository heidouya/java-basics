package _10inner_classes;
/*
# Java 内部类简介

内部类（Inner Class）是指**定义在另一个类内部的类**。它主要用于实现更好的封装、逻辑分组，以及方便地访问外部类成员。

## 四种内部类

### 1. 成员内部类
定义在外部类内部、方法之外，是最常见的内部类。

```java
class Outer {
    private int age = 18;

    class Inner {          // 成员内部类
        void show() {
            System.out.println(age);   // 可直接访问外部类的私有成员
        }
    }
}
// 使用：Outer.Inner inner = new Outer().new Inner();
```

- 可以无条件访问外部类的所有成员（包括私有）
- 外部类访问内部类成员，需要先创建内部类对象
- 编译后生成 `Outer$Inner.class`

### 2. 静态内部类
用 `static` 修饰的内部类，属于外部类本身，而非外部类对象。

```java
class Outer {
    static class Inner {   // 静态内部类
    }
}
// 使用：new Outer.Inner();
```


- 只能访问外部类的**静态成员**
- 不需要先创建外部类对象即可创建

### 3. 局部内部类
定义在方法内部的类，作用范围仅限于该方法。

```java
class Outer {
    void test() {
        int num = 10;
        class Inner {      // 局部内部类
            void show() {
                System.out.println(num);   // 访问的局部变量必须是 final/ effectively final
            }
        }
        new Inner().show();
    }
}
```


### 4. 匿名内部类
没有类名的局部内部类，通常用于简化代码，常见于接口或抽象类的快速实现。

```java
interface Animal {
    void run();
}

Animal dog = new Animal() {     // 匿名内部类
    @Override
    public void run() {
        System.out.println("狗在跑");
    }
};
```


## 作用与注意点

- **优势**：封装性更好、逻辑集中、能轻松访问外部类私有成员
- **劣势**：类文件增多（`Outer$Inner.class`）、增加代码理解难度
- **常见应用**：事件监听、回调、集合排序（`Comparator` 匿名内部类）等

总的来说，成员内部类和匿名内部类使用最频繁，静态内部类常用于"配套类"的设计（如 `Map.Entry`），局部内部类相对少见。
*/
public class Demo1 {
    public static void main(String[] args) {
        Outer outer = new Outer("Outer");

        // 成员内部类，需要先创建外部类对象
        Outer.Inner1 inner1 = outer.new Inner1();
        inner1.show();

        // 静态内部类，属于外部类本身，而非外部类对象
        Outer.Inner2 inner2 = new Outer.Inner2();
        inner2.show();

        // 局部内部类，定义在方法内部，作用范围仅限于该方法
        outer.test();
    }
}

class Outer {
    private final String name;
    private static final int age = 18;

    public Outer(String name) {
        this.name = name;
    }

    // 成员内部类
    class Inner1 {
        public void show() {
            System.out.println(STR."我是 \{name} 的成员内部类");
            System.out.println(Outer.this.name);
        }
    }

    // 静态内部类
    static class Inner2 {
        public void show() {
            System.out.println(Outer.age);
        }
    }

    // 局部内部类
    void test() {
        int num = 10;
        class Inner {      // 局部内部类
            void show() {
                System.out.println(num);   // 访问的局部变量必须是 final/ effectively final 变量的值不能修改
            }
        }
        new Inner().show();
    }
}
