package tripadministratorjava;

import java.util.*;
import java.util.stream.Collectors;

public class TripAdministratorImpl implements TripAdministrator {
    private Map<String, Company> companiesByNames;
    private Map<String, Trip> tripsByIds;
    private Map<String, List<Trip>> companiesWithTrips;

    public TripAdministratorImpl() {
        this.companiesByNames = new LinkedHashMap<>();
        this.tripsByIds = new LinkedHashMap<>();
        this.companiesWithTrips = new LinkedHashMap<>();
    }

    @Override
    public void addCompany(Company c) {
        if (companiesByNames.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }

        this.companiesByNames.put(c.name, c);
        this.companiesWithTrips.put(c.name, new ArrayList<>());
    }

    @Override
    public void addTrip(Company c, Trip t) {
        if (!this.companiesByNames.containsKey(c.name) || exist(t)) {
            throw new IllegalArgumentException();
        }

        this.tripsByIds.put(t.id, t);

        if (c.tripOrganizationLimit == companiesWithTrips.get(c.name).size()) {
            throw new IllegalArgumentException();
        }

        this.companiesWithTrips.get(c.name).add(t);
    }

    @Override
    public boolean exist(Company c) {
        return this.companiesByNames.containsKey(c.name);
    }

    @Override
    public boolean exist(Trip t) {
        return this.tripsByIds.containsKey(t.id);
    }

    @Override
    public void removeCompany(Company c) {
        if (!companiesByNames.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }

        this.companiesByNames.remove(c.name);
        List<Trip> trips = companiesWithTrips.remove(c.name);
        trips.forEach(trip -> tripsByIds.remove(trip.id));
    }

    @Override
    public Collection<Company> getCompanies() {
        return this.companiesByNames.values();
    }

    @Override
    public Collection<Trip> getTrips() {
        return this.tripsByIds.values();
    }

    @Override
    public void executeTrip(Company c, Trip t) {
        if (!exist(c) || !exist(c)) {
            throw new IllegalArgumentException();
        }

        List<Trip> companyTrips = this.companiesWithTrips.get(c.name);

        boolean removed = companyTrips.removeIf(tr -> tr.id.equals(t.id));

        if (!removed) {
            throw new IllegalArgumentException();
        }

        this.tripsByIds.remove(t.id);
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {
        return getCompanies().stream()
                .filter(c -> companiesWithTrips.get(c.name).size() > n)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getTripsWithTransportationType(Transportation t) {
        return getTrips().stream()
                .filter(trip -> trip.transportation == t)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getAllTripsInPriceRange(int lo, int hi) {
        return getTrips().stream()
                .filter(trip -> trip.price >= lo && trip.price <= hi)
                .collect(Collectors.toList());
    }

}
