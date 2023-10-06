package barbershopjava;

import java.util.*;
import java.util.stream.Collectors;

public class BarberShopImpl implements BarberShop {
    private Map<String, Barber> barbersByName;
    private Map<String, Client> clientsByName;

    // Map key barbersNname and value List<Client>
    private Map<String, List<Client>> clientsByBarbesrId;

    private Map<String, Client> clientWithNoBarber;

    public BarberShopImpl() {
        this.barbersByName = new HashMap<>();
        this.clientsByName = new HashMap<>();
        this.clientsByBarbesrId = new HashMap<>();
        this.clientWithNoBarber = new HashMap<>();
    }

    @Override
    public void addBarber(Barber b) {
        // exception by condition
        if (exist(b)) {
            throw new IllegalArgumentException();
        }

        // adding a new barber to the map barbersByName
        this.barbersByName.put(b.name, b);

        // adding a barbersName as key to the map clientsByBarberId and initialising the List<Client>
        this.clientsByBarbesrId.put(b.name, new ArrayList<>());
    }

    @Override
    public void addClient(Client c) {
        if (exist(c)) {
            throw new IllegalArgumentException();
        }

        // adding the clint to the map clientsByName
        this.clientsByName.put(c.name, c);

        // adding the client to the map clientsWithNoBarber
        this.clientWithNoBarber.put(c.name, c);
    }

    // return if the barber exist
    @Override
    public boolean exist(Barber b) {
        return this.barbersByName.containsKey(b.name);
    }

    // returns if the client exist
    @Override
    public boolean exist(Client c) {
        return this.clientsByName.containsKey(c.name);
    }

    // return a collection of barbers
    @Override
    public Collection<Barber> getBarbers() {
        return this.barbersByName.values();
    }

    // return a collection of clients
    @Override
    public Collection<Client> getClients() {
        return this.clientsByName.values();
    }

    @Override
    public void assignClient(Barber b, Client c) {
        // exception by condition
        if (!exist(b) || !exist(c)) {
            throw new IllegalArgumentException();
        }

        // adding a client to the barber
        this.clientsByBarbesrId.get(b.name).add(c);
        // ! the client now has a new barber, because of the adding
        c.barber = b;

        // removing the client from the map clientsWithNoBarber, because now he has a barber
        this.clientWithNoBarber.remove(c.name);
    }

    @Override
    public void deleteAllClientsFrom(Barber b) {
        if (!exist(b)) {
            throw new IllegalArgumentException();
        }

        // List<Client> for removing from the map clientsByBarbersId using barbersName
        List<Client> clientsForRemoving = this.clientsByBarbesrId.get(b.name);

        // removing the clients from the map clientsByName user the upper List<Client>
        clientsForRemoving.forEach((client -> {
            this.clientsByName.remove(client.name);
        }));

        // removing the clients from the map clientsByBarbersId
        clientsForRemoving.clear();
    }

    // returns the clients from the map clientWithNoBarber
    @Override
    public Collection<Client> getClientsWithNoBarber() {
        return this.clientWithNoBarber.values();
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithClientsCountDesc() {
// all barbers sorted by the count of their clients (they are in the map clientsByBarbersId)
        return this.barbersByName.values()
                .stream()
                .sorted((barber1, barber2) -> {
                    int first = this.clientsByBarbesrId.get(barber1.name).size();
                    int second = this.clientsByBarbesrId.get(barber2.name).size();

                    return Integer.compare(second, first);
                })
                .collect(Collectors.toList());

//                return this.barbersByName.values()
//                .stream()
//                .sorted(Comparator.comparing( (Barber b) -> this.clientsByBarbesrId.get(b.name).size()).reversed())
//                .collect(Collectors.toList());

    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
        // all barbers sorted by:
        // 1. their stars descending
        // 2. their haircut price ascending
        return this.barbersByName.values()
                .stream()
                .sorted((b1, b2) -> {
                    int result = Integer.compare(b2.stars, b1.stars);

                    if (result == 0) {
                        result = Integer.compare(b1.haircutPrice, b2.haircutPrice);
                    }

                    return result;
                })
                .collect(Collectors.toList());

//        return this.barbersByName.values()
//                .stream()
//                .sorted(Comparator.comparing(Barber::getStars).reversed()
//                        .thenComparing(Barber::getHaircutPrice))
//                .collect(Collectors.toList());
    }

    @Override
    public Collection<Client> getClientsSortedByAgeDescAndBarbersStarsDesc() {
        // clients that are assigned (clientsByBarbersId map) sorted by:
        //         1. their age in descending order
        //         2. their barber's stars in descending order

        return this.clientsByName.values()
                .stream()
                .filter( (client) -> client.barber != null)
                .sorted((c1, c2) -> {
                    int result = Integer.compare(c2.age, c1.age);

                    if (result == 0) {
                        result = Integer.compare(c2.barber.stars, c1.barber.stars);
                    }

                    return result;
                })
                .collect(Collectors.toList());

//        return this.clientsByBarbesrId
//                .values()
//                .stream()
//                .flatMap(List::stream)
//                .sorted((client1, client2) -> {
//                    int result = Integer.compare(client2.age, client1.age);
//
//                    if (result == 0) {
//                        result = Integer.compare(client2.barber.stars, client1.barber.stars);
//                    }
//
//                    return result;
//                })
//                .collect(Collectors.toList());

    }
}
