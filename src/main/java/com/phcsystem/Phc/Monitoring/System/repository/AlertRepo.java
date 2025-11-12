package com.phcsystem.Phc.Monitoring.System.repository;

import com.phcsystem.Phc.Monitoring.System.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepo extends JpaRepository<Alert, Long> {
    List<Alert> findByPhcIdAndResolved(Long phcId, boolean resolved);
}
