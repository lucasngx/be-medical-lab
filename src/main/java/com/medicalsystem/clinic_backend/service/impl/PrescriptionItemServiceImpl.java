package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.PrescriptionItem;
import com.medicalsystem.clinic_backend.repository.PrescriptionItemRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.PrescriptionItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionItemServiceImpl implements PrescriptionItemService {
    private final PrescriptionItemRepository prescriptionItemRepository;

    @Override
    public PaginatedResponse<PrescriptionItem> getAllPrescriptionItems(Pageable pageable) {
        Page<PrescriptionItem> page = prescriptionItemRepository.findAll(pageable);
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public PrescriptionItem getPrescriptionItemById(Long id) {
        return prescriptionItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("PrescriptionItem not found with id: " + id));
    }

    @Override
    @Transactional
    public PrescriptionItem createPrescriptionItem(PrescriptionItem prescriptionItem) {
        prescriptionItem.setCreatedAt(new Date());
        prescriptionItem.setUpdatedAt(new Date());
        return prescriptionItemRepository.save(prescriptionItem);
    }

    @Override
    @Transactional
    public PrescriptionItem updatePrescriptionItem(Long id, PrescriptionItem prescriptionItemDetails) {
        PrescriptionItem prescriptionItem = getPrescriptionItemById(id);

        prescriptionItem.setMedication(prescriptionItemDetails.getMedication());
        prescriptionItem.setDosage(prescriptionItemDetails.getDosage());
        prescriptionItem.setFrequency(prescriptionItemDetails.getFrequency());
        prescriptionItem.setDuration(prescriptionItemDetails.getDuration());
        prescriptionItem.setInstructions(prescriptionItemDetails.getInstructions());
        prescriptionItem.setUpdatedAt(new Date());

        return prescriptionItemRepository.save(prescriptionItem);
    }

    @Override
    @Transactional
    public void deletePrescriptionItem(Long id) {
        PrescriptionItem prescriptionItem = getPrescriptionItemById(id);
        prescriptionItemRepository.delete(prescriptionItem);
    }

    @Override
    public List<PrescriptionItem> getPrescriptionItemsByPrescriptionId(Long prescriptionId) {
        return prescriptionItemRepository.findByPrescriptionId(prescriptionId);
    }

    @Override
    public List<PrescriptionItem> getPrescriptionItemsByMedicationId(Long medicationId) {
        return prescriptionItemRepository.findByMedicationId(medicationId);
    }

    @Override
    public List<PrescriptionItem> getPrescriptionItemsByPatientId(Long patientId) {
        return prescriptionItemRepository.findByPatientId(patientId);
    }
} 