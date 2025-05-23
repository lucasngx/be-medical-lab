package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.model.User;
import com.medicalsystem.clinic_backend.repository.TechnicianRepository;
import com.medicalsystem.clinic_backend.repository.UserRepository;
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
    private final UserRepository userRepository;
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
        // Create user first
        User user = new User();
        user.setEmail(technician.getEmail());
        user.setPassword(passwordEncoder.encode(technician.getUser().getPassword()));
        user.setName(technician.getUser().getName());
        user.setRole(technician.getUser().getRole());
        user.setEnabled(true);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user = userRepository.save(user);

        // Set user to technician
        technician.setUser(user);
        technician.setCreatedAt(new Date());
        technician.setUpdatedAt(new Date());
        
        return technicianRepository.save(technician);
    }

    @Override
    @Transactional
    public Technician updateTechnician(Long id, Technician technicianDetails) {
        Technician technician = getTechnicianById(id);
        
        // Update user details
        User user = technician.getUser();
        user.setName(technicianDetails.getUser().getName());
        user.setEmail(technicianDetails.getEmail());
        user.setUpdatedAt(new Date());
        userRepository.save(user);

        // Update technician details
        technician.setDepartment(technicianDetails.getDepartment());
        technician.setPhone(technicianDetails.getPhone());
        technician.setEmail(technicianDetails.getEmail());
        technician.setUpdatedAt(new Date());
        
        return technicianRepository.save(technician);
    }

    @Override
    @Transactional
    public void deleteTechnician(Long id) {
        Technician technician = getTechnicianById(id);
        technicianRepository.delete(technician);
        userRepository.delete(technician.getUser());
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
        return technicianRepository.findByUser_NameContainingIgnoreCase(name);
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