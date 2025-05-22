package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Optional<Prescription> findByExaminationId(Long examinationId);
    List<Prescription> findByDiagnosisContainingIgnoreCase(String diagnosisKeyword);
}