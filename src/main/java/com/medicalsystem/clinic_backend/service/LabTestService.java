package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.LabTest;
import com.medicalsystem.clinic_backend.model.enums.ExamStatus;
import com.medicalsystem.clinic_backend.repository.LabTestRepository;
import com.medicalsystem.clinic_backend.response.ExaminationResponse;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabTestService {
    private final LabTestRepository labTestRepository;

    public PaginatedResponse<LabTest> getPaginatedLabTests(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<LabTest> pageResult = labTestRepository.findAll(pageable);

        PaginatedResponse<LabTest> response = new PaginatedResponse<>();
        response.setData(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setPage(page);
        response.setLimit(limit);
        return response;
    }

    public LabTest getLabTestById(Long id) {
        return labTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab Test not found with id: " + id));
    }

    public List<LabTest> searchLabTestsByName(String name) {
        return labTestRepository.findByNameContainingIgnoreCase(name);
    }

    public LabTest createLabTest(LabTest labTest) {
        return labTestRepository.save(labTest);
    }

    public LabTest updateLabTest(Long id, LabTest labTestDetails) {
        LabTest labTest = getLabTestById(id);

        labTest.setName(labTestDetails.getName());
        labTest.setDescription(labTestDetails.getDescription());
        labTest.setUnit(labTestDetails.getUnit());
        labTest.setRefRange(labTestDetails.getRefRange());

        return labTestRepository.save(labTest);
    }

    public void deleteLabTest(Long id) {
        LabTest labTest = getLabTestById(id);
        labTestRepository.delete(labTest);
    }
}