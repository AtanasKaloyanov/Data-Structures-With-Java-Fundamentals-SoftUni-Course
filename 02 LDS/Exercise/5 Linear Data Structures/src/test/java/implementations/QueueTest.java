package implementations;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueueTest {
    private Queue<String> queue;

    @Before
    public void setUp() {
        this.queue = new Queue<>();
        for (int i = 0; i < 100; i++) {
            queue.offer(String.valueOf(i));
        }
    }

    @Test
    public void testOfferShouldAddAtTheEnd() {
        queue.offer("Slayer");
        assertEquals("0", queue.peek());
    }

    @Test
    public void testPopShouldRemoveAndReturnTopElement() {
        assertEquals("0", queue.poll());
        assertEquals(99, queue.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testPopShouldThrowWhenEmpty() {
        new Queue<>().poll();
    }

    @Test
    public void testPeekShouldReturnAndNotRemove() {
        assertEquals("0", queue.peek());
        assertEquals(100, queue.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testPeekShouldThrowWhenEmpty() {
        new Queue<>().peek();
    }

    @Test
    public void testSize() {
        assertEquals(100, queue.size());
        assertEquals(0, new Queue<>().size());
    }


    @Test
    public void testIsEmpty() {
        assertTrue(new Queue<>().isEmpty());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testIteratorShouldTraversItemsFromBeginToEnd() {
        int last = 0;
        for (String s : queue) {
            assertEquals(String.valueOf(last++), s);
        }
    }

    // my test

    @Test
    public void testNewNodeAddingWithTail() {
        Queue<Integer> queue1 = new Queue<>();
        queue1.offer(12);
        queue1.offer(13);
        queue1.offer(14);
    }

    // Node 2      Node 3
    //             Element 2

    // Node 1      Node 2
    // head        Element 12
}