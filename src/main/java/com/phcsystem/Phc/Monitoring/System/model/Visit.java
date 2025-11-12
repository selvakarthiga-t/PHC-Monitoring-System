package com.phcsystem.Phc.Monitoring.System.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "visit", indexes = {
        @Index(name = "idx_visit_phc_time", columnList = "phc_id, visit_time")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Visit extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phc_id")
    private Phc phc;

    @Column(name = "visit_time")
    private LocalDateTime visitTime;

    private String reason;
    private String diagnosisCode;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("visit-medicines")
    private List<VisitMedicine> prescriptions = new ArrayList<>();
}

