package thread.priority;


public class PriorityDemo {

    private static Runnable runnable = ()->{
        int count=0;
        while(true){
            synchronized(PriorityDemo.class){
                count++;
                if(count>10000000){
                    System.out.println(Thread.currentThread().getName() + " completed");
                    break;
                }
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Thread thread1=new Thread(runnable);
        thread1.setPriority(Thread.MAX_PRIORITY);
        thread1.setName("High Priority Thread");

        Thread thread2=new Thread(runnable);
        thread2.setPriority(Thread.MIN_PRIORITY);
        thread2.setName("Low Priority Thread");

        thread1.start();
        thread2.start();
    }
}
