package com.phcsystem.Phc.Monitoring.System.service;

import com.phcsystem.Phc.Monitoring.System.model.*;
import com.phcsystem.Phc.Monitoring.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PhcRepo phcRepo;

    @Autowired
    private UserRepo userRepo;

    /**
     * ✅ Check-in doctor
     */
    public Attendance checkIn(Long doctorId, Long phcId, AttendanceMode mode, double lat, double lng) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + doctorId));
        Phc phc = phcRepo.findById(phcId)
                .orElseThrow(() -> new RuntimeException("PHC not found with id " + phcId));

        // prevent double check-in
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        boolean alreadyCheckedIn = attendanceRepo.existsByDoctorIdAndCheckInTimeBetween(doctorId, start, end);
        if (alreadyCheckedIn) {
            throw new RuntimeException("Doctor already checked in today!");
        }

        Attendance attendance = Attendance.builder()
                .doctor(doctor)
                .phc(phc)
                .checkInTime(LocalDateTime.now())
                .mode(mode)
                .locationLat(lat)
                .locationLng(lng)
                .build();

        return attendanceRepo.save(attendance);
    }

    /**
     * ✅ Check-out doctor
     */
    public Attendance checkOut(Long doctorId) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        Attendance attendance = attendanceRepo
                .findTopByDoctorIdAndCheckInTimeBetweenOrderByCheckInTimeDesc(doctorId, start, end)
                .orElseThrow(() -> new RuntimeException("No check-in record found for today"));

        if (attendance.getCheckOutTime() != null) {
            throw new RuntimeException("Doctor already checked out!");
        }

        attendance.setCheckOutTime(LocalDateTime.now());
        return attendanceRepo.save(attendance);
    }

    /**
     * ✅ Get a doctor's attendance for a given date
     */
    public List<Attendance> getAttendance(Long doctorId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return attendanceRepo.findByDoctorIdAndCheckInTimeBetween(doctorId, start, end);
    }

    /**
     * ✅ Get attendance of all doctors in a PHC within a date range
     */
    public List<Attendance> getAttendanceByPhc(Long phcId, LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);
        return attendanceRepo.findByPhcIdAndCheckInTimeBetween(phcId, start, end);
    }

    /**
     * ✅ Correct attendance (Manager/Admin)
     */
    public Attendance correctAttendance(Long id, Attendance corrected, Long correctedByUserId) {
        Attendance existing = attendanceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id " + id));

        User correctedBy = userRepo.findById(correctedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + correctedByUserId));

        if (corrected.getCheckInTime() != null)
            existing.setCheckInTime(corrected.getCheckInTime());

        if (corrected.getCheckOutTime() != null)
            existing.setCheckOutTime(corrected.getCheckOutTime());

        existing.setCorrectedBy(correctedBy);
        return attendanceRepo.save(existing);
    }
}
