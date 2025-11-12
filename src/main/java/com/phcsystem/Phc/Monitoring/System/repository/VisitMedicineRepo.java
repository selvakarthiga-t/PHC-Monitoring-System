package com.phcsystem.Phc.Monitoring.System.repository;

import com.phcsystem.Phc.Monitoring.System.model.VisitMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitMedicineRepo extends JpaRepository<VisitMedicine, Long> {

    List<VisitMedicine> findByVisitId(Long visitId);

    List<VisitMedicine> findByMedicineId(Long medicineId);

    // via visit → phc
    @Query("SELECT vm FROM VisitMedicine vm WHERE vm.visit.phc.id = :phcId")
    List<VisitMedicine> findByPhcId(Long phcId);
}
