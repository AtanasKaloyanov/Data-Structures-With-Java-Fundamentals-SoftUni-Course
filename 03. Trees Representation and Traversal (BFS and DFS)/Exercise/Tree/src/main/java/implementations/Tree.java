package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Tree<E> implements AbstractTree<E> {
    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key /*,Tree<E>... children*/) {
        this.key = key;
        this.children = new ArrayList<>();

//        for (Tree<E> currentChild : children) {
//            this.children.add(currentChild);
//            currentChild.setParent(this);
//        }
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.key).append(System.lineSeparator());

        int counter = 0;

        dfsRecursion(this, sb, counter);

        return sb.toString().trim();
    }

    private void dfsRecursion(Tree<E> tree, StringBuilder sb, int counter) {
        if (tree == null) {
            return;
        }

        counter++;

        for (Tree<E> current : tree.children) {
            sb.append("  ".repeat(Math.max(0, counter)));
            sb.append(current.key);
            sb.append(System.lineSeparator());
            dfsRecursion(current, sb, counter);
        }

    }

    @Override
    public List<E> getLeafKeys() {
        return null;
    }

    @Override
    public List<E> getMiddleKeys() {
        return null;
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        return null;
    }

    @Override
    public List<E> getLongestPath() {
        return null;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        return null;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        return null;
    }
}



