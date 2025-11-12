package com.phcsystem.Phc.Monitoring.System.service;

import com.phcsystem.Phc.Monitoring.System.model.Doctor;
import com.phcsystem.Phc.Monitoring.System.model.Phc;
import com.phcsystem.Phc.Monitoring.System.model.User;
import com.phcsystem.Phc.Monitoring.System.repository.DoctorRepo;
import com.phcsystem.Phc.Monitoring.System.repository.PhcRepo;
import com.phcsystem.Phc.Monitoring.System.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PhcRepo phcRepo;

    @Autowired
    private UserRepo userRepo;

    public Doctor addDoctor(Doctor doctor) {
        // Just save the doctor — no PHC assignment here
        return doctorRepo.save(doctor);
    }

    public List<Doctor> getAllDoctor() {
        return doctorRepo.findAll();
    }

    public List<Doctor> getDoctorsByPhc(Long phcId) {
       Phc phc = phcRepo.findById(phcId)
               .orElseThrow(() -> new RuntimeException("Phc not found"));
       return phc.getDoctors();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public void deactivateDoctor(Long id) {
        Doctor doc = doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        if(doc.getActive() == true){
            doc.setActive(false);
        }else{
            throw new RuntimeException("Doctor already inactive");
        }
        doctorRepo.save(doc);
    }

    public Doctor updateDoctor(Long id, Doctor doctor) {
        Doctor existingDoctor = doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));


        if (doctor.getRegistrationNumber() != null) {
            existingDoctor.setRegistrationNumber(doctor.getRegistrationNumber());
        }

        if (doctor.getSpecialization() != null) {
            existingDoctor.setSpecialization(doctor.getSpecialization());
        }

        if (doctor.getActive() != null) {
            existingDoctor.setActive(doctor.getActive());
        }


        if (doctor.getUser() != null) {
            User newUser = doctor.getUser();
            User existingUser = existingDoctor.getUser();

            if (existingUser != null) {
                if (newUser.getName() != null) existingUser.setName(newUser.getName());
                if (newUser.getPasswordHash() != null) existingUser.setPasswordHash(newUser.getPasswordHash());
                if (newUser.getRole() != null) existingUser.setRole(newUser.getRole());
            } else {
                existingDoctor.setUser(newUser);
            }
        }


        if (doctor.getPhc() != null) {
            existingDoctor.setPhc(doctor.getPhc());
        }



        return doctorRepo.save(existingDoctor);
    }


}


