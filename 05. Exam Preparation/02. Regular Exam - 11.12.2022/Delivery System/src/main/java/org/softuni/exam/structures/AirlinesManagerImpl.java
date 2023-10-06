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
        // 1. adding an airline to the airlinesByIdMap
        this.airlinesById.put(airline.getId(), airline);

        // 2. adding an airlineId to the map flightByAilinesId and ilitialise the value of this map
        this.flightsByAirlinesId.put(airline.getId(), new ArrayList<>());
    }

    @Override
    public void addFlight(Airline airline, Flight flight) {
        // exception by condition
        if (!contains(airline)) {
            throw new IllegalArgumentException();
        }

        // adding a flight to the map flightsById
        this.flightsById.put(flight.getId(), flight);

        // adding an flight to the map flightsByAirlinesId by airlinesId
        this.flightsByAirlinesId.get(airline.getId()).add(flight);

        // adding the flight to the map completedFlight if the given flight is completed
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
        // exception by condition
        if (!contains(airline)) {
            throw new IllegalArgumentException();
        }

        // removing the airline from the map airlinesById
        this.airlinesById.remove(airline.getId());

        // Deleting the flights, that are assigned with the given airlines (from the filghtsById map
        // and from the completedFlightsMap)

        List<Flight> flightsForRemoving = this.flightsByAirlinesId.get(airline.getId());

        flightsForRemoving.forEach((flight) -> {
            this.flightsById.remove(flight.getId());
            this.completedFlights.remove(flight.getId());
        });

        flightsForRemoving.clear();
        /*
        List<Flight> removedAirlinesFlight = flightsByAirlinesId.get(airline.getId());

        for (Flight flight : removedAirlinesFlight) {
            this.flightsById.remove(flight.getId());
            this.completedFlights.remove(flight.getId());
        }
         */
    }

    // returning the values of the map getAllFlights
    @Override
    public Iterable<Flight> getAllFlights() {
        return this.flightsById.values();
    }

    @Override
    public Flight performFlight(Airline airline, Flight flight) throws IllegalArgumentException {
        // exception by condition
        if (!contains(airline) || !contains(flight)) {
            throw new IllegalArgumentException();
        }

        this.flightsById.get(flight.getId()).setCompleted(true);

        // when we perform the given flight (by setting its boolen variable isCompleted to true) we
        // should add it to the map with the completed flights
        this.completedFlights.put(flight.getId(), flight);
        return flight;
    }

    // returning the values of tha map completedFlight
    @Override
    public Iterable<Flight> getCompletedFlights() {
        return this.completedFlights.values();
    }

    @Override
    public Iterable<Flight> getFlightsOrderedByNumberThenByCompletion() {
        //1.  comparing by completion (the flight, that are not completed
        // (their boolean isCompleted == false) should be first)
        // 2. Then by the number of their completion

        return this.flightsById.values()
                .stream()
                .sorted((f1, f2) -> {
                    int result = Boolean.compare(f1.isCompleted(), f2.isCompleted());

                    if (result == 0) {
                        result = f1.getNumber().compareTo(f2.getNumber());
                    }

                    return result;
                })
                .collect(Collectors.toList());

        //        return this.flightsById.values()
//                .stream()
//                .sorted(Comparator.comparing(Flight::isCompleted)
//                        .thenComparing(Flight::getNumber))
//                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesOrderedByRatingThenByCountOfFlightsThenByName() {
        // 1. comparing of airlines by their rating in descending order
        // 2. then by counts of flights by descending order
        // 3. then by their name is ascending order
        return this.airlinesById.values()
                .stream()
                .sorted( (a1, a2) -> {
                    int result = Double.compare(a2.getRating(), a1.getRating()); // by this solution the reversed comparing by rating is true

                    if (result == 0) {
                        int firstAirlineFlightsCount = this.flightsByAirlinesId.get(a1.getId()).size();
                        int secondAirlineFlightsCount = this.flightsByAirlinesId.get(a2.getId()).size();

                        result = Integer.compare(secondAirlineFlightsCount, firstAirlineFlightsCount);
                    }

                    if (result == 0) {
                        result = a1.getName().compareTo(a2.getName());
                    }

                    return result;
                })
                .collect(Collectors.toList());

//        return this.airlinesById.values()
//                .stream()
//                .sorted(Comparator.comparing(Airline::getRating) // .reversed() reversed is wrong
//                        .thenComparing((Airline a) -> this.flightsByAirlinesId.get(a.getId()).size()).reversed()
//                        .thenComparing(Airline::getName))
//                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesWithFlightsFromOriginToDestination(String origin, String destination) {
        //

        return this.airlinesById.values()
                .stream()
                .filter( (Airline a) -> this.flightsByAirlinesId.get(a.getId())
                        .stream()
                        .anyMatch( (flight) -> !flight.isCompleted() && flight.getOrigin().equals(origin) && flight.getDestination().equals(destination)))
                .collect(Collectors.toList());


    }
}
