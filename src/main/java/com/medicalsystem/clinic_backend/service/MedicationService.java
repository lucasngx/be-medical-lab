package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.model.Medication;
import com.medicalsystem.clinic_backend.repository.MedicationRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationService {
    private final MedicationRepository medicationRepository;

    public PaginatedResponse<Medication> getPaginatedMedications(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Medication> pageResult = medicationRepository.findAll(pageable);

        PaginatedResponse<Medication> response = new PaginatedResponse<>();
        response.setData(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setPage(page);
        response.setLimit(limit);
        return response;
    }

    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found with id: " + id));
    }

    public List<Medication> searchMedicationsByName(String name) {
        return medicationRepository.findByNameContainingIgnoreCase(name);
    }

    public Medication createMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public Medication updateMedication(Long id, Medication medicationDetails) {
        Medication medication = getMedicationById(id);

        medication.setName(medicationDetails.getName());
        medication.setDosageInfo(medicationDetails.getDosageInfo());
        medication.setSideEffects(medicationDetails.getSideEffects());

        return medicationRepository.save(medication);
    }

    public void deleteMedication(Long id) {
        Medication medication = getMedicationById(id);
        medicationRepository.delete(medication);
    }
}