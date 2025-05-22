package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.AssignedTest;
import com.medicalsystem.clinic_backend.model.enums.TestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignedTestRepository extends JpaRepository<AssignedTest, Long> {
    List<AssignedTest> findByExaminationId(Long examinationId);
    List<AssignedTest> findByLabTestId(Long labTestId);
    Page<AssignedTest> findByStatus(Pageable pageable, TestStatus status);
}