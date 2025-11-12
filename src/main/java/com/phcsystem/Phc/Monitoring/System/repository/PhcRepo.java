package com.phcsystem.Phc.Monitoring.System.repository;

import com.phcsystem.Phc.Monitoring.System.model.Phc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface PhcRepo extends JpaRepository<Phc,Long> {
}
