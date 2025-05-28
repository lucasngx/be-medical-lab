package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.model.Medication;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicationService {
    PaginatedResponse<Medication> getAllMedications(Pageable pageable);
    Medication getMedicationById(Long id);
    Medication createMedication(Medication medication);
    Medication updateMedication(Long id, Medication medication);
    void deleteMedication(Long id);
    List<Medication> searchMedicationsByName(String name);
} 