package tripadministratorjava;

import java.util.*;
import java.util.stream.Collectors;

public class TripAdministratorImpl implements TripAdministrator {
    private Map<String, Company> companiesByName;
    private Map<String, Trip> tripsById;
    private Map<String, List<Trip>> companyTrps;


    public TripAdministratorImpl() {
        this.companiesByName = new HashMap<>();
        this.tripsById = new HashMap<>();
        this.companyTrps = new HashMap<>();
    }

    // +
    @Override
    public void addCompany(Company c) {
        if (this.companiesByName.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }

        this.companiesByName.put(c.name, c);

        // adding the company in the map with the trips too
        this.companyTrps.put(c.name, new ArrayList<>());
    }

    @Override
    public void addTrip(Company c, Trip t) {
        // We should throw an exception when the trip exist in the map tripsById
        if (!exist(c) || exist(t)) {
            throw new IllegalArgumentException();
        }

        this.tripsById.put(t.id, t);

        // when the number of the trips is equal to the limit, we should throw
        // an exception, becaus of the full capacity
        if (c.tripOrganizationLimit == this.companyTrps.get(c.name).size()) {
            throw new IllegalArgumentException();
        }

        this.companyTrps.get(c.name).add(t);
    }

    @Override
    public boolean exist(Company c) {
        return this.companiesByName.containsKey(c.name);
    }

    @Override
    public boolean exist(Trip t) {
        return this.tripsById.containsKey(t.id);
    }

    @Override
    public void removeCompany(Company c) {
        if (!this.companiesByName.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }

        this.companiesByName.remove(c.name);
        // After the removing of the trips by the company name in the map companyTrips,
        // we should remove this trips from the map tripsById.
        // This can be done when we assign this trips by a variable (the remove method in the
        // map returns the )
        List<Trip> trips = this.companyTrps.remove(c.name);
        trips.forEach( (trip) -> this.tripsById.remove(trip.id));
    }

    @Override
    public Collection<Company> getCompanies() {
        return this.companiesByName.values();
    }

    @Override
    public Collection<Trip> getTrips() {
        return this.tripsById.values();
    }

    @Override
    public void executeTrip(Company c, Trip t) {
        if (!exist(t) || !(exist(c))) {
            throw new IllegalArgumentException();
        }

        if (!this.companyTrps.get(c.name).contains(t)) {
            throw new IllegalArgumentException();
        }

        this.companyTrps.get(c.name).remove(t);
        this.tripsById.remove(t.id);
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {
        return this.companiesByName.values()
                .stream()
                .filter((company) -> {
                    List<Trip> currentTrips = this.companyTrps.get(company.name);

                    return currentTrips.size() > n;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getTripsWithTransportationType(Transportation t) {
        return this.tripsById.values()
                .stream()
                .filter((trip) -> trip.transportation.equals(t))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getAllTripsInPriceRange(int lo, int hi) {
        return this.tripsById.values()
                .stream()
                .filter((trip) -> trip.price >= lo && trip.price <= hi)
                .collect(Collectors.toList());
    }
}
