package lockFree;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomic {
    // 整数最大值
    private static int M = 1000000;

    /**
     * 无锁方法
     *
     * @throws InterruptedException
     */
    private static void atomicMethod(int threadCount) throws InterruptedException {
        AtomicRun atomicRun = new AtomicRun();
        AtomicRun.endValue = M;

        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        // 开始时间
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            service.submit(atomicRun);
        }
        service.shutdown();
        service.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        // 结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("无锁开始时间为 ： " + startTime
                + " 结束时间为 ： " + endTime + " 耗费时间为 ： " + (endTime - startTime)
                + "ms" + " value:" + AtomicRun.atomicInteger);
    }

    /**
     * 加锁方法
     *
     * @throws InterruptedException
     */
    private static void synMethod(int threadCount) throws InterruptedException {
        SynRun synRun = new SynRun();
        SynRun.endValue = M;

        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            service.submit(synRun);
        }
        service.shutdown();
        service.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        long endTime = System.currentTimeMillis();
        System.out.println("有锁开始时间为 ： " + startTime
                + " 结束时间为 ： " + endTime + " 耗费时间为 ： " + (endTime - startTime)
                + "ms" + " value:" + AtomicRun.atomicInteger);
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 3;
        System.out.println("当线程数量为 ： " + threadCount + "时：");
        atomicMethod(threadCount);
        synMethod(threadCount);

        threadCount = 30;
        System.out.println("当线程数量为 ： " + threadCount + "时：");
        atomicMethod(threadCount);
        synMethod(threadCount);

        threadCount = 300;
        System.out.println("当线程数量为 ： " + threadCount + "时：");
        atomicMethod(threadCount);
        synMethod(threadCount);

        threadCount = 1000;
        System.out.println("当线程数量为 ： " + threadCount + "时：");
        atomicMethod(threadCount);
        synMethod(threadCount);
    }
}

class AtomicRun implements Runnable {

    protected static AtomicInteger atomicInteger = new AtomicInteger();
    protected static int endValue;

    @Override
    public void run() {
        int startValue = atomicInteger.get();
        while (startValue < endValue) {
            startValue = atomicInteger.incrementAndGet();
        }
    }
}

class SynRun implements Runnable {

    protected static int startValue;
    protected static int endValue;

    @Override
    public void run() {
        while (startValue < endValue) {
            addValue();
        }
    }

    private synchronized void addValue() {
        startValue++;
    }
}