package com.phcsystem.Phc.Monitoring.System.controller;

import com.phcsystem.Phc.Monitoring.System.model.VisitMedicine;
import com.phcsystem.Phc.Monitoring.System.service.VisitMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visit-medicine")
public class VisitMedicineController {

    @Autowired
    private VisitMedicineService visitMedicineService;

    @PostMapping("/add")
    public ResponseEntity<VisitMedicine> addVisitMedicine(@RequestBody VisitMedicine vm) {
        return ResponseEntity.ok(visitMedicineService.addVisitMedicine(vm));
    }

    @GetMapping("/visit/{visitId}")
    public ResponseEntity<List<VisitMedicine>> getByVisit(@PathVariable Long visitId) {
        return ResponseEntity.ok(visitMedicineService.getByVisit(visitId));
    }

    @GetMapping("/medicine/{medicineId}")
    public ResponseEntity<List<VisitMedicine>> getByMedicine(@PathVariable Long medicineId) {
        return ResponseEntity.ok(visitMedicineService.getByMedicine(medicineId));
    }

    @GetMapping("/phc/{phcId}")
    public ResponseEntity<List<VisitMedicine>> getByPhc(@PathVariable Long phcId) {
        return ResponseEntity.ok(visitMedicineService.getByPhc(phcId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVisitMedicine(@PathVariable Long id) {
        visitMedicineService.deleteVisitMedicine(id);
        return ResponseEntity.noContent().build();
    }
}
