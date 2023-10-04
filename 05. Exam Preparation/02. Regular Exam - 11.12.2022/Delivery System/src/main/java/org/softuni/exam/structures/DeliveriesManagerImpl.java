package org.softuni.exam.structures;

import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Package;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeliveriesManagerImpl implements DeliveriesManager {

    private Map<String, Deliverer> deliversById;

    private Map<String, Package> packagesById;

    // The String is the deliversId the int is the number of packages
    private Map<String, Integer> packagesByDeliverersId;

    // The key is deliversId, the value is Package
    private Map<String, Package> unassaignedPackage;


    // The LinkedHashMap saves the objects by the order of their input
    public DeliveriesManagerImpl() {
        this.deliversById = new LinkedHashMap<>();
        this.packagesById = new LinkedHashMap<>();
        this.packagesByDeliverersId = new LinkedHashMap<>();
        this.unassaignedPackage = new LinkedHashMap<>();

    }

    @Override
    public void addDeliverer(Deliverer deliverer) {
        this.deliversById.put(deliverer.getId(), deliverer);
        this.packagesByDeliverersId.put(deliverer.getId(), 0);
    }

    @Override
    public void addPackage(Package _package) {
       this.packagesById.put(_package.getId(), _package);
       this.unassaignedPackage.put(_package.getId(), _package);
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

       // The current count of the packages.
       int currentCount = this.packagesByDeliverersId.get(deliverer.getId());
       this.packagesByDeliverersId.put(deliverer.getId(), currentCount + 1);

       this.unassaignedPackage.remove(_package.getId());
    }

    @Override
    public Iterable<Package> getUnassignedPackages() {
        return this.unassaignedPackage.values();
    }

    @Override
    public Iterable<Package> getPackagesOrderedByWeightThenByReceiver() {
        return this.packagesById.values()
                .stream()
                //  comparing the packages first by their wight in descending order
                .sorted(Comparator.comparing(Package::getWeight).reversed()
                        // then by their receiver in ascending order
                        .thenComparing(Package::getReceiver))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Deliverer> getDeliverersOrderedByCountOfPackagesThenByName() {
        return this.deliversById.values()
                .stream()
                // comparing the delivers by the number of their packages in descending order (they are in another map - packagesByDeliversId)
                .sorted(Comparator.comparing( (Deliverer d) -> this.packagesByDeliverersId.get(d.getId())).reversed()
                        // then by their name
                        .thenComparing(Deliverer::getName))
                .collect(Collectors.toList());
    }
}
