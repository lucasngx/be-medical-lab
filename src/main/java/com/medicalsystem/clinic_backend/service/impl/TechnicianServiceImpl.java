package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.repository.TechnicianRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnicianServiceImpl implements TechnicianService {
    private final TechnicianRepository technicianRepository;

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
    public List<Technician> getTechniciansBySpecialization(String specialization) {
        return technicianRepository.findBySpecialization(specialization);
    }

    @Override
    public PaginatedResponse<Technician> searchTechniciansByName(String name) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Technician> page = technicianRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name, pageable);
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public PaginatedResponse<Technician> searchTechniciansBySpecialization(String specialization) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Technician> page = technicianRepository.findBySpecializationContainingIgnoreCase(specialization, pageable);
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public Technician getById(Long id) {
        return technicianRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Technician not found with id: " + id));
    }
} 