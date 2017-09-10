package thread.deadLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ewan on 02/09/2017.
 */
public class ReentrantDeadLock {
    private static Lock money = new ReentrantLock();
    private static Lock goods = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread seller = new Thread(() -> {
            try {
                System.out.println("seller owns goods");
                goods.lock();
                Thread.sleep(100);
                System.out.println("seller is waiting for money");
                money.lock();
                System.out.println("seller get money");
                goods.unlock();
                System.out.println("seller give out the goods");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        seller.setName("Seller");

        Thread customer = new Thread(() -> {
            try {
                System.out.println("customer owns money");
                money.lock();
                Thread.sleep(100);
                System.out.println("customer is waiting for goods");
                goods.lock();
                System.out.println("customer get goods");
                money.unlock();
                System.out.println("customer give out the money");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        seller.setName("Customer");

        seller.start();
        customer.start();

        seller.join();
        customer.join();
    }
}
