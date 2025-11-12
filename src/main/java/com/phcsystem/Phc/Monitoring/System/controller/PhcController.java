package com.phcsystem.Phc.Monitoring.System.controller;


import com.phcsystem.Phc.Monitoring.System.model.Phc;
import com.phcsystem.Phc.Monitoring.System.service.PhcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phc")
public class PhcController {
    @Autowired
    public PhcService phcService;

    @PostMapping("/addphc")
    public ResponseEntity<Phc> createPhc(@RequestBody Phc phc) {
        Phc saved = phcService.createPhc(phc);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phc> getPhcById(@PathVariable Long id) {
        Phc phc = phcService.getPhcById(id);
        return ResponseEntity.ok(phc);
    }

    @GetMapping("/getallphc")
    public List<Phc> getAllPhc(){
        return phcService.getAllPhc();
    }

    @PutMapping("updatephc/{id}")
    public ResponseEntity<Phc> updatePhc(@PathVariable Long id, @RequestBody Phc phc){
        Phc saved = phcService.updatePhc(id,phc);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("deletephc/{id}")
    public void deletePhc(@PathVariable Long id){
        phcService.deletePhc(id);
    }
}
