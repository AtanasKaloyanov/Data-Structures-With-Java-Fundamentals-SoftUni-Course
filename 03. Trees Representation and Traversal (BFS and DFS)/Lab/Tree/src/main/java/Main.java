import implementations.Tree;

public class Main {
    public static void main(String[] args) {
//              Tree<Integer> myTree = new Tree<>(
//                      7,
//                      new Tree<>(19,
//                            new Tree<>(1),
//                            new Tree<>(12),
//                            new Tree<>(31)
//                      ),
//                      new Tree<>(21),
//                      new Tree<>(4,
//                              new Tree<>(31),
//                              new Tree<>(6)
//                      )
//              );
//
//              System.out.println(myTree.orderBfs());
//              System.out.println(myTree.orderDfs());

        int n = 5;
        int result = recursion(n);
        System.out.println(result);

    }

    private static int recursion(int n) {
        if (n == 1) {
            return 1;
        }
        n *=  recursion(n - 1);
        return n;
    }
}

//                                                     7

//                                        19          21           4

//                                   1    12   31               31     6
