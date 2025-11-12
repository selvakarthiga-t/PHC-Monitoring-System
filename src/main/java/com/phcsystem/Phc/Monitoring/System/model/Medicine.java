package com.phcsystem.Phc.Monitoring.System.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "medicine")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Medicine extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private String batchNo;

    private LocalDate expiryDate;

    private Integer totalQuantity = 0;

    private Integer minThreshold = 10;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phc_id")
    @JsonBackReference
    private Phc phc;

    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<VisitMedicine> consumptionHistory = new ArrayList<>();
}
