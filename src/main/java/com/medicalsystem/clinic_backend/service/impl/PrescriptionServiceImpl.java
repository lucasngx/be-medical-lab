package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.Prescription;
import com.medicalsystem.clinic_backend.model.PrescriptionItem;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;
import com.medicalsystem.clinic_backend.repository.PrescriptionRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.ExaminationService;
import com.medicalsystem.clinic_backend.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final ExaminationService examinationService;

    @Override
    public PaginatedResponse<Prescription> getAllPrescriptions(Pageable pageable) {
        Page<Prescription> page = prescriptionRepository.findAll(pageable);
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));
    }

    @Override
    public Prescription getPrescriptionByExaminationId(Long examinationId) {
        return prescriptionRepository.findByExaminationId(examinationId)
            .orElseThrow(() -> new ResourceNotFoundException("Prescription not found for examination id: " + examinationId));
    }

    @Override
    @Transactional
    public Prescription createPrescription(Prescription prescription) {
        prescription.setCreatedAt(new Date());
        prescription.setUpdatedAt(new Date());
        return prescriptionRepository.save(prescription);
    }

    @Override
    @Transactional
    public Prescription createPrescriptionWithItems(Prescription prescription) {
        // Get and update examination
        Examination examination = examinationService.getExaminationById(prescription.getExaminationId());
        examinationService.updateExaminationStatus(examination.getId(), ExaminationStatus.COMPLETED);

        // Set prescriptionId for prescription items
        if (prescription.getItems() != null) {
            prescription.getItems().forEach(item -> item.setPrescription(prescription));
        }

        // Save and return
        return prescriptionRepository.save(prescription);
    }

    @Override
    @Transactional
    public Prescription updatePrescription(Long id, Prescription prescriptionDetails) {
        Prescription prescription = getPrescriptionById(id);

        prescription.setPatientId(prescriptionDetails.getPatientId());
        prescription.setDoctorId(prescriptionDetails.getDoctorId());
        prescription.setDiagnosis(prescriptionDetails.getDiagnosis());
        prescription.setNotes(prescriptionDetails.getNotes());
        prescription.setUpdatedAt(new Date());

        // Update items if provided
        if (prescriptionDetails.getItems() != null) {
            prescription.getItems().clear();
            for (PrescriptionItem item : prescriptionDetails.getItems()) {
                item.setPrescription(prescription);
                prescription.getItems().add(item);
            }
        }

        return prescriptionRepository.save(prescription);
    }

    @Override
    @Transactional
    public void deletePrescription(Long id) {
        Prescription prescription = getPrescriptionById(id);
        prescriptionRepository.delete(prescription);
    }

    @Override
    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    @Override
    public List<Prescription> getPrescriptionsByDoctorId(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId);
    }
} 