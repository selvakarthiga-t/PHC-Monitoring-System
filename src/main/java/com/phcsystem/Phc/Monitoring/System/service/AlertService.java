package com.phcsystem.Phc.Monitoring.System.service;

import com.phcsystem.Phc.Monitoring.System.model.*;
import com.phcsystem.Phc.Monitoring.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepo alertRepo;

    @Autowired
    private PhcRepo phcRepo;

    /**
     * ✅ Create a new alert for a PHC
     */
    public Alert createAlert(Alert alert) {
        if (alert.getPhc() == null || alert.getPhc().getId() == null) {
            throw new RuntimeException("PHC must be specified for creating an alert.");
        }

        Phc phc = phcRepo.findById(alert.getPhc().getId())
                .orElseThrow(() -> new RuntimeException("PHC not found with id " + alert.getPhc().getId()));

        alert.setPhc(phc);
        alert.setResolved(false);
        alert.setResolvedAt(null);

        return alertRepo.save(alert);
    }

    /**
     * ✅ Get all alerts for a PHC (filter by resolved flag)
     */
    public List<Alert> getAlertsByPhc(Long phcId, boolean resolved) {
        return alertRepo.findByPhcIdAndResolved(phcId, resolved);
    }

    /**
     * ✅ Resolve an alert (mark as resolved with timestamp)
     */
    public Alert resolveAlert(Long id) {
        Alert alert = alertRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found with id " + id));

        if (alert.getResolved()) {
            throw new RuntimeException("Alert is already resolved.");
        }

        alert.setResolved(true);
        alert.setResolvedAt(LocalDateTime.now());

        return alertRepo.save(alert);
    }
}
