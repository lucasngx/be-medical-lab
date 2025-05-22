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
            .orElseThrow(() -> new ResourceNotFoundException("Lab Test not found with id: " + id));
    }

    @Override
    @Transactional
    public LabTest createLabTest(LabTest labTest) {
        return labTestRepository.save(labTest);
    }

    @Override
    @Transactional
    public LabTest updateLabTest(Long id, LabTest labTestDetails) {
        LabTest labTest = getLabTestById(id);

        labTest.setName(labTestDetails.getName());
        labTest.setDescription(labTestDetails.getDescription());
        labTest.setRefRange(labTestDetails.getRefRange());

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
} 