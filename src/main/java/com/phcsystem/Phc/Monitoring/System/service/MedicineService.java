package com.phcsystem.Phc.Monitoring.System.service;

import com.phcsystem.Phc.Monitoring.System.model.*;
import com.phcsystem.Phc.Monitoring.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepo medicineRepo;

    @Autowired
    private PhcRepo phcRepo;

    @Autowired
    private VisitRepo visitRepo;

    @Autowired
    private VisitMedicineRepo visitMedicineRepo;

    /**
     * ✅ Add a new medicine and link it to a PHC
     */
    public Medicine addMedicine(Medicine med, Long phcId) {
        Phc phc = phcRepo.findById(phcId)
                .orElseThrow(() -> new RuntimeException("PHC not found with id " + phcId));

        med.setPhc(phc);
        return medicineRepo.save(med);
    }

    /**
     * ✅ Update medicine details
     */
    public Medicine updateMedicine(Long id, Medicine med) {
        Medicine existing = medicineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id " + id));

        if (med.getName() != null) existing.setName(med.getName());
        if (med.getBatchNo() != null) existing.setBatchNo(med.getBatchNo());
        if (med.getExpiryDate() != null) existing.setExpiryDate(med.getExpiryDate());
        if (med.getTotalQuantity() != null) existing.setTotalQuantity(med.getTotalQuantity());
        if (med.getMinThreshold() != null) existing.setMinThreshold(med.getMinThreshold());

        return medicineRepo.save(existing);
    }

    /**
     * ✅ Get all medicines of a PHC
     */
    public List<Medicine> getMedicinesByPhc(Long phcId) {
        return medicineRepo.findByPhcId(phcId);
    }

    /**
     * ✅ Consume stock when used in a visit
     */
    public Medicine consumeStock(Long medId, int qty, Long visitId) {
        Medicine med = medicineRepo.findById(medId)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id " + medId));

        if (med.getTotalQuantity() < qty) {
            throw new RuntimeException("Insufficient stock for medicine: " + med.getName());
        }

        med.setTotalQuantity(med.getTotalQuantity() - qty);

        Visit visit = visitRepo.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found with id " + visitId));

        // create record in VisitMedicine table (consumption history)
        VisitMedicine vm = VisitMedicine.builder()
                .visit(visit)
                .medicine(med)
                .quantity(qty)
                .build();

        visitMedicineRepo.save(vm);
        return medicineRepo.save(med);
    }

    /**
     * ✅ Get low stock medicines for a PHC
     */
    public List<Medicine> getLowStockMedicines(Long phcId) {
        return medicineRepo.findByPhcIdAndTotalQuantityLessThanEqualMinThreshold(phcId);
    }
}
