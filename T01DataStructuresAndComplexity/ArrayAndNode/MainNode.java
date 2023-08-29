package T01DataStructuresAndComplexity.ArrayAndNode;


import java.util.ArrayList;
import java.util.List;

public class MainNode {
    static class Node {
        private int element;
        private Node nextNode;
    }

    public static void main(String[] args) {
        Node currentLast = new Node();
        currentLast.element = 5;

        List<Node> nodes = new ArrayList();

        for (int i = 1; i < 100; i++) {
            Node newNode = new Node();
            newNode.element = i + 5;
            currentLast.nextNode = newNode;

            nodes.add(currentLast);
            currentLast = newNode;
        }

            nodes.forEach( (currentElement) -> {
                int currentInt = currentElement.element;
                int nextElementInt = currentElement.nextNode.element;

                System.out.println(currentInt + " " + nextElementInt);
            });
    }
}
