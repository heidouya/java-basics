package _04polymorphism;

/* 多态示例1 */
public class Demo3 {
    public static void main(String[] args) {
        PaymentService service = new PaymentService();

        service.processPayment(new Alipay(100));
        service.processPayment(new WechatPay(200));
        service.processPayment(new CreditCardPay(500, "1234-5678"));
    }
}

// 支付抽象类
abstract class Payment {
    protected double amount;

    public Payment(double amount) {
        this.amount = amount;
    }

    public abstract void pay();
}

// 支付宝支付
class Alipay extends Payment {
    public Alipay(double amount) {
        super(amount);
    }

    @Override
    public void pay() {
        System.out.println("使用支付宝支付：" + amount + "元");
        // 支付宝特有逻辑
    }
}

// 微信支付
class WechatPay extends Payment {
    public WechatPay(double amount) {
        super(amount);
    }

    @Override
    public void pay() {
        System.out.println("使用微信支付：" + amount + "元");
        // 微信特有逻辑
    }
}

// 信用卡支付
class CreditCardPay extends Payment {
    private String cardNumber;

    public CreditCardPay(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay() {
        System.out.println("使用信用卡支付：" + amount + "元");
        System.out.println("卡号：" + cardNumber);
    }
}

// 支付服务
class PaymentService {
    public void processPayment(Payment payment) {
        payment.pay();  // 多态：根据实际类型执行不同逻辑
        System.out.println("支付完成\n");
    }
}
