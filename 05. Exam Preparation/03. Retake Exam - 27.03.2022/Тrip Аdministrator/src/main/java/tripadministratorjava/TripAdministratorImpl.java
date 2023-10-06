package tripadministratorjava;

import java.util.*;
import java.util.stream.Collectors;

public class TripAdministratorImpl implements TripAdministrator {
    private Map<String, Company> companiesByName;
    private Map<String, Trip> tripsById;

    // key - companyName, value List<Trip>
    private Map<String, List<Trip>> tripsByCompanyName;

    public TripAdministratorImpl() {
        this.companiesByName = new LinkedHashMap<>();
        this.tripsById = new LinkedHashMap<>();
        this.tripsByCompanyName = new LinkedHashMap<>();
    }

    @Override
    public void addCompany(Company c) {
        // exception by condition
        if (exist(c)) {
            throw new IllegalArgumentException();
        }

        // adding the company in the map companiesByName
        this.companiesByName.put(c.name, c);

        // adding the name of the company to the map tripsByCompanyName
        // and initialisation of the List<Trip>
        this.tripsByCompanyName.put(c.name, new ArrayList<>());
    }

    @Override
    public void addTrip(Company c, Trip t) {
        // exception by condition
        // ?exist(t) contition is not in the document
        if (!exist(c) || exist(t)) {
            throw new IllegalArgumentException();
        }

        this.tripsById.put(t.id, t);

        // only if the limit is tripOrganizationLimit is equal to the number of the trips (got by company name)
        /// in the map tripsByCompanyName, we add the trip to the map tripsByCompanyName, because only in this case
        // we have space

        if (c.tripOrganizationLimit > this.tripsByCompanyName.get(c.name).size()) {
            this.tripsByCompanyName.get(c.name).add(t);
        }

    }

    // return if the company exists
    @Override
    public boolean exist(Company c) {
        return this.companiesByName.containsKey(c.name);
    }

    // returns if the trip exists
    @Override
    public boolean exist(Trip t) {
        return this.tripsById.containsKey(t.id);
    }

    @Override
    public void removeCompany(Company c) {
        if (!exist(c)) {
            throw new IllegalArgumentException();
        }

        // first removing the company from the map companiesByName
        this.companiesByName.remove(c.name);

        // getting the trips from the map tripsByCompanyName using the name of the company
        List<Trip> tripsForRemoving = this.tripsByCompanyName.get(c.name);
        // removing the trips from the map tripsById
        tripsForRemoving.forEach((trip) -> this.tripsById.remove(trip.id));

        // finally removing the trips from the map tripsForRemoving
        tripsForRemoving.clear();
    }

    // returns all companies
    @Override
    public Collection<Company> getCompanies() {
        return this.companiesByName.values();
    }

    // returns all trips
    @Override
    public Collection<Trip> getTrips() {
        return this.tripsById.values();
    }


    @Override
    public void executeTrip(Company c, Trip t) {
        // exception by condition (this time everything is perfect)
        if (!exist(t) || !(exist(c))) {
            throw new IllegalArgumentException();
        }

        // exception by condition
        if (!this.tripsByCompanyName.get(c.name).contains(t)) {
            throw new IllegalArgumentException();
        }
// removing the prit first from the map tripsByCompanyName
        this.tripsByCompanyName.get(c.name).remove(t);
        // then from the map tripsById
        this.tripsById.remove(t.id);
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {
        // returns the companies (from the map companiesByName, that have more than n trips (the trips are in map thripsByCompanyName)
        return this.companiesByName.values()
                .stream()
                .filter( (company) -> {
                    List<Trip> currentTrips = this.tripsByCompanyName.get(company.name);

                    return currentTrips.size() > n;
                })
                .collect(Collectors.toList());
    }

    // returns only the trips (from the map tripsById) with the given transportation
    @Override
    public Collection<Trip> getTripsWithTransportationType(Transportation t) {
        return this.tripsById.values()
                .stream()
                .filter((trip) -> trip.transportation.equals(t))
                .collect(Collectors.toList());
    }

    // returns the trips (from the map tripsById), that price is in range (greater or equal to lo and smaller or equal to hi)
    @Override
    public Collection<Trip> getAllTripsInPriceRange(int lo, int hi) {
        return this.tripsById.values()
                .stream()
                .filter((trip) -> trip.price >= lo && trip.price <= hi)
                .collect(Collectors.toList());
    }
}
