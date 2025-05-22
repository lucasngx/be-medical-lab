package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.enums.ExamStatus;
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
    Page<Examination> findByStatus(Pageable pageable , ExamStatus status);
    List<Examination> findByExamDateBetween(Date startDate, Date endDate);
}