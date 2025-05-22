package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.service.TechnicianService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technicians")
public class TechnicianController {
    private final TechnicianService technicianService;

    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @GetMapping
    public ResponseEntity<List<Technician>> getAllTechnicians() {
        return ResponseEntity.ok(technicianService.getAllTechnicians());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Technician>> getAllTechniciansPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(technicianService.getAllTechnicians(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Technician> getTechnicianById(@PathVariable Long id) {
        return ResponseEntity.ok(technicianService.getTechnicianById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Technician>> searchTechnicians(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(technicianService.searchTechnicians(name, department, pageable));
    }

    @PostMapping
    public ResponseEntity<Technician> createTechnician(@RequestBody Technician technician) {
        return ResponseEntity.ok(technicianService.createTechnician(technician));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Technician> updateTechnician(
            @PathVariable Long id,
            @RequestBody Technician technician) {
        return ResponseEntity.ok(technicianService.updateTechnician(id, technician));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnician(@PathVariable Long id) {
        technicianService.deleteTechnician(id);
        return ResponseEntity.ok().build();
    }
}