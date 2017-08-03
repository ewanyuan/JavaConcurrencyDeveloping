import lockFree.LockFreeStack;
import notify.MyConcurrentQueue;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ewan on 27/07/2017.
 */
public class ConcurrencyTest {
    static ConcurrentLinkedQueue<Object> cq = new ConcurrentLinkedQueue<>();
    static LinkedBlockingQueue<Object> bq = new LinkedBlockingQueue<>();

    @Test
    public void CompareConcurrentLinkedQueueAndBlockingQueue() throws InterruptedException {
        long startMili = System.currentTimeMillis();
        ConcurrentLinkedQueueAddThread[] ca = new ConcurrentLinkedQueueAddThread[100];
        ConcurrentLinkedQueuePollThread[] cp = new ConcurrentLinkedQueuePollThread[100];
        for (int i = 0; i < 100; i++) {
            ca[i] = new ConcurrentLinkedQueueAddThread();
            cp[i] = new ConcurrentLinkedQueuePollThread();
        }
        for (int i = 0; i < 100; i++) {
            ca[i].start();
            cp[i].start();
        }
        for (int i = 0; i < 100; i++) {
            ca[i].join();
            cp[i].join();
        }
        long endMili = System.currentTimeMillis();
        System.out.println(endMili - startMili);


        startMili = System.currentTimeMillis();
        BlockingLinkedQueueAddThread[] ba = new BlockingLinkedQueueAddThread[100];
        BlockingLinkedQueuePollThread[] bp = new BlockingLinkedQueuePollThread[100];
        for (int i = 0; i < 100; i++) {
            ba[i] = new BlockingLinkedQueueAddThread();
            bp[i] = new BlockingLinkedQueuePollThread();
        }
        for (int i = 0; i < 100; i++) {
            ba[i].start();
            bp[i].start();
        }
        for (int i = 0; i < 100; i++) {
            ba[i].join();
            bp[i].join();
        }
        endMili = System.currentTimeMillis();
        System.out.println(endMili - startMili);
    }

    static class ConcurrentLinkedQueueAddThread extends Thread {
        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                cq.add("Random->"+Math.random());
            }
        }
    }

    static class ConcurrentLinkedQueuePollThread extends Thread {
        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                cq.poll();
            }
        }
    }

    static class BlockingLinkedQueueAddThread extends Thread {
        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                bq.add("Random->"+Math.random());
            }
        }
    }

    static class BlockingLinkedQueuePollThread extends Thread {
        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                bq.poll();
            }
        }
    }
}
