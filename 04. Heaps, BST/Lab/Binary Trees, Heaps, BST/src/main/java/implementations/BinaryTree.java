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


     /*
          17
            9
              3
              11
            25
              20
              21
     */

    //                     17

    //         9                        25
    //
    //    3         11             20         31

    @Override
    public String asIndentedPreOrder(int indent) {
        StringBuilder result = new StringBuilder();

        result.append(createPadding(indent)).append(this.key);

        if (this.left != null) {
            String preOrder = getLeft().asIndentedPreOrder(indent + 2);
            result.append(System.lineSeparator());
            result.append(preOrder);
        }

        if (this.right != null) {
            String preOrder = getRight().asIndentedPreOrder(indent + 2);
            result.append(System.lineSeparator());
            result.append(preOrder);

        }

        return result.toString();
    }

    private String createPadding(int indent) {
        StringBuilder indentResult = new StringBuilder();

        for (int i = 0; i < indent; i++) {
            indentResult.append(" ");
        }

        return indentResult.toString();
    }


    @Override
    public List<AbstractBinaryTree<E>> preOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        preOrderRecc(this, result);

        return result;
    }

    private void preOrderRecc(BinaryTree<E> current, List<AbstractBinaryTree<E>> result) {

        result.add(current);

        if (current.left != null) {
            preOrderRecc(current.left, result);
        }

        if (current.right != null) {
            preOrderRecc(current.right, result);
        }
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

        if (current.left != null) {
            inOrderRecc(current.left, result);
        }

        result.add(current);
        if (current.right != null) {
            inOrderRecc(current.right, result);
        }
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
        if (current.left != null) {
            postOrderRecc(current.left, result);
        }

        if (current.right != null) {
            postOrderRecc(current.right, result);
        }

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
