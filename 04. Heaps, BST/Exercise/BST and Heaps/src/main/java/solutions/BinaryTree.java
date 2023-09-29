package solutions;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    private int value;
    private BinaryTree left;
    private BinaryTree right;

    public BinaryTree(int key, BinaryTree first, BinaryTree second) {
        this.value = key;
        this.left = first;
        this.right = second;
    }

    public Integer findLowestCommonAncestor(int first, int second) {
        List<Integer> firstpath = findPath(this, first);
        List<Integer> secondPath = findPath(this, second);

        int smallerSize = Math.min(firstpath.size(), secondPath.size());

        int i = 0;
        for (; i < smallerSize; i++) {
            if (!firstpath.get(i).equals(secondPath.get(i))) {
                break;
            }
        }

        return firstpath.get(i - 1);
    }

    private List<Integer> findPath(BinaryTree binaryTree, int element) {
        List<Integer> result = new ArrayList<>();
        findNodePath(this, element, result);
        return result;
    }

    private boolean findNodePath(BinaryTree binaryTree, int element, List<Integer> currentPath) {
        if (binaryTree == null) {
            return false;
        }

        if (binaryTree.value == element) {
            return true;
        }

        currentPath.add(binaryTree.value);

        boolean leftResult = findNodePath(binaryTree.left, element, currentPath);

        if (leftResult) {
            return true;
        }

        boolean rightResult = findNodePath(binaryTree.right, element, currentPath);

        if (rightResult) {
            return true;
        }

        currentPath.remove(Integer.valueOf(binaryTree.value));
        return false;
    }

    public List<Integer> topView() {
        return null;
    }

}
