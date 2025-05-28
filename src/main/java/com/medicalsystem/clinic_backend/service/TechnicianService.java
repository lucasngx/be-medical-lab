package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.repository.TechnicianRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface TechnicianService {
    PaginatedResponse<Technician> getAllTechnicians(Pageable pageable);
    Technician getTechnicianById(Long id);
    Technician getTechnicianByEmail(String email);
    Technician createTechnician(Technician technician);
    Technician updateTechnician(Long id, Technician technicianDetails);
    void deleteTechnician(Long id);
    PaginatedResponse<Technician> searchTechnicians(String name, String department, Pageable pageable);
    List<Technician> searchTechniciansByName(String name);
    List<Technician> getTechniciansByDepartment(String department);
}