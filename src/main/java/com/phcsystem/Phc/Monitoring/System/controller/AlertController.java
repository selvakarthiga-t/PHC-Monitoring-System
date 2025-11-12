package com.phcsystem.Phc.Monitoring.System.controller;

import com.phcsystem.Phc.Monitoring.System.model.Alert;
import com.phcsystem.Phc.Monitoring.System.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @PostMapping("/add")
    public ResponseEntity<Alert> createAlert(@RequestBody Alert alert) {
        return ResponseEntity.ok(alertService.createAlert(alert));
    }

    @GetMapping("/phc/{phcId}")
    public ResponseEntity<List<Alert>> getAlertsByPhc(
            @PathVariable Long phcId,
            @RequestParam(defaultValue = "false") boolean resolved) {
        return ResponseEntity.ok(alertService.getAlertsByPhc(phcId, resolved));
    }

    @PutMapping("/resolve/{id}")
    public ResponseEntity<Alert> resolveAlert(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.resolveAlert(id));
    }
}

