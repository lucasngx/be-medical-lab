package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.LabTest;
import com.medicalsystem.clinic_backend.repository.LabTestRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.LabTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LabTestServiceImpl implements LabTestService {
    private final LabTestRepository labTestRepository;

    @Override
    public PaginatedResponse<LabTest> getAllLabTests(Pageable pageable) {
        Page<LabTest> page = labTestRepository.findAll(pageable);
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public LabTest getLabTestById(Long id) {
        return labTestRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("LabTest not found with id: " + id));
    }

    @Override
    @Transactional
    public LabTest createLabTest(LabTest labTest) {
        labTest.setCreatedAt(new Date());
        labTest.setUpdatedAt(new Date());
        labTest.setStatus("ACTIVE");
        return labTestRepository.save(labTest);
    }

    @Override
    @Transactional
    public LabTest updateLabTest(Long id, LabTest labTestDetails) {
        LabTest labTest = getLabTestById(id);

        labTest.setName(labTestDetails.getName());
        labTest.setDescription(labTestDetails.getDescription());
        labTest.setPrice(labTestDetails.getPrice());
        labTest.setStatus(labTestDetails.getStatus());
        labTest.setUpdatedAt(new Date());

        return labTestRepository.save(labTest);
    }

    @Override
    @Transactional
    public void deleteLabTest(Long id) {
        LabTest labTest = getLabTestById(id);
        labTestRepository.delete(labTest);
    }

    @Override
    public List<LabTest> searchLabTestsByName(String name) {
        return labTestRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<LabTest> getLabTestsByPatientId(Long patientId) {
        return labTestRepository.findByPatientId(patientId);
    }
} 