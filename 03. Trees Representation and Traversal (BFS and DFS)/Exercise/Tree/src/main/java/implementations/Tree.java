package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

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

    public Tree() {
        this.children = new ArrayList<>();
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

        dfsReccursion(this, sb, 0);

        return sb.toString().trim();
    }

    @Override
    public List<E> getLeafKeys() {
        List<E> result = new ArrayList<>();
        getLeafsReccursion(this, result);
        return result;

//        return getTreesDFS(this)
//                .stream()
//                .filter( (tree) -> tree.children.size() == 0)
//                .map( (tree) -> tree.key)
//                .collect(Collectors.toList());
    }

    @Override
    public List<E> getMiddleKeys() {
        List<E> result = new ArrayList();
        getMiddleKeysReccursion(this, result);
        return result;

//        return getTreesDFS(this)
//                .stream()
//                .filter( (tree) -> tree.parent != null && tree.children.size() != 0)
//                .map(Tree::getKey)
//                .collect(Collectors.toList());
    }

    private void getMiddleKeysReccursion(Tree<E> tree, List<E> result) {
        if (tree == null) {
            return;
        }

        for (Tree<E> current : tree.children) {
            if (current.children.size() > 0 && current.parent != null) {
                result.add(current.key);
            }

            getMiddleKeysReccursion(current, result);
        }

    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> result = new ArrayList<>();
        int[] maxPath = new int[1];

        result.add(new Tree<>());

        int max = 0;
        findDeepestNodeDFS(result, maxPath, max, this);
        return result.get(0);
    }

    @Override
    public List<E> getLongestPath() {
        Tree<E> deepestTree = getDeepestLeftmostNode();
        return findPath(deepestTree);
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<Tree<E>> leafs = getTreesDFS(this)
                .stream()
                .filter( (tree) -> tree.children.size() == 0)
                .collect(Collectors.toList());

        List<List<E>> result = new ArrayList<>();

        for (Tree<E> currentLeaf : leafs) {
            List<E> currentLeafPath = findPath(currentLeaf);

            int currentSum = currentLeafPath
                    .stream()
                    .map( (E) -> (Integer) E)
                    .mapToInt( (element) -> element.intValue())
                    .sum();

            if (currentSum == sum) {
                result.add(currentLeafPath);
            }

        }

        return result;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        List<Tree<E>> subtreeRoots = new ArrayList<>();
        getSubtreeRootWithSum(this, sum, subtreeRoots);
        return subtreeRoots;

    }

    private int getSubtreeRootWithSum(Tree<E> current, int targetSum, List<Tree<E>> result) {
        int currentSum = (int) current.key;

        for (Tree<E> child : current.children) {
            currentSum += getSubtreeRootWithSum(child, targetSum, result);
        }

        if (currentSum == targetSum) {
            result.add(current);
        }
        return currentSum;
    }

    private void dfsReccursion(Tree<E> tree, StringBuilder sb, int counter) {
        if (tree == null) {
            return;
        }

        sb.append("  ".repeat(Math.max(0, counter)));
        sb.append(tree.key);
        sb.append(System.lineSeparator());
        counter++;

        for (Tree<E> current : tree.children) {
            dfsReccursion(current, sb, counter);
        }

    }

    private void getLeafsReccursion(Tree<E> tree, List<E> result) {
        if (tree.children.size() == 0) {
            result.add(tree.key);
            return;
        }

        for (Tree<E> currentTree : tree.children) {
            getLeafsReccursion(currentTree, result);
        }
    }

    private List<Tree<E>> getTreesDFS(Tree<E> root) {
        List<Tree<E>> result = new ArrayList<>();
        result.add(this);

        Deque<Tree<E>> deque = new ArrayDeque<>();
        deque.offer(this);

        while (deque.size() != 0) {
            Tree<E> topTree = deque.poll();

            for (Tree<E> currentTree : topTree.children) {
                deque.offer(currentTree);
                result.add(currentTree);
            }

        }

        return result;
    }

    private void findDeepestNodeDFS(List<Tree<E>> result, int[] maxPath, int max, Tree<E> tree) {
        if (max > maxPath[0]) {
            maxPath[0] = max;
            result.set(0, tree);
        }

        for (Tree<E> current : tree.children) {
            findDeepestNodeDFS(result, maxPath, max + 1, current);
        }

    }

    private List<E> findPath(Tree<E> deepestTree) {
        Deque<E> result = new ArrayDeque<>();

        while (deepestTree != null) {
            result.push(deepestTree.key);
            deepestTree = deepestTree.parent;
        }

        return new ArrayList<>(result);
    }
}



