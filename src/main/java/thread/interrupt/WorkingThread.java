package thread.interrupt;

import java.math.BigDecimal;

public class WorkingThread extends Thread {

    private Integer ticketsNumber;
    private BigDecimal income;

    public WorkingThread(Integer ticketsNumber, BigDecimal income) {
        this.ticketsNumber = ticketsNumber;
        this.income = income;
    }

    @Override
    public void run() {
        while (true) {
            if (Thread.interrupted())
            {//working中断
                break;
            }
            synchronized (WorkingThread.class) {
                ticketsNumber = ticketsNumber - 1;
                income = income.add(new BigDecimal(100));
                System.out.println("a ticket was sold.");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //sleep中断
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Stop selling tickets.");
    }
}