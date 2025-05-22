package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.repository.TechnicianRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TechnicianService {
    private final TechnicianRepository technicianRepository;
    private final PasswordEncoder passwordEncoder;

    public TechnicianService(TechnicianRepository technicianRepository, PasswordEncoder passwordEncoder) {
        this.technicianRepository = technicianRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PaginatedResponse<Technician> getPaginatedTechnicians(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Technician> technicianPage = technicianRepository.findAll(pageable);
        
        return new PaginatedResponse<>(
            technicianPage.getContent(),
            page,
            technicianPage.getTotalPages(),
            technicianPage.getTotalElements(),
            limit
        );
    }

    public Technician getTechnicianById(Long id) {
        return technicianRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Technician not found with id: " + id));
    }

    public Technician getTechnicianByEmail(String email) {
        return technicianRepository.findByEmail(email);
    }

    public List<Technician> searchTechnicians(String name, String department) {
        if (name != null && !name.isEmpty() && department != null && !department.isEmpty()) {
            return technicianRepository.findByNameContainingIgnoreCaseAndDepartmentContainingIgnoreCase(name, department);
        } else if (name != null && !name.isEmpty()) {
            return technicianRepository.findByNameContainingIgnoreCase(name);
        } else if (department != null && !department.isEmpty()) {
            return technicianRepository.findByDepartmentContainingIgnoreCase(department);
        }
        return technicianRepository.findAll();
    }

    public Technician createTechnician(Technician technician) {
        // Encrypt password before saving
        technician.setPassword(passwordEncoder.encode(technician.getPassword()));
        technician.setCreatedAt(new Date());
        technician.setUpdatedAt(new Date());
        return technicianRepository.save(technician);
    }

    public Technician updateTechnician(Long id, Technician technicianDetails) {
        Technician technician = getTechnicianById(id);
        
        technician.setFirstName(technicianDetails.getFirstName());
        technician.setLastName(technicianDetails.getLastName());
        technician.setEmail(technicianDetails.getEmail());
        technician.setPhone(technicianDetails.getPhone());
        technician.setStatus(technicianDetails.getStatus());
        technician.setUpdatedAt(new Date());

        // Only update password if it's provided
        if (technicianDetails.getPassword() != null && !technicianDetails.getPassword().isEmpty()) {
            technician.setPassword(passwordEncoder.encode(technicianDetails.getPassword()));
        }

        return technicianRepository.save(technician);
    }

    public void deleteTechnician(Long id) {
        Technician technician = getTechnicianById(id);
        technicianRepository.delete(technician);
    }

    public Technician getById(Long id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technician not found with ID: " + id));
    }
}