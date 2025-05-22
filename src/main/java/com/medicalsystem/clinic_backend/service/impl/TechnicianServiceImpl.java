package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.repository.TechnicianRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnicianServiceImpl implements TechnicianService {
    private final TechnicianRepository technicianRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PaginatedResponse<Technician> getAllTechnicians(Pageable pageable) {
        Page<Technician> page = technicianRepository.findAll(pageable);
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public Technician getTechnicianById(Long id) {
        return technicianRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Technician not found with id: " + id));
    }

    @Override
    public Technician getTechnicianByEmail(String email) {
        return technicianRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Technician not found with email: " + email));
    }

    @Override
    @Transactional
    public Technician createTechnician(Technician technician) {
        technician.setPassword(passwordEncoder.encode(technician.getPassword()));
        technician.setCreatedAt(new Date());
        technician.setUpdatedAt(new Date());
        return technicianRepository.save(technician);
    }

    @Override
    @Transactional
    public Technician updateTechnician(Long id, Technician technicianDetails) {
        Technician technician = getTechnicianById(id);

        technician.setFirstName(technicianDetails.getFirstName());
        technician.setLastName(technicianDetails.getLastName());
        technician.setEmail(technicianDetails.getEmail());
        technician.setPhone(technicianDetails.getPhone());
        technician.setSpecialization(technicianDetails.getSpecialization());
        technician.setStatus(technicianDetails.getStatus());
        technician.setUpdatedAt(new Date());

        return technicianRepository.save(technician);
    }

    @Override
    @Transactional
    public void deleteTechnician(Long id) {
        Technician technician = getTechnicianById(id);
        technicianRepository.delete(technician);
    }

    @Override
    public PaginatedResponse<Technician> searchTechnicians(String firstName, String lastName, Pageable pageable) {
        Page<Technician> page;
        if ((firstName != null && !firstName.isEmpty()) || (lastName != null && !lastName.isEmpty())) {
            page = technicianRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                firstName != null ? firstName : "",
                lastName != null ? lastName : "",
                pageable
            );
        } else {
            page = technicianRepository.findAll(pageable);
        }
        
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public List<Technician> searchTechniciansByName(String name) {
        return technicianRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

    @Override
    public List<Technician> getTechniciansBySpecialization(String specialization) {
        return technicianRepository.findBySpecialization(specialization);
    }

    @Override
    public Technician getById(Long id) {
        return getTechnicianById(id);
    }
} 