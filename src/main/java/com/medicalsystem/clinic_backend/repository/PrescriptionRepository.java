package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatient_Id(Long patientId);
    List<Prescription> findByDoctor_Id(Long doctorId);
    Optional<Prescription> findByExaminationId(Long examinationId);
    List<Prescription> findByDiagnosisContainingIgnoreCase(String diagnosisKeyword);
    Page<Prescription> findByPatient_Id(Long patientId, Pageable pageable);
    Page<Prescription> findByDoctor_Id(Long doctorId, Pageable pageable);
}