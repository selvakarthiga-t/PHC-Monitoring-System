package com.phcsystem.Phc.Monitoring.System.service;

import com.phcsystem.Phc.Monitoring.System.model.*;
import com.phcsystem.Phc.Monitoring.System.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitService {

    @Autowired
    private VisitRepo visitRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PhcRepo phcRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private MedicineRepo medicineRepo;

    @Autowired
    private VisitMedicineRepo visitMedicineRepo;

    @Transactional
    public Visit createVisit(Visit visit) {
        // 1️⃣ Validate and fetch doctor, patient, phc
        Doctor doctor = doctorRepo.findById(visit.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + visit.getDoctor().getId()));

        Patient patient = patientRepo.findById(visit.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + visit.getPatient().getId()));

        Phc phc = phcRepo.findById(visit.getPhc().getId())
                .orElseThrow(() -> new RuntimeException("PHC not found with id " + visit.getPhc().getId()));

        // 2️⃣ Set relationships and visit time
        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit.setPhc(phc);
        visit.setVisitTime(LocalDateTime.now());

        // 3️⃣ Link prescriptions properly before saving
        if (visit.getPrescriptions() != null && !visit.getPrescriptions().isEmpty()) {
            for (VisitMedicine vm : visit.getPrescriptions()) {
                Medicine med = medicineRepo.findById(vm.getMedicine().getId())
                        .orElseThrow(() -> new RuntimeException("Medicine not found with id " + vm.getMedicine().getId()));

                // Validate and deduct stock
                if (med.getTotalQuantity() < vm.getQuantity()) {
                    throw new RuntimeException("Not enough stock for medicine: " + med.getName());
                }

                med.setTotalQuantity(med.getTotalQuantity() - vm.getQuantity());
                medicineRepo.save(med);

                // 🔥 Link both sides correctly before save
                vm.setVisit(visit); // <-- The missing key line
                vm.setMedicine(med);
            }
        }

        // 4️⃣ Save visit (cascade saves all VisitMedicines)
        return visitRepo.save(visit);
    }
    /**
     * ✅ Get all visits for a PHC within date range
     */
    public List<Visit> getVisitsByPhc(Long phcId, LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);
        return visitRepo.findByPhcIdAndVisitTimeBetween(phcId, start, end);
    }

    /**
     * ✅ Get all visits for a specific doctor
     */
    public List<Visit> getVisitsByDoctor(Long doctorId) {
        return visitRepo.findByDoctorIdOrderByVisitTimeDesc(doctorId);
    }

    /**
     * ✅ Get visit details by ID
     */
    public Visit getVisitById(Long id) {
        return visitRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Visit not found with id " + id));
    }
}
