package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {
    List<PrescriptionItem> findByPrescriptionId(Long prescriptionId);
    
    @Query("SELECT pi FROM PrescriptionItem pi WHERE pi.medication.id = :medicationId")
    List<PrescriptionItem> findByMedicationId(@Param("medicationId") Long medicationId);
    
    @Query("SELECT pi FROM PrescriptionItem pi JOIN pi.prescription p WHERE p.patientId = :patientId")
    List<PrescriptionItem> findByPatientId(@Param("patientId") Long patientId);
}