package com.medicalsystem.clinic_backend.repository;

import com.medicalsystem.clinic_backend.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
} 