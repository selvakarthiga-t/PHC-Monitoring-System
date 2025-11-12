package com.phcsystem.Phc.Monitoring.System.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;

@Entity
@Table(name = "doctor")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Doctor extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column(name = "registration_number")
    private String registrationNumber;

    private String specialization;

    @Column(name = "active_flag")
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phc_id")
    @JsonBackReference
    private Phc phc;


    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Visit> visits = new ArrayList<>();
}

