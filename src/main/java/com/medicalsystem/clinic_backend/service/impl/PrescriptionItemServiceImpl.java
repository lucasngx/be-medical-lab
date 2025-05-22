package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.PrescriptionItem;
import com.medicalsystem.clinic_backend.repository.PrescriptionItemRepository;
import com.medicalsystem.clinic_backend.service.PrescriptionItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionItemServiceImpl implements PrescriptionItemService {
    private final PrescriptionItemRepository prescriptionItemRepository;

    @Override
    public List<PrescriptionItem> getAllPrescriptionItems() {
        return prescriptionItemRepository.findAll();
    }

    @Override
    public PrescriptionItem getPrescriptionItemById(Long id) {
        return prescriptionItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription Item not found with id: " + id));
    }

    @Override
    public List<PrescriptionItem> getPrescriptionItemsByPrescriptionId(Long prescriptionId) {
        return prescriptionItemRepository.findByPrescriptionId(prescriptionId);
    }

    @Override
    @Transactional
    public PrescriptionItem createPrescriptionItem(PrescriptionItem prescriptionItem) {
        return prescriptionItemRepository.save(prescriptionItem);
    }

    @Override
    @Transactional
    public PrescriptionItem updatePrescriptionItem(Long id, PrescriptionItem prescriptionItemDetails) {
        PrescriptionItem prescriptionItem = getPrescriptionItemById(id);

        prescriptionItem.setMedicationName(prescriptionItemDetails.getMedicationName());
        prescriptionItem.setDosage(prescriptionItemDetails.getDosage());
        prescriptionItem.setDuration(prescriptionItemDetails.getDuration());
        prescriptionItem.setFrequency(prescriptionItemDetails.getFrequency());

        return prescriptionItemRepository.save(prescriptionItem);
    }

    @Override
    @Transactional
    public void deletePrescriptionItem(Long id) {
        PrescriptionItem prescriptionItem = getPrescriptionItemById(id);
        prescriptionItemRepository.delete(prescriptionItem);
    }
} 