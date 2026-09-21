package _01thread;

import java.util.concurrent.locks.ReentrantLock;

public class Demo10 {
    public static void main(String[] args) {
        SaleTicketThread4 saleTicket1 = new SaleTicketThread4();
        SaleTicketThread4 saleTicket2 = new SaleTicketThread4();
        SaleTicketThread4 saleTicket3 = new SaleTicketThread4();

        saleTicket1.setName("窗口1");
        saleTicket2.setName("窗口2");
        saleTicket3.setName("窗口3");

        saleTicket1.start();
        saleTicket2.start();
        saleTicket3.start();
    }
}

class SaleTicketThread4 extends Thread {
    static int ticketNum = 0;
    static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (ticketNum >= 10) break;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(STR."\{getName()}正在卖第\{++ticketNum}张票");
            lock.unlock();
        }
    }
}
