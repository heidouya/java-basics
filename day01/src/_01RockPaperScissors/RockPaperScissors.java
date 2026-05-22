package _01RockPaperScissors;

import java.util.Random;
import java.util.Scanner;

/**
 * 猜拳游戏（石头、布、剪刀）
 * 规则：石头赢剪刀、剪刀赢布、布赢石头
 * 操作：1=石头，2=布，3=剪刀，0=退出游戏
 */
public class RockPaperScissors {

    /**
     * 程序入口
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("========== 欢迎来到猜拳游戏 ==========");
        System.out.println("操作说明：1=石头  2=布  3=剪刀  0=退出");
        System.out.println("=====================================");

        // 循环进行游戏，输入0时退出
        while (true) {
            System.out.print("\n请出拳（1=石头 2=布 3=剪刀 0=退出）：");

            // 处理非法字符输入，避免程序崩溃
            if (!scanner.hasNextInt()) {
                System.out.println("【提示】输入非法，请输入数字 0、1、2 或 3！");
                scanner.next(); // 消耗掉非法输入，避免死循环
                continue;
            }

            int playerChoice = scanner.nextInt();

            // 输入0退出游戏
            if (playerChoice == 0) {
                System.out.println("\n感谢游玩，再见！");
                break;
            }

            // 校验输入范围
            if (playerChoice < 1 || playerChoice > 3) {
                System.out.println("【提示】输入超出范围，请输入 1、2、3 或 0！");
                continue;
            }

            // 电脑随机生成 1~3 的出拳结果
            int computerChoice = random.nextInt(3) + 1;

            // 显示双方出拳结果
            System.out.println("------------------------------------");
            System.out.println("你的出拳：" + getChoiceName(playerChoice));
            System.out.println("电脑出拳：" + getChoiceName(computerChoice));

            // 判断胜负并输出结果
            String result = judgeResult(playerChoice, computerChoice);
            System.out.println("【结果】" + result);
            System.out.println("------------------------------------");
        }

        scanner.close();
    }

    /**
     * 将出拳数字转换为对应名称
     *
     * @param choice 出拳数字（1=石头，2=布，3=剪刀）
     * @return 出拳名称字符串
     */
    public static String getChoiceName(int choice) {
        switch (choice) {
            case 1: return "石头 ✊";
            case 2: return "布   ✋";
            case 3: return "剪刀 ✌";
            default: return "未知";
        }
    }

    /**
     * 判断猜拳胜负
     * 规则：石头(1)赢剪刀(3)，剪刀(3)赢布(2)，布(2)赢石头(1)
     *
     * @param player   玩家出拳（1/2/3）
     * @param computer 电脑出拳（1/2/3）
     * @return 胜负结果描述字符串
     */
    public static String judgeResult(int player, int computer) {
        // 平局：双方出拳相同
        if (player == computer) {
            return "平局！再来一局吧~";
        }

        // 利用数学规律判断胜负：
        // 玩家赢的情况：(player - computer + 3) % 3 == 1
        //   石头(1) vs 剪刀(3)：(1-3+3)%3 = 1%3 = 1 ✔
        //   剪刀(3) vs 布(2)  ：(3-2+3)%3 = 4%3 = 1 ✔
        //   布(2)   vs 石头(1)：(2-1+3)%3 = 4%3 = 1 ✔
        if ((player - computer + 3) % 3 == 1) {
            return "恭喜你，你赢了！ (^_^)";
        } else {
            return "很遗憾，电脑赢了！ (>_<)";
        }
    }
}
