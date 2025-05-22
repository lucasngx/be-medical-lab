package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.TestResult;
import com.medicalsystem.clinic_backend.model.enums.ResultStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    Optional<TestResult> findByAssignedTestId(Long assignedTestId);
    List<TestResult> findByTechnicianId(Long technicianId);
    List<TestResult> findByStatus(ResultStatus status);
}