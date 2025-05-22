package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.repository.DoctorRepository;
import com.medicalsystem.clinic_backend.repository.TechnicianRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final DoctorRepository doctorRepository;
    private final TechnicianRepository technicianRepository;

    public CustomUserDetailsService(DoctorRepository doctorRepository, TechnicianRepository technicianRepository) {
        this.doctorRepository = doctorRepository;
        this.technicianRepository = technicianRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try to find a doctor first
        Doctor doctor = doctorRepository.findByEmail(email);
        if (doctor != null) {
            return new User(
                doctor.getEmail(),
                doctor.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + doctor.getRole().name()))
            );
        }

        // If not a doctor, try to find a technician
        Technician technician = technicianRepository.findByEmail(email);
        if (technician != null) {
            return new User(
                technician.getEmail(),
                technician.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + technician.getRole().name()))
            );
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
} 