package T01DataStructuresAndComplexity.GetNumberOfSteps;

public class ForLoopSteps {
     long getOperations(int n) {
        long counter = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                counter++;
            }
        }

        return counter;
    }

}
