package com.phcsystem.Phc.Monitoring.System.controller;

import com.phcsystem.Phc.Monitoring.System.model.*;
import com.phcsystem.Phc.Monitoring.System.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/checkin/{doctorId}/{phcId}")
    public ResponseEntity<Attendance> checkIn(
            @PathVariable Long doctorId,
            @PathVariable Long phcId,
            @RequestParam AttendanceMode mode,
            @RequestParam double lat,
            @RequestParam double lng) {
        return ResponseEntity.ok(attendanceService.checkIn(doctorId, phcId, mode, lat, lng));
    }

    @PutMapping("/checkout/{doctorId}")
    public ResponseEntity<Attendance> checkOut(@PathVariable Long doctorId) {
        return ResponseEntity.ok(attendanceService.checkOut(doctorId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Attendance>> getAttendance(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendance(doctorId, date));
    }

    @GetMapping("/phc/{phcId}")
    public ResponseEntity<List<Attendance>> getAttendanceByPhc(
            @PathVariable Long phcId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(attendanceService.getAttendanceByPhc(phcId, from, to));
    }

    @PutMapping("/correct/{id}/{correctedByUserId}")
    public ResponseEntity<Attendance> correctAttendance(
            @PathVariable Long id,
            @PathVariable Long correctedByUserId,
            @RequestBody Attendance corrected) {
        return ResponseEntity.ok(attendanceService.correctAttendance(id, corrected, correctedByUserId));
    }
}
