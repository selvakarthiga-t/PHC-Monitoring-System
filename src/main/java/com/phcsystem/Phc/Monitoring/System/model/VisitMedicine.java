package com.phcsystem.Phc.Monitoring.System.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "visit_medicine")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VisitMedicine extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    @JsonBackReference("visit-medicines")
    private Visit visit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    private Integer quantity;


}
