package com.phcsystem.Phc.Monitoring.System.repository;

import com.phcsystem.Phc.Monitoring.System.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepo extends JpaRepository<Medicine, Long> {

    List<Medicine> findByPhcId(Long phcId);

    // Find low stock medicines
    @Query("SELECT m FROM Medicine m WHERE m.phc.id = :phcId AND m.totalQuantity <= m.minThreshold")
    List<Medicine> findByPhcIdAndTotalQuantityLessThanEqualMinThreshold(Long phcId);
}