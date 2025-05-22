package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.repository.DoctorRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public PaginatedResponse<Doctor> getPaginatedDoctors(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Doctor> doctorPage = doctorRepository.findAll(pageable);
        
        return new PaginatedResponse<>(
            doctorPage.getContent(),
            page,
            doctorPage.getTotalPages(),
            doctorPage.getTotalElements(),
            limit
        );
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Doctor not found with email: " + email));
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    public Doctor createDoctor(Doctor doctor) {
        // Encrypt password before saving
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.setCreatedAt(new Date());
        doctor.setUpdatedAt(new Date());
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor doctor = getDoctorById(id);
        
        doctor.setFirstName(doctorDetails.getFirstName());
        doctor.setLastName(doctorDetails.getLastName());
        doctor.setEmail(doctorDetails.getEmail());
        doctor.setPhone(doctorDetails.getPhone());
        doctor.setSpecialization(doctorDetails.getSpecialization());
        doctor.setRole(doctorDetails.getRole());
        doctor.setUpdatedAt(new Date());

        // Only update password if it's provided
        if (doctorDetails.getPassword() != null && !doctorDetails.getPassword().isEmpty()) {
            doctor.setPassword(passwordEncoder.encode(doctorDetails.getPassword()));
        }

        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        Doctor doctor = getDoctorById(id);
        doctorRepository.delete(doctor);
    }

    public PaginatedResponse<Doctor> searchDoctors(String name, String specialization, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Doctor> doctorPage;

        if (name != null && !name.isEmpty() && specialization != null && !specialization.isEmpty()) {
            doctorPage = doctorRepository.findByNameContainingIgnoreCaseAndSpecializationContainingIgnoreCase(name, specialization, pageable);
        } else if (name != null && !name.isEmpty()) {
            doctorPage = doctorRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (specialization != null && !specialization.isEmpty()) {
            doctorPage = doctorRepository.findBySpecializationContainingIgnoreCase(specialization, pageable);
        } else {
            doctorPage = doctorRepository.findAll(pageable);
        }

        return new PaginatedResponse<>(
            doctorPage.getContent(),
            page,
            doctorPage.getTotalPages(),
            doctorPage.getTotalElements(),
            limit
        );
    }

    public PaginatedResponse<Doctor> searchDoctorsByName(String name) {
        return doctorRepository.findByNameContainingIgnoreCase(name);
    }

    public PaginatedResponse<Doctor> searchDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
    }

    public Doctor getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
    }
}