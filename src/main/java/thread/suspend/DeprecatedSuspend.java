package thread.suspend;

public class DeprecatedSuspend {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name){
            super.setName(name);
        }
        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in "+getName());
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        //先resume，再suspsend线程会无限期挂住
        //而wait和notify会检查，先notify再wait会抛出异常
        t1.resume();
        t2.resume();
        t1.join();
        t2.join();
    }
}
