package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByPhone(String phone);
    List<Patient> findByNameContainingIgnoreCase(String name);
    Page<Patient> findByNameContainingIgnoreCase(String name, Pageable pageable);
}