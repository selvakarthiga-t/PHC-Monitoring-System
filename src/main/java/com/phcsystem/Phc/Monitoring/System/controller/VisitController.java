package com.phcsystem.Phc.Monitoring.System.controller;

import com.phcsystem.Phc.Monitoring.System.model.Visit;
import com.phcsystem.Phc.Monitoring.System.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @PostMapping("/add")
    public ResponseEntity<Visit> createVisit(@RequestBody Visit visit) {
        return ResponseEntity.ok(visitService.createVisit(visit));
    }

    @GetMapping("/phc/{phcId}")
    public ResponseEntity<List<Visit>> getVisitsByPhc(
            @PathVariable Long phcId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(visitService.getVisitsByPhc(phcId, from, to));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Visit>> getVisitsByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(visitService.getVisitsByDoctor(doctorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.getVisitById(id));
    }
}
