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
    List<AssignedTest> findByLabTestId(Long labTestId);
    Page<AssignedTest> findByStatus(Pageable pageable, TestStatus status);

    @Query("SELECT at FROM AssignedTest at " +
           "JOIN Examination e ON at.examinationId = e.id " +
           "JOIN Patient p ON e.patientId = p.id " +
           "WHERE (:patientName IS NULL OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :patientName, '%')) " +
           "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :patientName, '%'))) " +
           "AND (:status IS NULL OR e.status = :status)")
    Page<AssignedTest> searchAssignedTests(
        @Param("patientName") String patientName,
        @Param("status") ExaminationStatus status,
        Pageable pageable
    );
}