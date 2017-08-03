import notify.MyConcurrentQueue;
import org.junit.Test;

/**
 * Created by ewan on 27/07/2017.
 */
public class ConcurrencyTest {
    @Test
    public void TestMyConcurrentQueue() {
        MyConcurrentQueue queue = new MyConcurrentQueue();

        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    queue.add(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    queue.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }
}
