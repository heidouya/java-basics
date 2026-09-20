package _01thread;

public class Demo7 {
    public static void main(String[] args) {
        SaleTicketThread saleTicketThread1 = new SaleTicketThread();
        SaleTicketThread saleTicketThread2 = new SaleTicketThread();
        SaleTicketThread saleTicketThread3 = new SaleTicketThread();

        saleTicketThread1.setName("电影院窗口1");
        saleTicketThread2.setName("电影院窗口2");
        saleTicketThread3.setName("电影院窗口3");

        saleTicketThread1.start();
        saleTicketThread2.start();
        saleTicketThread3.start();
    }
}

class SaleTicketThread extends Thread {
    static int ticketNum = 0;

    @Override
    public void run() {
        while (true) {
            synchronized (SaleTicketThread.class) {
                if (ticketNum >= 10) {
                    break;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(STR."\{getName()}正在卖第\{++ticketNum}张票");
            }
        }
    }
}
