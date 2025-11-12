package com.phcsystem.Phc.Monitoring.System.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance", indexes = {
        @Index(name = "idx_attendance_doctor_date", columnList = "doctor_id, check_in_time")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Attendance extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phc_id")
    private Phc phc;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Enumerated(EnumType.STRING)
    private AttendanceMode mode;

    private Double locationLat;
    private Double locationLng;

    // who corrected this record (admin/manager)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corrected_by")
    private User correctedBy;
}

