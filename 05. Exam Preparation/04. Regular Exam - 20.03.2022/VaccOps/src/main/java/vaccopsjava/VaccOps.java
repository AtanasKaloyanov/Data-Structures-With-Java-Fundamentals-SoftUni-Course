package vaccopsjava;

import com.sun.source.tree.LiteralTree;

import java.util.*;
import java.util.stream.Collectors;

public class VaccOps implements IVaccOps {
    private Map<String, Doctor> doctorsByName;

    private Map<String, Patient> patientsByName;

    // key - doctorsName, value - List<Patient>
    private Map<String, List<Patient>> patientsByDoctorsName;


    public VaccOps() {
        this.doctorsByName = new LinkedHashMap<>();
        this.patientsByName = new LinkedHashMap<>();
        this.patientsByDoctorsName = new LinkedHashMap<>();
    }

    public void addDoctor(Doctor d) {
        if (exist(d)) {
            throw new IllegalArgumentException();
        }

        this.doctorsByName.put(d.name, d);
        this.patientsByDoctorsName.put(d.name, new ArrayList<>());
    }

    public void addPatient(Doctor d, Patient p) {
        if (!exist(d)) {
            throw new IllegalArgumentException();
        }

        this.patientsByName.put(p.name, p);
        this.patientsByDoctorsName.get(d.name).add(p);

    }

    public Collection<Doctor> getDoctors() {
        return this.doctorsByName.values();
    }

    public Collection<Patient> getPatients() {
        return this.patientsByName.values();
    }

    public boolean exist(Doctor d) {
        return this.doctorsByName.containsKey(d.name);
    }

    public boolean exist(Patient p) {
        return this.patientsByName.containsKey(p.name);
    }


    public Doctor removeDoctor(String name) {
        if (!this.doctorsByName.containsKey(name)) {
            throw new IllegalArgumentException();
        }

        Doctor doctorForRemoving = this.doctorsByName.get(name);
        List<Patient> patientsForRemoving = this.patientsByDoctorsName.get(name);

        patientsForRemoving.forEach((patient) -> {
            this.patientsByName.remove(patient.name);
        });

        patientsForRemoving.clear();

        return doctorForRemoving;
    }

    public void changeDoctor(Doctor from, Doctor to, Patient p) {
        if (!exist(from) || !exist(to) || !exist(p)) {
            throw new IllegalArgumentException();
        }

        if (patientsByDoctorsName.containsKey(p.name)) {
            this.patientsByDoctorsName.get(from.name).remove(p);
            this.patientsByDoctorsName.get(to.name).add(p);
        }
    }

    public Collection<Doctor> getDoctorsByPopularity(int populariry) {
        return this.doctorsByName.values()
                .stream()
                .filter((doctor -> doctor.popularity == populariry))
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsByTown(String town) {
        return this.patientsByName.values()
                .stream()
                .filter((patient) -> patient.town.equals(town))
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsInAgeRange(int lo, int hi) {
        return this.patientsByName.values()
                .stream()
                .filter((patient) -> patient.age >= lo && patient.age <= hi)
                .collect(Collectors.toList());
    }

    public Collection<Doctor> getDoctorsSortedByPatientsCountDescAndNameAsc() {
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

        // doctorMap
        // patientMap
        // patientsByDoctorNameMap

        return null;
    }
}
