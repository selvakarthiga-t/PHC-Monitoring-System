package com.phcsystem.Phc.Monitoring.System.repository;

import com.phcsystem.Phc.Monitoring.System.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

    boolean existsByDoctorIdAndCheckInTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    Optional<Attendance> findTopByDoctorIdAndCheckInTimeBetweenOrderByCheckInTimeDesc(Long doctorId, LocalDateTime start, LocalDateTime end);

    List<Attendance> findByDoctorIdAndCheckInTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    List<Attendance> findByPhcIdAndCheckInTimeBetween(Long phcId, LocalDateTime start, LocalDateTime end);
}
