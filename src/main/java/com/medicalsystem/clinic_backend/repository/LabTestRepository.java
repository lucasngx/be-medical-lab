package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long> {
    List<LabTest> findByNameContainingIgnoreCase(String name);
    List<LabTest> findByAssignedTests_PatientId(Long patientId);
    Optional<LabTest> findByCode(String code);
}
