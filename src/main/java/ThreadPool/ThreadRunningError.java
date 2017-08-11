package ThreadPool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ewan on 11/08/2017.
 */
public class ThreadRunningError implements Runnable {
    @Override
    public void run() {
        double re = 1/0;
        System.out.println(re);
    }
}
