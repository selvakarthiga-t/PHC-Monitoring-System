package com.phcsystem.Phc.Monitoring.System.controller;

import com.phcsystem.Phc.Monitoring.System.model.Medicine;
import com.phcsystem.Phc.Monitoring.System.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping("/add/{phcId}")
    public ResponseEntity<Medicine> addMedicine(@RequestBody Medicine med, @PathVariable Long phcId) {
        return ResponseEntity.ok(medicineService.addMedicine(med, phcId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @RequestBody Medicine med) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, med));
    }

    @GetMapping("/phc/{phcId}")
    public ResponseEntity<List<Medicine>> getMedicinesByPhc(@PathVariable Long phcId) {
        return ResponseEntity.ok(medicineService.getMedicinesByPhc(phcId));
    }

    @PutMapping("/consume/{medId}/{qty}/{visitId}")
    public ResponseEntity<Medicine> consumeStock(@PathVariable Long medId, @PathVariable int qty, @PathVariable Long visitId) {
        return ResponseEntity.ok(medicineService.consumeStock(medId, qty, visitId));
    }

    @GetMapping("/low-stock/{phcId}")
    public ResponseEntity<List<Medicine>> getLowStockMedicines(@PathVariable Long phcId) {
        return ResponseEntity.ok(medicineService.getLowStockMedicines(phcId));
    }
}
