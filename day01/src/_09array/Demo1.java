package _09array;

/*
    数组是 Java 中存储固定长度、相同数据类型元素的容器—— 简单说，就是 “一个能装多个同类型数据的盒子，一旦创建，盒子大小就不能改”。

    声明数组变量的语法：dataType[] arrayRefVar（推荐）; 或 dataType arrayRefVar[];

    创建数组的两种方式：
    1. dataType[] arrayRefVar = new dataType[arraySize];
    2. dataType[] arrayRefVar = {value0, value1, ..., valuek};

    Java中数组特性：
    1. 长度固定：创建时必须指定长度（如int[] arr = new int[5]），后续无法扩容 / 缩容；
    2. 类型统一：数组里只能存同一种类型的数据（比如 int 数组只能存整数，不能混存字符串）；
    3. 索引访问：元素通过「索引」获取，索引从 0 开始（第一个元素是arr[0]，最后一个是arr[长度-1]）。

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
        System.out.println(deviceIds [0]); // 1

        // 3. 获取数组长度/元素个数
        System.out.println(deviceIds.length); // 3

        // 4. 遍历数组 按顺序依次访问数组中元素
        for (int id : deviceIds ) {
            System.out.println("设备ID：" + id);
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

        // 4. 遍历数组
        for (int i :arr){
            System.out.println(i);
        }
    }
}
