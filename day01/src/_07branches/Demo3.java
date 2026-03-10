package _07branches;

/*
    switch(条件表达式){
        case value1 :
           // 条件表达式执行结果为 value1 时要执行的代码
           break; // break可选，如果去掉break，执行完满足条件的case下面的代码后，会依次继续执行其余 case 下的代码，直到遇到break或者执行完所有代码（包括default下的代码）才停止
        case value2 :
           // 条件表达式执行结果为 value2 时要执行的代码
           break; //可选
        ...
        default : // default 可选
           // 条件表达式执行结果
    }
*/
public class Demo3 {
    public static void main(String[] args) {
        String vip = "VIP";

        switch (vip) {
            case "VIP":
                System.out.println("您是VIP会员");
                break;
            case "VIP儿童":
                System.out.println("您是VIP儿童会员");
                break;
            case "VIP白金":
                System.out.println("您是VIP白金会员");
                break;
            case "VIP黄金":
                System.out.println("您是VIP黄金会员");
                break;
            case "SVIP":
                System.out.println("您是SVIP会员");
                break;
            default:
                System.out.println("您还不是会员或者您输入的会员级别有误");
        }

        // switch 简化
         switch (vip) {
            case "VIP" -> System.out.println("您是VIP会员");
            case "VIP儿童"-> System.out.println("您是VIP儿童会员");
            case "VIP白金" -> System.out.println("您是VIP白金会员");
            case "VIP黄金" -> System.out.println("您是VIP黄金会员");
            case "SVIP" -> System.out.println("您是SVIP会员");
            default -> System.out.println("您还不是会员或者您输入的会员级别有误");
        }

        // swtich 表达式
        String res1 = switch (vip) {
            case "VIP" -> "您是VIP会员";
            case "VIP儿童"-> "您是VIP儿童会员";
            case "VIP白金" -> "您是VIP白金会员";
            case "VIP黄金" -> "您是VIP黄金会员";
            case "SVIP" -> "您是SVIP会员";
            default -> "您还不是会员或者您输入的会员级别有误";
        };
        System.out.println(res1);

        // 多模式匹配
        vip = "vip1";
        String res2 = switch (vip) {
            case "VIP", "vip", "vip1" -> "您是VIP会员";
            case "VIP儿童", "vip儿童"-> "您是VIP儿童会员";
            case "VIP白金", "vip白金" -> "您是VIP白金会员";
            case "VIP黄金", "vip黄金" -> "您是VIP黄金会员";
            case "SVIP", "svip" -> "您是SVIP会员";
            default -> "您还不是会员或者您输入的会员级别有误";
        };
        System.out.println(res2);
    }
}
