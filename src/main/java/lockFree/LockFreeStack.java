package lockFree;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by ewan on 27/07/2017.
 * from stackoverflow.com
 */
public class LockFreeStack<E> {
    private volatile AtomicReference<Node<E>> head = new AtomicReference<Node<E>>();

    public E peek() {
        E payload = null;
        Node<E> oldHeadNode = head.get();
        if (oldHeadNode != null) { payload = head.get().payload; }
        return payload;
    }

    public E pop() {
        E payload;
        while (true) {
            Node<E> oldHeadNode = head.get();
            if (oldHeadNode == null) { return null; }
            payload = head.get().payload;
            if (head.compareAndSet(oldHeadNode, oldHeadNode.next.get())) { break; }
//System.out.println("Retry");
        }
        return payload;
    }

    public void push(E e) {
        Node<E> oldHeadNode = new Node<E>(e);

        while (true) {
            Node<E> oldRootNode = head.get();
            if (oldRootNode != null) { oldHeadNode.next.set(oldRootNode); }
            if (head.compareAndSet(oldRootNode, oldHeadNode)) { break; }
//System.out.println("Retry");
        }
    }

    public class Node<E> {
        private E payload;
        private AtomicReference<Node<E>> next;

        public Node(E e) {
            payload = e;
            next = new AtomicReference<Node<E>>();
        }
    }

    public static class RandomStackUse implements Runnable {
        private LockFreeStack<String> stack;
        private Random rand = new Random();

        public RandomStackUse(LockFreeStack<String> stack) {this.stack = stack;}

        @Override
        public void run() {
            long counter = 0;
            while (true) {
                if (rand.nextInt() % 3 == 0) {
                    stack.push(String.valueOf(counter++));
//System.out.println(String.format("%s pushed %d", Thread.currentThread().getName(), counter));
                }
                if (rand.nextInt() % 3 == 1) {
                    String value = stack.pop();
//System.out.println(String.format("%s pop %s", Thread.currentThread().getName(), value));
                }
                if (rand.nextInt() % 3 == 2) {
                    String value = stack.peek();
//System.out.println(String.format("%s peek %s", Thread.currentThread().getName(), value));
                }
            }
        }
    }
}