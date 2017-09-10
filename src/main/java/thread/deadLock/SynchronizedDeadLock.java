package thread.deadLock;

import java.math.BigDecimal;

/**
 * Created by ewan on 02/09/2017.
 */
public class SynchronizedDeadLock {

    private static BigDecimal sellerBasket = new BigDecimal(0);
    private static Goods goods = new Goods();

    private static class Goods{
        private String Owner;

        public void setOwner(String owner) {
            Owner = owner;
        }

        public String getOwner() {
            return Owner;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread seller = new Thread(() -> {
            try {
                System.out.println("seller owns goods");
                Thread.sleep(100);
                synchronized (goods) {
                    System.out.println("seller is waiting for money");

                    while (true) {
                        if (sellerBasket.compareTo(new BigDecimal(100)) == 0) {
                            System.out.println("seller get money");
                            Thread.sleep(100);
                            goods.setOwner("customer");
                            System.out.println("seller give out the goods");
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        seller.setName("Seller");

        Thread customer = new Thread(() -> {
            try {
                System.out.println("customer owns money");
                Thread.sleep(100);
                synchronized (sellerBasket) {
                    System.out.println("customer is waiting for goods");
                    while (true) {
                        if (goods.getOwner() == "customer") {
                            System.out.println("customer get goods");
                            Thread.sleep(100);
                            sellerBasket.add(new BigDecimal(100));
                            System.out.println("seller give out money");
                        }
                    }
                }
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
