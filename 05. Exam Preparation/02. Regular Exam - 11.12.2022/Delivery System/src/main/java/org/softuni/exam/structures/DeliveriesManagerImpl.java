package org.softuni.exam.structures;

import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Package;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveriesManagerImpl implements DeliveriesManager {

    private Map<String, Deliverer> deliversById;

    private Map<String, Package> packagesById;

    // The key is the deliversId the value is the List of packages
    private Map<String, List<Package>> packagesByDeliverersId;

    // The key is deliversId, the value is Package
    private Map<String, Package> unassaignedPackages;


    // The LinkedHashMap saves the objects by the order of their input
    public DeliveriesManagerImpl() {
        this.deliversById = new LinkedHashMap<>();
        this.packagesById = new LinkedHashMap<>();
        this.packagesByDeliverersId = new LinkedHashMap<>();
        this.unassaignedPackages = new LinkedHashMap<>();

    }

    @Override
    public void addDeliverer(Deliverer deliverer) {
        this.deliversById.put(deliverer.getId(), deliverer);
        this.packagesByDeliverersId.put(deliverer.getId(), new ArrayList<>());
    }

    @Override
    public void addPackage(Package _package) {
        this.packagesById.put(_package.getId(), _package);
        this.unassaignedPackages.put(_package.getId(), _package);
    }

    @Override
    public boolean contains(Deliverer deliverer) {
        return this.deliversById.containsKey(deliverer.getId());
    }

    @Override
    public boolean contains(Package _package) {
        return this.packagesById.containsKey(_package.getId());
    }

    @Override
    public Iterable<Deliverer> getDeliverers() {
        return this.deliversById.values();
    }

    @Override
    public Iterable<Package> getPackages() {
        return this.packagesById.values();
    }

    @Override
    public void assignPackage(Deliverer deliverer, Package _package) throws IllegalArgumentException {
        if (!contains(deliverer) || !contains(_package)) {
            throw new IllegalArgumentException();
        }

        // adding a new package to the  map packagesByDeliversId (by its deliversId).
        this.packagesByDeliverersId.get(deliverer.getId()).add(_package);
        // removing the package from the map unassaignedPackes (because now the packe is assaigned)
        this.unassaignedPackages.remove(_package.getId());
    }

    @Override
    public Iterable<Package> getUnassignedPackages() {
        return this.unassaignedPackages.values();
    }

    @Override
    public Iterable<Package> getPackagesOrderedByWeightThenByReceiver() {
        // 1. comparing the packages first by their wight in descending order
        // 2. then by their receiver in ascending order

        return this.packagesById.values()
                .stream()
                .sorted((p1, p2) -> {
                    int result = Double.compare(p2.getWeight(), p1.getWeight());

                    if (result == 0) {
                        result = p1.getReceiver().compareTo(p2.getReceiver());
                    }

                    return result;
                })
                .collect(Collectors.toList());

        /*
        return this.packagesById.values()
                .stream()
                .sorted(Comparator.comparing(Package::getWeight).reversed()
                        .thenComparing(Package::getReceiver))
                .collect(Collectors.toList());

         */
    }

    @Override
    public Iterable<Deliverer> getDeliverersOrderedByCountOfPackagesThenByName() {
        // 1. comparing the delivers by the number of their packages in descending order (they are in another map - packagesByDeliversId)
        // 2. then by their name

        return this.deliversById.values()
                .stream()
                .sorted( (d1, d2) -> {
                    int firstPackagesCount = this.packagesByDeliverersId.get(d1.getId()).size();
                    int secondPackagesCount = this.packagesByDeliverersId.get(d2.getId()).size();

                    int result = Integer.compare(secondPackagesCount, firstPackagesCount);

                    if (result == 0) {
                        result = d1.getName().compareTo(d2.getName());
                    }

                    return result;
                })
                .collect(Collectors.toList());
        /*
        return this.deliversById.values()
                .stream()
                .sorted(Comparator.comparing( (Deliverer d) -> this.packagesByDeliverersId.get(d.getId()).size()).reversed()
                        .thenComparing(Deliverer::getName))
                .collect(Collectors.toList());
         */
    }
}
