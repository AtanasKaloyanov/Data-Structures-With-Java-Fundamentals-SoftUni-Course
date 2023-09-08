import implementations.ReversedList;

public class Main {
    public static void main(String[] args) {
        ReversedList<Integer> rs = new ReversedList<>();

        rs.add(1);
        rs.add(2);
        rs.add(3);
        rs.add(4);
        rs.add(5);
        rs.add(6);

        int capacity = rs.capacity();
        int size = rs.size();

          int e2 = rs.get(7);
          int e = rs.removeAt(2);

          System.out.println();

//
//        for (int currentElement: rs) {
//            System.out.println(currentElement);
//        }


        // indeces             0 1 2 3 4 5      index = 1,

        // old elements        1 2 3 4 5 6
        // new elements        1 3 4 5 6





    }
}
