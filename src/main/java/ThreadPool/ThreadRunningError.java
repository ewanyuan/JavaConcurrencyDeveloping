package ThreadPool;

/**
 * Created by ewan on 11/08/2017.
 */
public class ThreadRunningError implements Runnable {
    @Override
    public void run() {
        myWork();
    }

    private void myWork(){
        double re = 1/0;
        System.out.println(re);
    }
}
