package lockWeight;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class BiasedLockTest {
    public static ConcurrentHashMap<Integer, String> numberList = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println("输入d开始，输入q退出");

            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();

            if (line.equals("d")) {
                long begin = System.currentTimeMillis();
                for (int i = 0; i < 10000000; i++) {
                    numberList.put(i, "hello_"+i);
                }
                long end = System.currentTimeMillis();
                System.out.println(end - begin);
            } else if (line.equals("q")) {
                break;
            }
        }
    }
}  