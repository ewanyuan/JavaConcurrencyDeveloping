package thread.deadLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ewan on 02/09/2017.
 */
public class DeadLockDemo {
    private static Lock place1 = new ReentrantLock();
    private static Lock place2 = new ReentrantLock();
    private static Lock place3 = new ReentrantLock();
    private static Lock place4 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread car1= new Thread(() -> {
            try {
                System.out.println("car1 was at place1");
                place1.lock();
                Thread.sleep(1000);
                System.out.println("car1 was waiting for place2");
                place2.lock();
                System.out.println("car1 arrived at place2");
                place1.unlock();
                System.out.println("car1 gave away place1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            place2.lock();
        });
        car1.setName("Car1");

        Thread car2= new Thread(() -> {
            try {
                System.out.println("car2 was at place4");
                place4.lock();
                Thread.sleep(100);
                System.out.println("car2 was waiting for place1");
                place1.lock();
                System.out.println("car2 arrived at place1");
                place4.unlock();
                System.out.println("car2 gave away place4");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        car2.setName("car2");

        Thread car3= new Thread(() -> {
            try {
                System.out.println("car3 was at place3");
                place3.lock();
                Thread.sleep(200);
                System.out.println("car3 was waiting for place4");
                place4.lock();
                System.out.println("car3 arrived at place4");
                place3.unlock();
                System.out.println("car3 gave away place3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        car3.setName("car3");

        Thread car4= new Thread(() -> {
            try {
                System.out.println("car4 was at place2");
                place2.lock();
                Thread.sleep(300);
                System.out.println("car4 was waiting for place3");
                place3.lock();
                System.out.println("car4 arrived place3");
                place2.unlock();
                System.out.println("car4 gave away place2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        car4.setName("car4");

        car1.join();
        car2.join();
        car3.join();
        car4.join();

        car1.start();
        car2.start();
        car3.start();
        car4.start();
    }
}
