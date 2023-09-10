package implementations;

import org.junit.Test;


public class ArrayDequeTest {

    @Test
    public void testArrayDeque() {
            ArrayDeque<Integer> deque = new ArrayDeque<>();
            deque.offer(13);
            deque.offer(14);
            deque.offer(15);
            deque.offer(16);
            deque.offer(17);

            for (Integer e: deque) {
                System.out.println(e);
            }

    }
}