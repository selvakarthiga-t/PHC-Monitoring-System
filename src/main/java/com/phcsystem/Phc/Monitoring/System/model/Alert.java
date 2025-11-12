package com.phcsystem.Phc.Monitoring.System.model;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Alert extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phc_id")
    private Phc phc;

    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "resolved_flag")
    private Boolean resolved = false;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;
}

