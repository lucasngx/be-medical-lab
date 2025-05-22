package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.model.PrescriptionItem;
import java.util.List;

public interface PrescriptionItemService {
    List<PrescriptionItem> getAllPrescriptionItems();
    PrescriptionItem getPrescriptionItemById(Long id);
    List<PrescriptionItem> getPrescriptionItemsByPrescriptionId(Long prescriptionId);
    PrescriptionItem createPrescriptionItem(PrescriptionItem prescriptionItem);
    PrescriptionItem updatePrescriptionItem(Long id, PrescriptionItem prescriptionItemDetails);
}