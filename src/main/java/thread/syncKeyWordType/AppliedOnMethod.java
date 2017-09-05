package thread.syncKeyWordType;

/**
 * Created by ewan on 05/09/2017.
 */
public class AppliedOnMethod implements Runnable{
    private static int i=0;
    public synchronized void increase(){
        i++;
    }

    @Override
    public void run() {
        for(int j=0;j<10000000;j++){
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        AppliedOnMethod appliedOnMethod = new AppliedOnMethod();
//        Thread t1=new Thread(appliedOnMethod);
//        Thread t2=new Thread(appliedOnMethod);
        Thread t1=new Thread(new AppliedOnMethod());
        Thread t2=new Thread(new AppliedOnMethod());
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
