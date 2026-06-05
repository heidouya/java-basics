package _10array;

import java.util.Arrays;

/*
    数组是 Java 中存储固定长度、相同数据类型元素的容器—— 简单说，就是 “一个能装多个同类型数据的盒子，一旦创建，盒子大小就不能改”。

    声明数组变量的语法：dataType[] arrayRefVar（推荐）; 或 dataType arrayRefVar[];

    创建数组的方式：
    1. 动态初始化：
        dataType[] arrayRefVar = new dataType[arraySize];
        示例：int[] arr = new int[5];
    2. 静态初始化：
        2.1 dataType[] arrayRefVar = {value0, value1, ..., valuek};
        示例：int[] arr = {1, 2, 3};
        2.2 dataType[] arrayRefVar = new dataType[] {value0, value1, ..., valuek};
        示例：int[] arr = new int[] {1, 2, 3};

    Java中数组特性：
    1. 长度固定：创建时必须指定长度（如int[] arr = new int[5]），后续无法扩容 / 缩容；
    2. 类型统一：数组里只能存同一种类型的数据（比如 int 数组只能存整数，不能混存字符串）；
    3. 索引访问：元素通过「索引」获取，索引从 0 开始（第一个元素是arr[0]，最后一个是arr[长度-1]）。
    4. 引用类型：数组变量存储的是地址，存放在堆内存中
    5. 默认初始化：创建后元素自动赋默认值（如 int 为 0，boolean 为 false，引用类型为 null）

    常用操作：
    1. 获取数组长度：array.length
    2. 遍历数组：for (int i = 0; i < array.length; i++) { } 或 for (dataType element : array) { }
    3. 数组排序：Arrays.sort(array)
    4. 数组复制：Arrays.copyOf(array, newLength)
    5. 数组比较：Arrays.equals(array1, array2)
    6. 数组填充：Arrays.fill(array, value)
    7. 数组查找：Arrays.binarySearch(array, value)
    8. 数组转换为字符串：Arrays.toString(array)

    注意：Java中的数组不支持reverse方法。

    数组的索引为什么从零开始呢？
    1. 传承 C 语言的设计规范，成为行业通用标准；
    2. 内存寻址时，0 代表 “首地址偏移 0 单位”，计算最高效；
        数组在内存中是「连续的一块空间」，数组名本质是「首元素的内存地址」。
        索引 0：表示「距离首地址偏移 0 个单位」（直接取首元素）；
        索引 n：表示「距离首地址偏移 n 个元素大小的空间」。
        比如 int 数组（每个 int 占 4 字节），arr [3] = 首地址 + 3×4 字节 → 计算直接高效。
        若从 1 开始，每次寻址都要算「首地址 + (n-1)× 元素大小」，多一次减法运算，增加底层开销。
*/
public class Demo1 {
    public static void main(String[] args) {
        // 1. 定义并初始化 int 数组
        int[] deviceIds = {1, 2, 3};

        // 2. 访问元素
        System.out.println(deviceIds[0]); // 1

        // 3. 获取数组长度/元素个数
        System.out.println(deviceIds.length); // 3

        // 4. 遍历数组 按顺序依次访问数组中元素
        for (int id : deviceIds ) {
            System.out.println(STR."设备ID：\{id}");
        }

        // 使用普通 for 循环遍历数组
        //for (int i = 0; i < deviceIds.length; i++) {
        //    System.out.println("设备ID：" + deviceIds[i]);
        //}

        // 使用 while 循环遍历数组
        //int i = 0;
        //while (i < deviceIds.length) {
        //    System.out.println("设备ID：" + deviceIds[i]);
        //    i++;
        //}

        // 1. 声明固定长度的数组
        int[] arr = new int[3];

        // 2. 添加元素
        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 3;

        // 3. 修改数组元素的值
        //arr[0] = 0;

        Arrays.sort(arr); // 排序数组
        // 转字符串
        System.out.println(Arrays.toString(arr));
        // 复制
        int[] arr2 = Arrays.copyOf(arr, 5); // 复制数组
        System.out.println(Arrays.equals(arr, arr2)); // 比较数组
        Arrays.fill(arr, 0); // 填充数组
        System.out.println(Arrays.binarySearch(arr, 2)); // 查找元素
    }
}
