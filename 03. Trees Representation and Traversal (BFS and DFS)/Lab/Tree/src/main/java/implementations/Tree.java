package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Tree<E> implements AbstractTree<E> {
    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key, Tree<E>... children) {
        this.key = key;
        this.children = new ArrayList<>();

        for (Tree<E> child : children) {
            this.children.add(child);
            child.parent = this;
        }
    }


    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();
        ArrayDeque<Tree<E>> queue = new ArrayDeque<>();

        result.add(this.key);
        queue.offer(this);

        while (queue.size() != 0) {
            Tree<E> currentTree = queue.poll();

            for (Tree<E> currentChild : currentTree.children) {
                result.add(currentChild.key);
                queue.offer(currentChild);
            }
        }

        return result;
    }

    //                                                        7

    //                                            19          21           4

    //                                      1    12   31                31    6
    @Override
    public List<E> orderDfs() {
        List<E> result = new ArrayList<>();
        dfsRecursion(this, result);

        return result;
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> searchedTree = findTree(parentKey);
        child.parent = searchedTree;
        searchedTree.children.add(child);
    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> treeForRemoving = findTree(nodeKey);

        if (treeForRemoving == null) {
            throw new IllegalStateException();
        }

        Tree<E> parent = treeForRemoving.parent;
        parent.children.remove(treeForRemoving);
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstTree = findTree(firstKey);
        Tree<E> secondTree = findTree(secondKey);

        Tree<E> firstParent = firstTree.parent;
        Tree<E> secondParent = secondTree.parent;

        firstTree.parent = secondParent;
        secondTree.parent = firstParent;

        int firstIndex = firstParent.children.indexOf(firstTree);
        int secondIndex = secondParent.children.indexOf(secondTree);

        firstParent.children.set(firstIndex, secondTree);
        secondParent.children.set(secondIndex, firstTree);


    }

    private void dfsRecursion(Tree<E> tree, List<E> result) {
        for (Tree<E> child : tree.children) {
            this.dfsRecursion(child, result);
        }

        result.add(tree.key);
    }

    private Tree<E> findTree(E parentKey) {
        Tree<E> searched = null;

        Deque<Tree<E>> deque = new ArrayDeque<>();
        deque.offer(this);

        while (deque.size() != 0) {
            Tree<E> firstTree = deque.poll();

            if (firstTree.key.equals(parentKey)) {
                searched = firstTree;
                break;
            }

            for (Tree<E> currentTree : firstTree.children) {
                deque.offer(currentTree);
            }

        }

        return searched;
    }

}



