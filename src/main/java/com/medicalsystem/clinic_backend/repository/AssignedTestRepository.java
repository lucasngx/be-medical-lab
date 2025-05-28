package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.AssignedTest;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;
import com.medicalsystem.clinic_backend.model.enums.TestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignedTestRepository extends JpaRepository<AssignedTest, Long> {
    List<AssignedTest> findByExaminationId(Long examinationId);
    List<AssignedTest> findByTestId(Long testId);
    Page<AssignedTest> findByStatus(Pageable pageable, TestStatus status);
    
    @Query("SELECT at FROM AssignedTest at WHERE at.examination.patient.id = :patientId")
    List<AssignedTest> findByPatientId(@Param("patientId") Long patientId);
    
    @Query("SELECT at FROM AssignedTest at WHERE at.technician.id = :technicianId")
    List<AssignedTest> findByTechnicianId(@Param("technicianId") Long technicianId);

    @Query("SELECT at FROM AssignedTest at WHERE " +
           "(:searchTerm IS NULL OR LOWER(at.examination.patient.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(at.test.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
           "(:status IS NULL OR at.status = :status)")
    Page<AssignedTest> searchAssignedTests(
        @Param("searchTerm") String searchTerm,
        @Param("status") TestStatus status,
        Pageable pageable
    );
}