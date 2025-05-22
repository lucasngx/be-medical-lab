package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByNameContainingIgnoreCase(String name);
    List<Medication> findByCategory(String category);
}