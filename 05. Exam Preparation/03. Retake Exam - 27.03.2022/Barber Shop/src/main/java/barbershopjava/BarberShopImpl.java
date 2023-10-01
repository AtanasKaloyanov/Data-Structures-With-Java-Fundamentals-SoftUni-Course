package barbershopjava;

import java.util.*;
import java.util.stream.Collectors;

public class BarberShopImpl implements BarberShop {
    private Map<String, Barber> barbersByNames;
    private Map<String, Client> clientsByNames;
    private Map<String, List<Client>> barberWithClients;
//    private List<Barber> barbers;
//    private List<Client> clients;

    public BarberShopImpl() {
        this.barbersByNames = new HashMap<>();
        this.clientsByNames = new HashMap<>();
        this.barberWithClients = new HashMap<>();
//        this.barbers = new ArrayList<>();
//        this.clients = new ArrayList<>();
    }

    @Override
    public void addBarber(Barber b) {
        if (this.barbersByNames.containsKey(b.name)) {
            throw new IllegalArgumentException();
        }

        this.barbersByNames.put(b.name, b);
        this.barberWithClients.put(b.name, new ArrayList<>());
//        this.barbers.add(b);
    }

    @Override
    public void addClient(Client c) {
        if (this.clientsByNames.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }

        this.clientsByNames.put(c.name, c);
//        this.clients.add(c);
    }

    @Override
    public boolean exist(Barber b) {
        return this.barbersByNames.containsKey(b.name);
    }

    @Override
    public boolean exist(Client c) {
        return this.clientsByNames.containsKey(c.name);
    }

    @Override
    public Collection<Barber> getBarbers() {
        return this.barbersByNames.values();
    }

    @Override
    public Collection<Client> getClients() {
        return this.clientsByNames.values();
    }

    @Override
    public void assignClient(Barber b, Client c) {
        if (!exist(b) || (!exist(c))) {
            throw new IllegalArgumentException();
        }

        c.barber = b;
        this.barberWithClients.get(b.name).add(c);
    }

    @Override
    public void deleteAllClientsFrom(Barber b) {
        if (!exist(b)) {
            throw new IllegalArgumentException();
        }

        List<Client> clients = this.barberWithClients.get(b.name);
        clients.clear();
    }

    @Override
    public Collection<Client> getClientsWithNoBarber() {
        return getClients()
                .stream()
                .filter(c -> c.barber == null)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithClientsCountDesc() {
        return getBarbers().stream()
                .sorted((b1, b2) -> {

                    int firstClients = this.barberWithClients.get(b1.name).size();
                    int secondClients = this.barberWithClients.get(b2.name).size();

                    return Integer.compare(secondClients, firstClients);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
        return getBarbers()
                .stream()
                .sorted((first, second) -> {
                    int result = Integer.compare(second.stars, first.stars);

                    if (result == 0) {
                        result = Integer.compare(first.haircutPrice, second.haircutPrice);
                    }

                    return result;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Client> getClientsSortedByAgeDescAndBarbersStarsDesc() {
        return barberWithClients.values()
                .stream()
                .flatMap(List::stream)
                .sorted( (c1, c2) -> {
                    int result = Integer.compare(c2.age, c1.age);

                    if (result == 0) {
                        result = Integer.compare(c2.barber.stars, c1.barber.stars);
                    }

                    return result;
                }).collect(Collectors.toList());

    }
}
