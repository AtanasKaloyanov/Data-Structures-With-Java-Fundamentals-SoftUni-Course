package implementations;

import interfaces.AbstractBinaryTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public class BinaryTree<E> implements AbstractBinaryTree<E> {
    private E key;
    private BinaryTree<E> left;
    private BinaryTree<E> right;

    public BinaryTree(E key, BinaryTree<E> left, BinaryTree<E> right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public AbstractBinaryTree<E> getLeft() {
        return this.left;
    }

    @Override
    public AbstractBinaryTree<E> getRight() {
        return this.right;
    }

    @Override
    public void setKey(E key) {
        this.key = key;
    }

//    @Override
//    public String asIndentedPreOrder(int indent) {
//        StringBuilder result = new StringBuilder();
//        result.append(this.key).append(System.lineSeparator());
//
//        appending(this.left, result, indent);
//        appending(this.right, result, indent);
//
//        return result.toString().trim();
//    }
//
//    private void appending(BinaryTree<E> tree, StringBuilder result, int indent) {
//        Queue<BinaryTree<E>> queue = new ArrayDeque<>();
//        queue.offer(tree);
//
//        int counter = 1;
//
//        while (!queue.isEmpty()) {
//            BinaryTree<E> current = queue.poll();
//
//            if (counter % 2 == 0) {
//                indent++;
//            }
//
//            for (int i = 0; i <= indent; i++) {
//                result.append("  ");
//            }
//
//            counter++;
//
//            result.append(current.key);
//            result.append(System.lineSeparator());
//
//            if (current.left != null) {
//                queue.offer(current.left);
//            }
//
//            if (current.right != null) {
//                queue.offer(current.right);
//            }
//        }
//    }

    //                     17

    //         9                        25
    //
    //    3         11             20         31

    @Override
    public String asIndentedPreOrder(int indent) {
        StringBuilder result = new StringBuilder();

        String padding = createPadding(indent) + this.getKey();
        result.append(padding);

        if (this.getLeft() != null) {
            String preOrder = this.getLeft().asIndentedPreOrder(indent + 2);
            result.append(System.lineSeparator()).append(preOrder);
        }

        if (this.getRight() != null) {
            String preOrder = this.getRight().asIndentedPreOrder(indent + 2);
            result.append(System.lineSeparator()).append(preOrder);
        }

        return result.toString();
    }

    private String createPadding(int indent) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < indent; i++) {
            builder.append(" ");
        }

        return builder.toString();
    }

    @Override
    public List<AbstractBinaryTree<E>> preOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        preOrderRecc(this, result);

        return result;
    }

    private void preOrderRecc(BinaryTree<E> root, List<AbstractBinaryTree<E>> result) {
        if (root == null) {
            return;
        }

        result.add(root);

        BinaryTree<E> left = root.left;
        preOrderRecc(left, result);
        BinaryTree<E> right = root.right;
        preOrderRecc(right, result);
    }

    //                     17

    //         9                        25
    //
    //    3         11             20         31


    // 17, 9, 3, 11, 25, 20, 31

    @Override
    public List<AbstractBinaryTree<E>> inOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();
        inOrderRecc(this, result);
        return result;
    }

    private void inOrderRecc(BinaryTree<E> current, List<AbstractBinaryTree<E>> result) {
        if (current == null) {
            return;
        }

        BinaryTree<E> left = current.left;
        inOrderRecc(left, result);
        result.add(current);

        BinaryTree<E> right = current.right;
        inOrderRecc(right, result);
    }

    //                     17

    //         9                        25
    //
    //    3         11             20         31


    // 3, 9, 11, 17, 20, 25, 31


    @Override
    public List<AbstractBinaryTree<E>> postOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();
        postOrderRecc(this, result);
        return result;
    }

    private void postOrderRecc(BinaryTree<E> current, List<AbstractBinaryTree<E>> result) {
        if (current == null) {
            return;
        }

        BinaryTree<E> left = current.left;
        postOrderRecc(left, result);

        BinaryTree<E> right = current.right;
        postOrderRecc(right, result);

        result.add(current);
    }

    @Override
    public void forEachInOrder(Consumer<E> consumer) {
        if (this.getLeft() != null) {
            this.getLeft().forEachInOrder(consumer);
        }

        consumer.accept(this.getKey());

        if (this.getRight() != null) {
            this.getRight().forEachInOrder(consumer);
        }

    }
}
