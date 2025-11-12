package com.phcsystem.Phc.Monitoring.System.controller;

import com.phcsystem.Phc.Monitoring.System.model.Doctor;
import com.phcsystem.Phc.Monitoring.System.model.Phc;
import com.phcsystem.Phc.Monitoring.System.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/adddoctor")
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.addDoctor(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    @GetMapping("/getalldoctor")
    public List<Doctor> getAllDoctor(){
        return doctorService.getAllDoctor();
    }

    @GetMapping("/getdoctorsbyphc/{id}")
    public List<Doctor> getDoctorsByPhc(Long phcId){
        return doctorService.getDoctorsByPhc(phcId);
    }

    @GetMapping("/getdoctor/{id}")
    public ResponseEntity<Doctor> getDoctorById(Long id){
      Doctor doc = doctorService.getDoctorById(id);
      return ResponseEntity.ok(doc);
    }

    @PutMapping("deactivatedoctor/{id}")
    public void deactivateDoctor(@PathVariable Long id){
      doctorService.deactivateDoctor(id);
    }

    @PutMapping("/updatedoctor/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        Doctor updated = doctorService.updateDoctor(id, doctor);
        return ResponseEntity.ok(updated);
    }

}
