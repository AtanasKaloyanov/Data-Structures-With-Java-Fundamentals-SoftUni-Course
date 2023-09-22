import implementations.BinaryTree;
import interfaces.AbstractBinarySearchTree;
import interfaces.AbstractBinaryTree;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
       BinaryTree<Integer> bt = new BinaryTree<>(17,
               new BinaryTree<>(9,
                       new BinaryTree<>(3, null, null),
                       new BinaryTree<>(11, null, null)),
               new BinaryTree<>(25,
                       new BinaryTree<>(20, null, null),
                       new BinaryTree<>(31, null, null)));


    }
}

//                     17

//         9                        25
//
//    3         11             20         31


// 3, 11, 9, 20, 31, 25, 17