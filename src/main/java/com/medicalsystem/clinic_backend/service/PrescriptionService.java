package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.Prescription;
import com.medicalsystem.clinic_backend.model.PrescriptionItem;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;
import com.medicalsystem.clinic_backend.repository.PrescriptionRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PrescriptionService {
    PaginatedResponse<Prescription> getAllPrescriptions(Pageable pageable);
    Prescription getPrescriptionById(Long id);
    Prescription getPrescriptionByExaminationId(Long examinationId);
    Prescription createPrescription(Prescription prescription);
    Prescription createPrescriptionWithItems(Prescription prescription);
    Prescription updatePrescription(Long id, Prescription prescriptionDetails);
    void deletePrescription(Long id);
    List<Prescription> getPrescriptionsByPatientId(Long patientId);
    List<Prescription> getPrescriptionsByDoctorId(Long doctorId);
}
