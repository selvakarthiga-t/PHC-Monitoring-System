package com.phcsystem.Phc.Monitoring.System.service;

import com.phcsystem.Phc.Monitoring.System.model.*;
import com.phcsystem.Phc.Monitoring.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitMedicineService {

    @Autowired
    private VisitMedicineRepo visitMedicineRepo;

    @Autowired
    private VisitRepo visitRepo;

    @Autowired
    private MedicineRepo medicineRepo;

    /**
     * ✅ Add medicine usage for a visit (records which medicine was prescribed)
     */
    public VisitMedicine addVisitMedicine(VisitMedicine vm) {
        Visit visit = visitRepo.findById(vm.getVisit().getId())
                .orElseThrow(() -> new RuntimeException("Visit not found with id " + vm.getVisit().getId()));

        Medicine medicine = medicineRepo.findById(vm.getMedicine().getId())
                .orElseThrow(() -> new RuntimeException("Medicine not found with id " + vm.getMedicine().getId()));

        // validate quantity
        if (vm.getQuantity() == null || vm.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        // deduct stock
        if (medicine.getTotalQuantity() < vm.getQuantity()) {
            throw new RuntimeException("Not enough stock for medicine: " + medicine.getName());
        }

        medicine.setTotalQuantity(medicine.getTotalQuantity() - vm.getQuantity());
        medicineRepo.save(medicine);

        vm.setVisit(visit);
        vm.setMedicine(medicine);
        return visitMedicineRepo.save(vm);
    }

    /**
     * ✅ Get all medicines used in a particular visit
     */
    public List<VisitMedicine> getByVisit(Long visitId) {
        return visitMedicineRepo.findByVisitId(visitId);
    }

    /**
     * ✅ Get all visits where a particular medicine was used
     */
    public List<VisitMedicine> getByMedicine(Long medicineId) {
        return visitMedicineRepo.findByMedicineId(medicineId);
    }

    /**
     * ✅ Get all Visit-Medicine records for a PHC
     */
    public List<VisitMedicine> getByPhc(Long phcId) {
        return visitMedicineRepo.findByPhcId(phcId);
    }

    /**
     * ✅ Delete a Visit-Medicine record (used for correction)
     */
    public void deleteVisitMedicine(Long id) {
        VisitMedicine vm = visitMedicineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("VisitMedicine not found with id " + id));

        // Optional: restore medicine stock if deleted
        Medicine medicine = vm.getMedicine();
        medicine.setTotalQuantity(medicine.getTotalQuantity() + vm.getQuantity());
        medicineRepo.save(medicine);

        visitMedicineRepo.delete(vm);
    }
}
