package thread.interrupt;

import java.math.BigDecimal;

/**
 * Created by ewan on 05/09/2017.
 */
public class WorkingInterrupted {
    private static Integer ticketsNumber = 1000000;
    private static BigDecimal income = new BigDecimal(0);

    public static void main(String[] args) throws InterruptedException {
        WorkingThread workingThread = new WorkingThread(ticketsNumber, income);

        workingThread.start();

        Thread.sleep(2000);

        workingThread.interrupt();
    }
}
