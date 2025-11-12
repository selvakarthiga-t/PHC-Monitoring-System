package com.phcsystem.Phc.Monitoring.System.repository;

import com.phcsystem.Phc.Monitoring.System.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepo extends JpaRepository<Visit , Long> {
    List<Visit> findByPhcIdAndVisitTimeBetween(Long phcId, LocalDateTime from, LocalDateTime to);

    List<Visit> findByDoctorIdOrderByVisitTimeDesc(Long doctorId);
}
