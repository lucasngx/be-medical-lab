package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.PrescriptionItem;
import com.medicalsystem.clinic_backend.repository.PrescriptionItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionItemService {
    private final PrescriptionItemRepository prescriptionItemRepository;

    public List<PrescriptionItem> getAllPrescriptionItems() {
        return prescriptionItemRepository.findAll();
    }

    public PrescriptionItem getPrescriptionItemById(Long id) {
        return prescriptionItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription Item not found with id: " + id));
    }

    public List<PrescriptionItem> getPrescriptionItemsByPrescriptionId(Long prescriptionId) {
        return prescriptionItemRepository.findByPrescriptionId(prescriptionId);
    }

    public PrescriptionItem createPrescriptionItem(PrescriptionItem prescriptionItem) {
        return prescriptionItemRepository.save(prescriptionItem);
    }

    public PrescriptionItem updatePrescriptionItem(Long id, PrescriptionItem prescriptionItemDetails) {
        PrescriptionItem prescriptionItem = getPrescriptionItemById(id);

        prescriptionItem.setMedicationId(prescriptionItemDetails.getMedicationId());
        prescriptionItem.setDosage(prescriptionItemDetails.getDosage());
        prescriptionItem.setDuration(prescriptionItemDetails.getDuration());
        prescriptionItem.setFrequency(prescriptionItemDetails.getFrequency());

        return prescriptionItemRepository.save(prescriptionItem);
    }

    public void deletePrescriptionItem(Long id) {
        PrescriptionItem prescriptionItem = getPrescriptionItemById(id);
        prescriptionItemRepository.delete(prescriptionItem);
    }
}