package readWriteLock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by ewan on 10/09/2017.
 */
public class ReadWriteLockDemo {

    private static class TicketSeller {
        private static long ticketNumber = 10000;

        private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        private static void Sell() throws Exception {
            readWriteLock.writeLock().lock();
            if (readTicketNumber() < 1) {
                throw new Exception("Tickets have been Sold out");
            }

            try {
                try {
                    //模拟写要耗费的时间
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticketNumber = ticketNumber - 1;
                System.out.println("A ticket was Sold");
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }

        private static long readTicketNumber() {
            readWriteLock.readLock().lock();

            try {
                //模拟读要耗费的时间
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ticketNumber;
            } finally {
                readWriteLock.readLock().unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long beginTime = System.currentTimeMillis();

        //读
        Runnable readRunnable = () -> {
            System.out.println("Remaining tickets are " + TicketSeller.readTicketNumber());
        };

        ArrayList<Thread> readThreadList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(readRunnable);
            readThreadList.add(thread);
        }

        //写
        Runnable writeRunnable = () -> {
            try {
                TicketSeller.Sell();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        ArrayList<Thread> writeThreadList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(writeRunnable);
            writeThreadList.add(thread);
        }


        for (Thread writeThread: writeThreadList
                ) {
            writeThread.start();
        }
        for (Thread readThread: readThreadList
                ) {
            readThread.start();
        }
        for (Thread writeThread: writeThreadList
                ) {
            writeThread.join();
        }
        for (Thread readThread: readThreadList
                ) {
            readThread.join();
        }


        System.out.println("ReadWriteLock耗时:" + (System.currentTimeMillis() - beginTime));
    }
}
