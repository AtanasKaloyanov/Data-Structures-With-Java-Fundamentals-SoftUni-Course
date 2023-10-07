package vaccopsjava;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> myMap = new LinkedHashMap<>();
        myMap.put("a", 1);
        myMap.put("a", 2);
        myMap.put("a", 3);

        myMap.entrySet().stream().forEach( (element) ->
                System.out.println(element.getKey() + " " + element.getValue()));
    }
}
