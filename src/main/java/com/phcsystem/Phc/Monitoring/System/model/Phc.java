package com.phcsystem.Phc.Monitoring.System.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "phc")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Phc extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private String district;
    private String address;
    private String contactPhone;

    // ✅ Handles PHC → Doctor one-directional JSON
    @OneToMany(mappedBy = "phc", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Doctor> doctors = new ArrayList<>();

    @OneToMany(mappedBy = "phc", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Medicine> medicines = new ArrayList<>();
}
