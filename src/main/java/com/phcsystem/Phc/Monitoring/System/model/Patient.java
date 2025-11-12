package com.phcsystem.Phc.Monitoring.System.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "patient")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient extends BaseEntity {
    private String name;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    // store masked Aadhaar or hash if you plan to keep sensitive info
    @Column(name = "aadhaar_masked")
    private String aadhaarMasked;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private List<Visit> visits = new ArrayList<>();
}

