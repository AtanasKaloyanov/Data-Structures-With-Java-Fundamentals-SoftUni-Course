package barbershopjava;

import java.util.*;
import java.util.stream.Collectors;

public class BarberShopImpl implements BarberShop {
    private Map<String, Barber> barbersByName;
    private Map<String, Client> clientsByName;

    // Map key String name and List<Client>
    private Map<String, List<Client>> barberClients;

    public BarberShopImpl() {
        this.barbersByName = new HashMap<>();
        this.clientsByName = new HashMap<>();
        this.barberClients = new HashMap<>();
    }

    @Override
    public void addBarber(Barber b) {
        if (this.barbersByName.containsKey(b.name)) {
            throw new IllegalArgumentException();
        }

        this.barbersByName.put(b.name, b);
        this.barberClients.put(b.name, new ArrayList<>());
    }

    @Override
    public void addClient(Client c) {
        if (this.clientsByName.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }

        this.clientsByName.put(c.name, c);
    }

    @Override
    public boolean exist(Barber b) {
        return this.barbersByName.containsKey(b.name);
    }

    @Override
    public boolean exist(Client c) {
        return this.clientsByName.containsKey(c.name);
    }

    @Override
    public Collection<Barber> getBarbers() {
        return this.barbersByName.values();
    }

    @Override
    public Collection<Client> getClients() {
        return this.clientsByName.values();
    }

    @Override
    public void assignClient(Barber b, Client c) {
        if (!exist(b) || !exist(c)) {
            throw new IllegalArgumentException();
        }
        
        this.barberClients.get(b.name).add(c);
        c.barber = b;
    }

    @Override
    public void deleteAllClientsFrom(Barber b) {
        if (!this.barbersByName.containsKey(b.name)) {
            throw new IllegalArgumentException();
        }

        this.barberClients.get(b.name).clear();
    }

    @Override
    public Collection<Client> getClientsWithNoBarber() {
        return this.clientsByName
                .values()
                .stream()
                .filter( (client) -> client.barber == null)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithClientsCountDesc() {
        // Use List, instead Map.
        // The List will return all barbers (including the barbers without clients).
        // The Map will rerurn only the barbers, that have clients

        // Wrong solution
//        return this.barberClients
//                .entrySet()
//                .stream()
//                .sorted( (entry1, entry2) -> {
//                    int firsBarberClientSize = entry1.getValue().size();
//                    int secondBarberClientsSize = entry2.getValue().size();
//
//                    return Integer.compare(secondBarberClientsSize, firsBarberClientSize);
//                }).map( (entry) -> entry.getKey())
//                .collect(Collectors.toList());

        return this.barbersByName.values()
                .stream()
                .sorted( (barber1, barber2) -> {
                    int first = this.barberClients.get(barber1.name).size();
                    int second = this.barberClients.get(barber2.name).size();

                    return Integer.compare(second, first);
                })
                .collect(Collectors.toList());

    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
        return this.barbersByName.values()
                .stream()
                .sorted( (b1, b2) -> {
                    int result = Integer.compare(b2.stars, b1.stars);

                    if (result == 0) {
                        result = Integer.compare(b1.haircutPrice, b2.haircutPrice);
                    }

                    return result;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Client> getClientsSortedByAgeDescAndBarbersStarsDesc() {
        // ! The return should be clients that are assign by barber => We should use the map barberClients
        // ! We use the function flatmap(List::stream) for mapping the stream of Lists to stream

        return this.barberClients
                .values()
                .stream()
                .flatMap(List::stream)
                .sorted( (client1, client2) -> {
                    int result = Integer.compare(client2.age, client1.age);

                    if (result == 0) {
                        result = Integer.compare(client2.barber.stars, client1.barber.stars);
                    }

                    return result;
                })
                .collect(Collectors.toList());
    }
}
