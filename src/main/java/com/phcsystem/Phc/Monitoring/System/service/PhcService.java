package com.phcsystem.Phc.Monitoring.System.service;

import com.phcsystem.Phc.Monitoring.System.model.Doctor;
import com.phcsystem.Phc.Monitoring.System.model.Medicine;
import com.phcsystem.Phc.Monitoring.System.model.Phc;
import com.phcsystem.Phc.Monitoring.System.model.User;
import com.phcsystem.Phc.Monitoring.System.repository.PhcRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhcService {

    @Autowired
    public PhcRepo phcRepo;

    public Phc createPhc(Phc phc) {
        return phcRepo.save(phc);
    }

    public Phc getPhcById(Long id) {
        Phc phc = phcRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("PHC not found"));
        phc.getDoctors().size(); // force initialize
        phc.getMedicines().size(); // force initialize
        return phc;
    }

    public List<Phc> getAllPhc() {
        return phcRepo.findAll();
    }

    public Phc updatePhc(Long id, Phc phc) {
        Phc Exphc = phcRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Phc not found!"));
        if(phc.getName() != null){
            Exphc.setName(phc.getName());
        }
        if(phc.getAddress() != null){
            Exphc.setAddress(phc.getAddress());
        }

        if (phc.getDoctors() != null) {
            Exphc.getDoctors().clear();  // Remove existing doctors
            for (Doctor doctor : phc.getDoctors()) {
                doctor.setPhc(Exphc);    // Ensure bidirectional link is set
                Exphc.getDoctors().add(doctor);
            }
        }

        if (phc.getMedicines() != null) {
            Exphc.getMedicines().clear();
            for (Medicine medicine : phc.getMedicines()) {
                medicine.setPhc(Exphc);
                Exphc.getMedicines().add(medicine);
            }
        }

        if(phc.getContactPhone()!= null){
            Exphc.setContactPhone(phc.getContactPhone());
        }
        return phcRepo.save(Exphc);
    }

    public void deletePhc(Long id) {
        phcRepo.deleteById(id);
    }
}
