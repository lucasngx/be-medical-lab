package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.model.enums.TechnicianStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    Optional<Technician> findByEmail(String email);
    
    Page<Technician> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName, String lastName, Pageable pageable);
        
    Page<Technician> findByStatus(TechnicianStatus status, Pageable pageable);
    
    Page<Technician> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndStatus(
        String firstName, String lastName, TechnicianStatus status, Pageable pageable);
        
    Page<Technician> findBySpecialization(String specialization, Pageable pageable);
    
    List<Technician> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    
    List<Technician> findBySpecialization(String specialization);

    List<Technician> findByUser_NameContainingIgnoreCase(String name);

    List<Technician> findByDepartment(String department);
}