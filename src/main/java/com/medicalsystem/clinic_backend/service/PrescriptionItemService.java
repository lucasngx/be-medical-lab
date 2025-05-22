package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.model.PrescriptionItem;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PrescriptionItemService {
    PaginatedResponse<PrescriptionItem> getAllPrescriptionItems(Pageable pageable);
    PrescriptionItem getPrescriptionItemById(Long id);
    List<PrescriptionItem> getPrescriptionItemsByPrescriptionId(Long prescriptionId);
    PrescriptionItem createPrescriptionItem(PrescriptionItem prescriptionItem);
    PrescriptionItem updatePrescriptionItem(Long id, PrescriptionItem prescriptionItemDetails);
    void deletePrescriptionItem(Long id);
    List<PrescriptionItem> getPrescriptionItemsByMedicationId(Long medicationId);
    List<PrescriptionItem> getPrescriptionItemsByPatientId(Long patientId);
}