package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {
    List<Examination> findByPatientId(Long patientId);
    List<Examination> findByDoctorId(Long doctorId);
    Page<Examination> findByStatus(ExaminationStatus status, Pageable pageable);
    List<Examination> findByExaminationDateBetween(Date startDate, Date endDate);
    Page<Examination> findByPatientFirstNameContainingIgnoreCaseAndStatus(String patientName, ExaminationStatus status, Pageable pageable);
    Page<Examination> findByPatientFirstNameContainingIgnoreCase(String patientName, Pageable pageable);
}