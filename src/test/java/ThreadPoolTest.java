import ThreadPool.ThreadPoolExecutorWrapper;
import ThreadPool.ThreadRunningError;
import forkJoin.AreaCalculator;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ewan on 11/08/2017.
 */
public class ThreadPoolTest {
    @Test
    public void TestRunningErrorWithSubmit() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0,Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,new SynchronousQueue<>());
            //不能打印异常堆栈
            pool.submit(new ThreadRunningError());
    }

    @Test
    public void TestRunningErrorWithExecute() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0,Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,new SynchronousQueue<>());
            //可以打印异常堆栈
            pool.execute(new ThreadRunningError());
    }

    @Test
    public void TestRunningErrorWithThreadPoolWrapper() {
        ThreadPoolExecutor pool = new ThreadPoolExecutorWrapper(0,Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,new SynchronousQueue<>());
            //通过wrapper打印异常堆栈
            pool.submit(new ThreadRunningError());
    }

    @Test
    public void TestAreaCalculator() throws ExecutionException, InterruptedException {
        float area = AreaCalculator.Calculate(1, 100);
        System.out.println("area="+area);
    }
}
