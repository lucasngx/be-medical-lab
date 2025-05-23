package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.repository.DoctorRepository;
import com.medicalsystem.clinic_backend.repository.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final DoctorRepository doctorRepository;
    private final TechnicianRepository technicianRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try to find doctor
        Doctor doctor = doctorRepository.findByEmail(email).orElse(null);
        if (doctor != null) {
            return new User(
                doctor.getEmail(),
                doctor.getUser().getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + doctor.getUser().getRole().name()))
            );
        }

        // Try to find technician
        Technician technician = technicianRepository.findByEmail(email).orElse(null);
        if (technician != null) {
            return new User(
                technician.getEmail(),
                technician.getUser().getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + technician.getUser().getRole().name()))
            );
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
} 