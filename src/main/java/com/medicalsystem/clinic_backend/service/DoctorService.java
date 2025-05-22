package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorService {
    PaginatedResponse<Doctor> getAllDoctors(Pageable pageable);
    Doctor getDoctorById(Long id);
    Doctor getDoctorByEmail(String email);
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(Long id, Doctor doctorDetails);
    void deleteDoctor(Long id);
    PaginatedResponse<Doctor> searchDoctors(String firstName, String lastName, Pageable pageable);
    List<Doctor> getDoctorsBySpecialization(String specialization);
    List<Doctor> searchDoctorsByName(String name);
    Doctor getDoctorByLicenseNumber(String licenseNumber);
    Doctor getById(Long id);
} 