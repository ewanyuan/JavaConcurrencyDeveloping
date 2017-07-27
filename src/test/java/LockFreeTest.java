/**
 * Created by ewan on 27/07/2017.
 */
import lockFree.LockFreeStack;
import org.junit.Test;
import static org.junit.Assert.*;


public class LockFreeTest {
    static LockFreeStack<Object> stack = new LockFreeStack<>();

    @Test
    public void TestLockFreeStack() {
        try {
            Push[] pushes = new Push[10];
            Pop[] pop = new Pop[10];
            for (int i = 0; i < 10; i++) {
                pushes[i] = new Push();
                pop[i] = new Pop();
            }
            for (int i = 0; i < 10; i++) {
                pushes[i].start();
                pop[i].start();
            }
            for (int i = 0; i < 10; i++) {
                pushes[i].join();
                pop[i].join();
            }
            assertTrue(true);
        } catch (Exception ex) {
            //if the lock free stack is replaced with a normal stack, an emptyStackException or
            //overflowException will be thrown, cause it is not thread safe.
            assertTrue(false);
        }
    }

    static class Push extends Thread {
        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                stack.push("Random->"+Math.random());
            }
        }
    }

    static class Pop extends Thread {
        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                System.out.println("已出栈：" + stack.pop());
            }
        }
    }
}