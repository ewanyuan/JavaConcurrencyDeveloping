import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ewan on 27/07/2017.
 */
public class ConcurrencyTest {
    static ConcurrentLinkedQueue<Object> cq = new ConcurrentLinkedQueue<>();
    static LinkedBlockingQueue<Object> bq = new LinkedBlockingQueue<>();

    @Test
    public void CompareConcurrentLinkedQueueAndBlockingQueue() throws InterruptedException {
        //预热
        runC();
        runB();


        long startMili = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            runC();
        }
        long endMili = System.currentTimeMillis();
        System.out.println(endMili - startMili);


        startMili = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            runB();
        }
        endMili = System.currentTimeMillis();
        System.out.println(endMili - startMili);
    }

    private void runC() throws InterruptedException {
        ConcurrentLinkedQueueAddThread[] ca = new ConcurrentLinkedQueueAddThread[2];
        ConcurrentLinkedQueuePollThread[] cp = new ConcurrentLinkedQueuePollThread[2];
        for (int i = 0; i < 2; i++) {
            ca[i] = new ConcurrentLinkedQueueAddThread();
            cp[i] = new ConcurrentLinkedQueuePollThread();
        }
        for (int i = 0; i < 2; i++) {
            ca[i].start();
            cp[i].start();
        }
        for (int i = 0; i < 2; i++) {
            ca[i].join();
            cp[i].join();
        }
    }

    private void runB() throws InterruptedException {
        BlockingLinkedQueueAddThread[] ba = new BlockingLinkedQueueAddThread[2];
        BlockingLinkedQueuePollThread[] bp = new BlockingLinkedQueuePollThread[2];
        for (int i = 0; i < 2; i++) {
            ba[i] = new BlockingLinkedQueueAddThread();
            bp[i] = new BlockingLinkedQueuePollThread();
        }
        for (int i = 0; i < 2; i++) {
            ba[i].start();
            bp[i].start();
        }
        for (int i = 0; i < 2; i++) {
            ba[i].join();
            bp[i].join();
        }
    }

    static class ConcurrentLinkedQueueAddThread extends Thread {
        @Override
        public void run() {
            for(int i = 0; i < 2000000; i++) {
                cq.add("Random->"+Math.random());
            }
        }
    }

    static class ConcurrentLinkedQueuePollThread extends Thread {
        @Override
        public void run() {
            for(int i = 0; i < 2000000; i++) {
                cq.poll();
            }
        }
    }

    static class BlockingLinkedQueueAddThread extends Thread {
        @Override
        public void run() {
            for(int i = 0; i < 2000000; i++) {
                bq.add("Random->"+Math.random());
            }
        }
    }

    static class BlockingLinkedQueuePollThread extends Thread {
        @Override
        public void run() {
            for(int i = 0; i < 2000000; i++) {
                bq.poll();
            }
        }
    }
}
