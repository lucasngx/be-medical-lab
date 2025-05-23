package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Medication;
import com.medicalsystem.clinic_backend.repository.MedicationRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;

    @Override
    public PaginatedResponse<Medication> getAllMedications(Pageable pageable) {
        Page<Medication> page = medicationRepository.findAll(pageable);
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Medication not found with id: " + id));
    }

    @Override
    @Transactional
    public Medication createMedication(Medication medication) {
        medication.setCreatedAt(new Date());
        medication.setUpdatedAt(new Date());
        return medicationRepository.save(medication);
    }

    @Override
    @Transactional
    public Medication updateMedication(Long id, Medication medicationDetails) {
        Medication medication = getMedicationById(id);
        
        medication.setName(medicationDetails.getName());
        medication.setDosageInfo(medicationDetails.getDosageInfo());
        medication.setSideEffects(medicationDetails.getSideEffects());
        medication.setUpdatedAt(new Date());
        
        return medicationRepository.save(medication);
    }

    @Override
    @Transactional
    public void deleteMedication(Long id) {
        Medication medication = getMedicationById(id);
        medicationRepository.delete(medication);
    }

    @Override
    public List<Medication> searchMedicationsByName(String name) {
        return medicationRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Medication> getMedicationsByCategory(String category) {
        return medicationRepository.findByCategory(category);
    }
} 