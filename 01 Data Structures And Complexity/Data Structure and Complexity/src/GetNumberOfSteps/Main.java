package GetNumberOfSteps;

public class Main {
    public static void main(String[] args) {
        ForLoopSteps forLoopSteps = new ForLoopSteps();
       System.out.println(forLoopSteps.getOperations(5)); // 25
       System.out.println(forLoopSteps.getOperations(10)); // 100

        // T(n) = 3(n ^ 2) + 3n + 3
    }
}
