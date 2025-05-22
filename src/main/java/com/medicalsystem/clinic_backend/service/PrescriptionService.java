package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.Prescription;
import com.medicalsystem.clinic_backend.model.PrescriptionItem;
import com.medicalsystem.clinic_backend.model.enums.ExamStatus;
import com.medicalsystem.clinic_backend.repository.PrescriptionRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final ExaminationService examinationService;

    public PaginatedResponse<Prescription> getPaginatedPrescriptions(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Prescription> pageResult = prescriptionRepository.findAll(pageable);

        PaginatedResponse<Prescription> response = new PaginatedResponse<>();
        response.setData(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setPage(page);
        response.setLimit(limit);
        return response;
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));
    }

    public Prescription getPrescriptionByExaminationId(Long examinationId) {
        return prescriptionRepository.findByExaminationId(examinationId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found for examination id: " + examinationId));
    }

    @Transactional
    public Prescription createPrescriptionWithItems(Prescription prescription) {
        // Get and update examination
        Examination examination = examinationService.getExaminationById(prescription.getExaminationId());
        examinationService.updateExaminationStatus(examination.getId(), ExamStatus.COMPLETED);

        // Set back-references for prescription items
        if (prescription.getPrescriptionItems() != null) {
            prescription.getPrescriptionItems().forEach(item -> item.setPrescription(prescription));
        }

        // Save and return
        return prescriptionRepository.save(prescription);
    }

    public Prescription updatePrescription(Long id, Prescription prescriptionDetails) {
        Prescription prescription = getPrescriptionById(id);
        prescription.setDiagnosis(prescriptionDetails.getDiagnosis());
        prescription.setNotes(prescriptionDetails.getNotes());
        return prescriptionRepository.save(prescription);
    }
}
