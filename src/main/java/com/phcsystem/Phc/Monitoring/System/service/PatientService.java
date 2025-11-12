package com.phcsystem.Phc.Monitoring.System.service;

import com.phcsystem.Phc.Monitoring.System.model.Patient;
import com.phcsystem.Phc.Monitoring.System.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepo;

    public Patient registerPatient(Patient patient) {
        return patientRepo.save(patient);
    }

    public Patient getPatientById(Long id) {
        return patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient existing = patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        if (updatedPatient.getName() != null) existing.setName(updatedPatient.getName());
        if (updatedPatient.getAge() != null) existing.setAge(updatedPatient.getAge());
        if (updatedPatient.getGender() != null) existing.setGender(updatedPatient.getGender());
        if (updatedPatient.getPhone() != null) existing.setPhone(updatedPatient.getPhone());
        if (updatedPatient.getAadhaarMasked() != null) existing.setAadhaarMasked(updatedPatient.getAadhaarMasked());

        return patientRepo.save(existing);
    }

    public void deletePatient(Long id) {
        if (!patientRepo.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepo.deleteById(id);
    }
}
