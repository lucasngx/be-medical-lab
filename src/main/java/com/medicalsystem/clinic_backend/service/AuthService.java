package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.dto.AuthRequest;
import com.medicalsystem.clinic_backend.dto.AuthResponse;
import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.model.User;
import com.medicalsystem.clinic_backend.model.enums.Role;
import com.medicalsystem.clinic_backend.repository.DoctorRepository;
import com.medicalsystem.clinic_backend.repository.TechnicianRepository;
import com.medicalsystem.clinic_backend.repository.UserRepository;
import com.medicalsystem.clinic_backend.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final DoctorRepository doctorRepository;
    private final TechnicianRepository technicianRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest request) {
        System.out.println("Attempting login for email: " + request.getEmail());
        
        // Try to find a doctor by email
        Doctor doctor = doctorRepository.findByEmail(request.getEmail()).orElse(null);
        if (doctor != null) {
            System.out.println("Found doctor with email: " + request.getEmail());
            if (!passwordEncoder.matches(request.getPassword(), doctor.getUser().getPassword())) {
                System.out.println("Invalid password for doctor: " + request.getEmail());
                throw new RuntimeException("Invalid password");
            }
            // Generate JWT token
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                doctor.getEmail(), doctor.getUser().getPassword(),
                Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_" + doctor.getUser().getRole().name())
                )
            );
            String token = jwtTokenUtil.generateToken(userDetails, doctor.getUser(), doctor, null);
            AuthResponse response = new AuthResponse(
                token,
                doctor.getEmail(),
                doctor.getUser().getName(),
                doctor.getUser().getRole(),
                doctor.getId(),
                doctor.getPhone(),
                null, // department is null for doctors
                doctor.getSpecialization(),
                doctor.getUser().getOrganization().getName()
            );
            System.out.println("Doctor login successful. Response: " + response);
            return response;
        }

        // Try to find a technician by email
        Technician technician = technicianRepository.findByEmail(request.getEmail()).orElse(null);
        if (technician != null) {
            System.out.println("Found technician with email: " + request.getEmail());
            if (!passwordEncoder.matches(request.getPassword(), technician.getUser().getPassword())) {
                System.out.println("Invalid password for technician: " + request.getEmail());
                throw new RuntimeException("Invalid password");
            }
            // Generate JWT token
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                technician.getEmail(), technician.getUser().getPassword(),
                Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_" + technician.getUser().getRole().name())
                )
            );
            String token = jwtTokenUtil.generateToken(userDetails, technician.getUser(), null, technician);
            AuthResponse response = new AuthResponse(
                token,
                technician.getEmail(),
                technician.getUser().getName(),
                technician.getUser().getRole(),
                technician.getId(),
                technician.getPhone(),
                technician.getDepartment(),
                null, // specialization is null for technicians
                technician.getUser().getOrganization().getName()
            );
            System.out.println("Technician login successful. Response: " + response);
            return response;
        }

        System.out.println("User not found with email: " + request.getEmail());
        throw new RuntimeException("User not found");
    }

    @Transactional
    public Doctor registerDoctor(Doctor doctor) {
        // Create user first
        User user = new User();
        user.setEmail(doctor.getEmail());
        user.setPassword(passwordEncoder.encode(doctor.getUser().getPassword()));
        user.setName(doctor.getUser().getName());
        user.setRole(Role.DOCTOR);
        user.setEnabled(true);
        user = userRepository.save(user);

        // Set user to doctor
        doctor.setUser(user);
        return doctorRepository.save(doctor);
    }

    @Transactional
    public Technician registerTechnician(Technician technician) {
        // Create user first
        User user = new User();
        user.setEmail(technician.getEmail());
        user.setPassword(passwordEncoder.encode(technician.getUser().getPassword()));
        user.setName(technician.getUser().getName());
        user.setRole(Role.TECHNICIAN);
        user.setEnabled(true);
        user = userRepository.save(user);

        // Set user to technician
        technician.setUser(user);
        return technicianRepository.save(technician);
    }

    public Doctor authenticateDoctor(String email, String password) {
        Doctor doctor = doctorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        if (!passwordEncoder.matches(password, doctor.getUser().getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        return doctor;
    }

    public Technician authenticateTechnician(String email, String password) {
        Technician technician = technicianRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Technician not found"));
        
        if (!passwordEncoder.matches(password, technician.getUser().getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        return technician;
    }
} 