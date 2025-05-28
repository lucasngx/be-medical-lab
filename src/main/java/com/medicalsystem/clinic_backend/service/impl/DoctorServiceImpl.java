package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.model.User;
import com.medicalsystem.clinic_backend.repository.DoctorRepository;
import com.medicalsystem.clinic_backend.repository.UserRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.DoctorService;
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
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PaginatedResponse<Doctor> getAllDoctors(Pageable pageable) {
        Page<Doctor> page = doctorRepository.findAll(pageable);
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }

    @Override
    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with email: " + email));
    }

    @Override
    @Transactional
    public Doctor createDoctor(Doctor doctor) {
        // Create user first
        User user = new User();
        user.setEmail(doctor.getEmail());
        user.setPassword(passwordEncoder.encode(doctor.getUser().getPassword()));
        user.setName(doctor.getUser().getName());
        user.setRole(doctor.getUser().getRole());
        user.setEnabled(true);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user = userRepository.save(user);

        // Set user to doctor
        doctor.setUser(user);
        doctor.setCreatedAt(new Date());
        doctor.setUpdatedAt(new Date());
        
        return doctorRepository.save(doctor);
    }

    @Override
    @Transactional
    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor doctor = getDoctorById(id);
        
        // Update user details
        User user = doctor.getUser();
        user.setName(doctorDetails.getUser().getName());
        user.setEmail(doctorDetails.getEmail());
        user.setUpdatedAt(new Date());
        userRepository.save(user);

        // Update doctor details
        doctor.setSpecialization(doctorDetails.getSpecialization());
        doctor.setPhone(doctorDetails.getPhone());
        doctor.setEmail(doctorDetails.getEmail());
        doctor.setUpdatedAt(new Date());
        
        return doctorRepository.save(doctor);
    }

    @Override
    @Transactional
    public void deleteDoctor(Long id) {
        Doctor doctor = getDoctorById(id);
        doctorRepository.delete(doctor);
        userRepository.delete(doctor.getUser());
    }

    @Override
    public PaginatedResponse<Doctor> searchDoctors(String name, String specialization, Pageable pageable) {
        Page<Doctor> page;
        if ((name != null && !name.isEmpty()) || (specialization != null && !specialization.isEmpty())) {
            page = doctorRepository.findByUser_NameContainingIgnoreCaseAndSpecializationContainingIgnoreCase(
                name != null ? name : "",
                specialization != null ? specialization : "",
                pageable
            );
        } else {
            page = doctorRepository.findAll(pageable);
        }
        
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    @Override
    public List<Doctor> searchDoctorsByName(String name) {
        return doctorRepository.findByUser_NameContainingIgnoreCase(name);
    }

    @Override
    public Doctor getById(Long id) {
        return getDoctorById(id);
    }
} 