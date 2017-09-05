package thread.notify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ewan on 11/07/2017.
 */
public class NotifyConcurrentQueue {
    private List<Integer> container = new ArrayList<>();
    final private int maxSize = 10;

    public synchronized Integer get() throws InterruptedException {
        if (container.size() < 1) {
            this.wait(); //会释放当前的锁
        }
        Integer first = container.get(0);
        container.remove(first);
        this.notifyAll();
        check();
        return first;
    }

    public synchronized void add(Integer integer) throws InterruptedException {
        if (container.size() >= maxSize) {
            this.wait();
        }
        container.add(integer);
        this.notifyAll();
        check();
    }

    private void check() {
        if (container.size() < 0 || container.size()>maxSize)
            System.out.println("Exceed Queue Maximum or Minimum Quota");
    }
}
