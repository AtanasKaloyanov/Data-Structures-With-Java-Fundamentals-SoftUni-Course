package vaccopsjava;

import com.sun.source.tree.LiteralTree;

import java.util.*;
import java.util.stream.Collectors;

public class VaccOps implements IVaccOps {
    private Map<String, Doctor> doctorsByName;

    private Map<String, Patient> patientsByName;

    // key - doctorsName, value - List<Patient>
    private Map<String, List<Patient>> patientsByDoctorsName;

    // the key is the patient's name, the value us the Doctor. This map is for the final sorting
    private Map<String, Doctor> doctorsByPatientsName;

    public VaccOps() {
        this.doctorsByName = new LinkedHashMap<>();
        this.patientsByName = new LinkedHashMap<>();
        this.patientsByDoctorsName = new LinkedHashMap<>();
        this.doctorsByPatientsName = new LinkedHashMap<>();
    }

    public void addDoctor(Doctor d) {
        // exception by condition
        if (exist(d)) {
            throw new IllegalArgumentException();
        }

        // adding a doctorsName as Key to the map doctorsByName and a doctor as a value
        this.doctorsByName.put(d.name, d);
        // adding a doctorsName as key to the map patiensByDoctorsName and initialising the value
        this.patientsByDoctorsName.put(d.name, new ArrayList<>());
    }

    public void addPatient(Doctor d, Patient p) {
        // exception by condition, exist(t) is not included in the world file
        if (!exist(d) || exist(p)) {
            throw new IllegalArgumentException();
        }

        // adding a patientsName to the map patientsByName as a key and the patient as a value
        this.patientsByName.put(p.name, p);
        // adding the patient to the value of the map patientsByDoctorsName
        this.patientsByDoctorsName.get(d.name).add(p);

        // adding a patientsName as a key to the map doctorsByPatientsName and the given doctor as a value
        this.doctorsByPatientsName.put(p.name, d);
    }

    // returns the doctors
    public Collection<Doctor> getDoctors() {
        return this.doctorsByName.values();
    }

    // returns the patients
    public Collection<Patient> getPatients() {
        return this.patientsByName.values();
    }

    // show us if the given doctor is included in the map doctorsByName
    public boolean exist(Doctor d) {
        return this.doctorsByName.containsKey(d.name);
    }

    // show us if the given doctor is included in the map patientsByName
    public boolean exist(Patient p) {
        return this.patientsByName.containsKey(p.name);
    }

    // exception by condition
    public Doctor removeDoctor(String name) {
        if (!this.doctorsByName.containsKey(name)) {
            throw new IllegalArgumentException();
        }

        // getting and removing the given doctor
        Doctor doctorForRemoving = this.doctorsByName.get(name);
        this.doctorsByName.remove(doctorForRemoving.name);

        // getting the patients of the doctor from the map patientsByDoctorsName
        List<Patient> patientsForRemoving = this.patientsByDoctorsName.get(doctorForRemoving.name);

        patientsForRemoving.forEach( (patient) -> {
            // removing  List<Patient> patientsForRemoving from the map patientsByName and doctorsByPatientsName
            this.patientsByName.remove(patient.name);
            this.doctorsByPatientsName.remove(patient.name);
        });

        // removing the patienst from the map patientsByDoctorsName
        patientsForRemoving.clear();

        // returning the doctor for removing
        return doctorForRemoving;
    }

    public void changeDoctor(Doctor from, Doctor to, Patient p) {
        // exception by condition
        if (!exist(from) || !exist(to) || !exist(p)) {
            throw new IllegalArgumentException();
        }

        // removing and adding the patient by the names of the given doctors
        this.patientsByDoctorsName.get(from.name).remove(p);
        this.patientsByDoctorsName.get(to.name).add(p);

        // replacing the new doctor in the map doctorsByPatientsName
        this.doctorsByPatientsName.put(p.name, to);

    }

    // getting the doctors with the given popularity from the map doctorsByName
    public Collection<Doctor> getDoctorsByPopularity(int populariry) {
        return this.doctorsByName.values()
                .stream()
                .filter((doctor -> doctor.popularity == populariry))
                .collect(Collectors.toList());
    }

    // getting the patienst from the given town from the map patientsByName
    public Collection<Patient> getPatientsByTown(String town) {
        return this.patientsByName.values()
                .stream()
                .filter((patient) -> patient.town.equals(town))
                .collect(Collectors.toList());
    }

    // getting the patients whic age is in range incluse - from the map patientsByName
    public Collection<Patient> getPatientsInAgeRange(int lo, int hi) {
        return this.patientsByName.values()
                .stream()
                .filter((patient) -> patient.age >= lo && patient.age <= hi)
                .collect(Collectors.toList());
    }

    public Collection<Doctor> getDoctorsSortedByPatientsCountDescAndNameAsc() {
        // return the doctors (from the map doctorsByName), sorted by
        //     1. the count of their patients (this is in the map patientsByDoctorsName) in descending order
        //     2. then by their name in ascending order
        return this.doctorsByName.values()
                .stream()
                .sorted((doctor1, doctor2) -> {
                    int firstDoctorPatientsCount = this.patientsByDoctorsName.get(doctor1.name).size();
                    int secondDoctorPatientsCount = this.patientsByDoctorsName.get(doctor2.name).size();

                    int result = Integer.compare(secondDoctorPatientsCount, firstDoctorPatientsCount);

                    if (result == 0) {
                        result = doctor1.name.compareTo(doctor2.name);
                    }

                    return result;
                })
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {
        // returns the pationts (from the map patientsByName) sorted by
        //      1. the popularity of ther doctors  (the doctor of the current patient in the map doctorsByPatientsName) in ascending order
        //      2. then by their height in descending order
        //      3. then by their age in ascending order
        return this.patientsByName.values()
                .stream()
                .sorted((patient1, patient2) -> {
                    int firstDoctorPopularity = this.doctorsByPatientsName.get(patient1.name).popularity;
                    int secondDoctorPopularity = this.doctorsByPatientsName.get(patient2.name).popularity;

                    int result = Integer.compare(firstDoctorPopularity, secondDoctorPopularity);

                    if (result == 0) {
                        result = Integer.compare(patient2.height, patient1.height);
                    }

                    if (result == 0) {
                        result = Integer.compare(patient1.age, patient2.age);
                    }

                    return result;
                })
                .collect(Collectors.toList());

    }
}
