package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findByUser_NameContainingIgnoreCase(String name);
    List<Doctor> findBySpecialization(String specialization);
    Page<Doctor> findByUser_NameContainingIgnoreCase(String name, Pageable pageable);
    Page<Doctor> findBySpecializationContainingIgnoreCase(String specialization, Pageable pageable);
    Page<Doctor> findByUser_NameContainingIgnoreCaseAndSpecializationContainingIgnoreCase(String name, String specialization, Pageable pageable);
}