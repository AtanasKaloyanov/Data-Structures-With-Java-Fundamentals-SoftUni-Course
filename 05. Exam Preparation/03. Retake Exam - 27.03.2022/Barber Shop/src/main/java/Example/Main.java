package Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<String, Trader> tradersByName = new HashMap<>();

        Trader firstTrader = new Trader("Pesho");
        Trader secondTrader = new Trader("Gosho");
        tradersByName.put("Pesho", firstTrader);
        tradersByName.put("Gosho", secondTrader);

        Map<String, ClienT> clientsByName = new HashMap<>();
        ClienT firstClient = new ClienT("Sasho");
        ClienT secondClient = new ClienT("Tosho");
        ClienT thirdClient = new ClienT("Valcho");

        clientsByName.put("Sasho", firstClient);
        clientsByName.put("Tosho", secondClient);
        clientsByName.put("Valcho", thirdClient);

        Map<Trader, List<ClienT>> traderByClients = new HashMap<>();
        traderByClients.put(firstTrader, new ArrayList<>());
        traderByClients.get(firstTrader).add(firstClient);
        traderByClients.get(firstTrader).add(secondClient);

        traderByClients.put(secondTrader, new ArrayList<>());
        traderByClients.get(secondTrader).add(thirdClient);
        traderByClients.get(secondTrader).add(firstClient);
        traderByClients.get(secondTrader).add(secondClient);

        traderByClients.entrySet()
                .stream()
                .sorted( (entry1, entry2) -> {
                    int secondSize = entry2.getValue().size();
                    int firstSize = entry1.getValue().size();

                   return Integer.compare(firstSize, secondSize);
                })
                .forEach((entry) -> {
                    System.out.println(entry.getKey().getName());
                    List<String> clients = entry.getValue()
                            .stream().map(ClienT::getName)
                            .collect(Collectors.toList());

                    System.out.println(clients.toString().replaceAll("[\\[\\],]", ""));
                });


    }
}
