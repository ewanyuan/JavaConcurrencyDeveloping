package thread.notify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ewan on 11/07/2017.
 */
public class BlockingConcurrentQueue {
    private List<Integer> container = new ArrayList<>();
    final private int maxSize = 10;

    public synchronized Integer get() throws InterruptedException {
        //第一种如下，方法内不断循环，消耗CPU资源。如果循环时间长CPU资源浪费会很严重
        //第二种，退出后再重试，线程上下文不断context switches，也很耗资源
        while (true) {
            if (container.size() > 0)
            {
                Integer first = container.get(0);
                container.remove(first);
                check();
                return first;
            }
        }
    }

    public synchronized void add(Integer integer) throws InterruptedException {
        while (true) {
            if (container.size() < maxSize)
            {
                container.add(integer);
                check();
            }
        }
    }

    private void check() {
        if (container.size() < 0 || container.size()>maxSize)
            System.out.println("Exceed Queue Maximum or Minimum Quota");
    }
}
