package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/technicians")
public class TechnicianController {
    private final TechnicianService technicianService;

    @Autowired
    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<Technician>> getAllTechnicians(Pageable pageable) {
        return ResponseEntity.ok(technicianService.getAllTechnicians(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Technician> getTechnicianById(@PathVariable Long id) {
        return ResponseEntity.ok(technicianService.getTechnicianById(id));
    }

    @PostMapping
    public ResponseEntity<Technician> createTechnician(@RequestBody Technician technician) {
        return ResponseEntity.ok(technicianService.createTechnician(technician));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Technician> updateTechnician(@PathVariable Long id, @RequestBody Technician technician) {
        return ResponseEntity.ok(technicianService.updateTechnician(id, technician));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnician(@PathVariable Long id) {
        technicianService.deleteTechnician(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResponse<Technician>> searchTechnicians(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            Pageable pageable) {
        return ResponseEntity.ok(technicianService.searchTechnicians(name, department, pageable));
    }
}