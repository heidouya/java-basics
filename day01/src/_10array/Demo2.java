package _10array;

/*
    二维数组是「数组的数组」—— 可以理解成 “表格 / 矩阵”，有行和列两个维度，本质是存储「多个一维数组」的容器，比如用int[][] arr表示，第一个索引代表行，第二个代表列。

    二维数组格式：
    type[][] arrayRefVar = new type[arrLength1][arrLength2];
    如：int[][] a = new int[2][3];

    长度：
    1. 外层长度（行数量）：arr.length → 表示二维数组有多少行（即包含多少个一维数组）；
    2. 内层长度（列数量）：arr[i].length → 表示第i行有多少列（即该行一维数组的元素个数），每行长度可不同（不规则二维数组）。

    特性：
    1. 维度逻辑：arr[i][j] 中i是行索引，j是列索引，均从 0 开始；
    2. 长度特性：每行长度可不同（不规则二维数组），比如int[][] arr = {{1}, {2,3}} 是合法的；
    3. 内存本质：一维数组存的是 “行地址”，每行对应的一维数组在内存中可连续也可分散。

    总结：
    1. 二维数组核心：数组的数组，对应表格 / 矩阵，双索引访问（行 + 列）；
    2. 关键特性：索引从 0 开始、每行长度可不同、本质是嵌套一维数组；
    3. 实用价值：鸿蒙中处理二维坐标 / 表格数据，算法中解决矩阵 / 网格类问题等。
*/
public class Demo2 {
    public static void main(String[] args) {
        // 1. 声明一个二维数组
        int[][] arr = new int[2][3];

        // 2. 二维数组赋值
        arr[0][0] = 0;
        arr[0][1] = 1;
        arr[0][2] = 2;
        arr[1][0] = 3;
        arr[1][1] = 4;
        arr[1][2] = 5;
        /*
        [
            [0, 1, 2],
            [3, 4, 5]
        ]
        */

        // 3. 二维数组每行的长度
        for (int i = 0; i < arr.length; i++) {
            System.out.println("第" + i + "行长度：" + arr[i].length);
        }

        // 4. 遍历二维数组
        for (int[] subArr : arr) {
            for (int i : subArr) {
                System.out.println(i);
            }
        }

        //------------------------------------
        // 1. 定义二维数组：存储鸿蒙设备的“行号-列号-设备ID”（3行2列）
        int[][] harmonyDeviceIds = {
                {1001, 1002}, // 第0行：2个设备ID
                {1003, 1004}, // 第1行
                {1005}  // 第2行
        };

        // 2. 访问元素：第1行第0列的设备ID（索引从0开始）
        System.out.println(harmonyDeviceIds[1][0]); // 输出1003

        // 3. 遍历二维数组（鸿蒙中批量处理表格数据常用）
        for (int[] row : harmonyDeviceIds) { // 遍历每一行（一维数组）
            for (int id : row) { // 遍历该行的每个元素
                System.out.print(id + " ");
            }
            System.out.println();
        }
    }
}
