package org.softuni.exam.structures;

import org.softuni.exam.entities.Airline;
import org.softuni.exam.entities.Flight;

import java.util.*;
import java.util.stream.Collectors;

public class AirlinesManagerImpl implements AirlinesManager {
    private Map<String, Airline> airlinesById;

    private Map<String, Flight> flightsById;

    // the is a airlineId, the value is a List<Flight>, thet the airline has
    private Map<String, List<Flight>> flightsByAirlinesId;

    // the key is a flightId, the value is a completed Flight (boolean isCompleted == true)
    private Map<String, Flight> completedFlights;

    public AirlinesManagerImpl() {
        this.airlinesById = new LinkedHashMap<>();
        this.flightsById = new LinkedHashMap<>();
        this.flightsByAirlinesId = new LinkedHashMap<>();
        this.completedFlights = new LinkedHashMap<>();
    }

    @Override
    public void addAirline(Airline airline) {
        this.airlinesById.put(airline.getId(), airline);
        this.flightsByAirlinesId.put(airline.getId(), new ArrayList<>());
    }

    @Override
    public void addFlight(Airline airline, Flight flight) {
        if (!contains(airline)) {
            throw new IllegalArgumentException();
        }

        this.flightsById.put(flight.getId(), flight);
        this.flightsByAirlinesId.get(airline.getId()).add(flight);

        if (flight.isCompleted()) {
            this.completedFlights.put(flight.getId(), flight);
        }
    }

    @Override
    public boolean contains(Airline airline) {
        return this.airlinesById.containsKey(airline.getId());
    }

    @Override
    public boolean contains(Flight flight) {
        return this.flightsById.containsKey(flight.getId());
    }

    @Override
    public void deleteAirline(Airline airline) throws IllegalArgumentException {
        if (!contains(airline)) {
            throw new IllegalArgumentException();
        }

        this.airlinesById.remove(airline.getId());

        // Deleting the flights, that are assigned with the given airlines (from the filghtsById map
        // and from the completedFlightsMap)
        List<Flight> removedAirlinesFlight = flightsByAirlinesId.get(airline.getId());

        for (Flight flight : removedAirlinesFlight) {
            this.flightsById.remove(flight.getId());
            this.completedFlights.remove(flight.getId());
        }
    }

    @Override
    public Iterable<Flight> getAllFlights() {
        return this.flightsById.values();
    }

    @Override
    public Flight performFlight(Airline airline, Flight flight) throws IllegalArgumentException {
        if (!contains(airline) || !contains(flight)) {
            throw new IllegalArgumentException();
        }

        this.flightsById.get(flight.getId()).setCompleted(true);

        // when we perform the given flight (by setting its boolen variable isCompleted to true) we
        // should add it to the map with the completed flights
        this.completedFlights.put(flight.getId(), flight);
        return flight;
    }

    @Override
    public Iterable<Flight> getCompletedFlights() {

        //  This solution (with filter function) is with O(n) complexity. Therefore
//         return this.flightsById.values()
//                .stream()
//                .filter(Flight::isCompleted)
//                .collect(Collectors.toList());

        return this.completedFlights.values();
    }

    @Override
    public Iterable<Flight> getFlightsOrderedByNumberThenByCompletion() {
        return this.flightsById.values()
                .stream()
                // comparing by completion (the flight, that are not completed
                // (their boolean isCompleted == false should be first
                // ))
                .sorted(Comparator.comparing(Flight::isCompleted)
                        .thenComparing(Flight::getNumber))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesOrderedByRatingThenByCountOfFlightsThenByName() {
        return this.airlinesById.values()
                .stream()
                .sorted(Comparator.comparing(Airline::getRating) // .reversed() reversed is wrong
                        .thenComparing((Airline a) -> this.flightsByAirlinesId.get(a.getId()).size()).reversed()
                        .thenComparing(Airline::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesWithFlightsFromOriginToDestination(String origin, String destination) {
        return this.airlinesById.values()
                .stream()
                .filter(a -> this.flightsByAirlinesId.get(a.getId())
                        .stream()
                        .anyMatch(f -> !f.isCompleted() && f.getDestination().equals(destination) && f.getOrigin().equals(origin))
                )
                .collect(Collectors.toList());

    }
}
