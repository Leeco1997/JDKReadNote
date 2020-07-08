package util.collections.queue.blockingQueue;

import java.util.concurrent.*;

/**
 * @author liqiao
 * @date 2020/7/8 12:46
 * @description
 */

public class ArrayBlockingTest {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<Integer>(5);
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<Integer>();

        SynchronousQueue synchronousQueue = new SynchronousQueue();
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
    }
}
