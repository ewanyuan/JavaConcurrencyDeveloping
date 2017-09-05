package ThreadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ewan on 11/08/2017.
 */
public class ThreadPoolExecutorWrapper extends ThreadPoolExecutor {
    public ThreadPoolExecutorWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public Future<?> submit(Runnable task) {
        Runnable newTask = () -> {
            try{
                task.run();
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }
        };
        return super.submit(newTask);
    }
}
