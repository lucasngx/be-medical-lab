package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.model.Patient;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    PaginatedResponse<Patient> getAllPatients(Pageable pageable);
    Patient getPatientById(Long id);
    List<Patient> searchPatientsByName(String name);
    Patient createPatient(Patient patient);
    Patient updatePatient(Long id, Patient patientDetails);
    void deletePatient(Long id);
    Patient getById(Long id);
}