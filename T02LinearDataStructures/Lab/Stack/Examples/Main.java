package T02LinearDataStructures.Lab.Stack.Examples;

import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        // stack
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack);  // 3, 2, 1

        System.out.println(stack.size());

        System.out.println(stack.isEmpty());

        System.out.println(stack.pop());

        stack.forEach( (element) -> System.out.print(element + " "));

        System.out.println(stack.contains(4));

        // queue

       ArrayDeque<Integer> queue = new ArrayDeque<>();

       queue.offer(1);
       queue.offer(2);
       queue.offer(3);

       System.out.println(queue.poll());
       System.out.println(queue);

       System.out.println(queue.peek());
    }
}
