package thread.state;

/**
 * Created by ewan on 05/09/2017.
 */
public class ThreadStateDemo {
    public static void main(String[] args) throws InterruptedException {
        sleepAndJoin();

    }

    private static void sleepAndJoin() throws InterruptedException {
        Thread thread = new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(thread.getState());
        thread.start();

        Thread thread1 = new Thread(()->{
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();

        Thread.sleep(100);

        System.out.println("sleepThreadState:"+thread.getState());
        System.out.println("joinedInThreadState:"+thread1.getState());
    }
}
