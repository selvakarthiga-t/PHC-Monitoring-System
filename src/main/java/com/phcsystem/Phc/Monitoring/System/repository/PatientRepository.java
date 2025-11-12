package com.phcsystem.Phc.Monitoring.System.repository;

import com.phcsystem.Phc.Monitoring.System.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
