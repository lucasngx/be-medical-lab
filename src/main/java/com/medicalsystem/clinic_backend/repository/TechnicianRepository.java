package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.Technician;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    Optional<Technician> findByEmail(String email);
    
    Page<Technician> findByUser_NameContainingIgnoreCase(String name, Pageable pageable);
    
    List<Technician> findByUser_NameContainingIgnoreCase(String name);
    
    List<Technician> findByDepartment(String department);
}