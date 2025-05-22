package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.dto.AuthRequest;
import com.medicalsystem.clinic_backend.dto.AuthResponse;
import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.repository.DoctorRepository;
import com.medicalsystem.clinic_backend.repository.TechnicianRepository;
import com.medicalsystem.clinic_backend.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final DoctorRepository doctorRepository;
    private final TechnicianRepository technicianRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);
        
        // Check if user is a doctor
        Doctor doctor = doctorRepository.findByEmail(request.getEmail()).orElse(null);
        if (doctor != null) {
            if (!passwordEncoder.matches(request.getPassword(), doctor.getPassword())) {
                throw new RuntimeException("Invalid password");
            }
            return new AuthResponse(token, doctor.getEmail(), doctor.getRole());
        }
        
        // Check if user is a technician
        Technician technician = technicianRepository.findByEmail(request.getEmail()).orElse(null);
        if (technician != null) {
            if (!passwordEncoder.matches(request.getPassword(), technician.getPassword())) {
                throw new RuntimeException("Invalid password");
            }
            return new AuthResponse(token, technician.getEmail(), technician.getRole());
        }
        
        throw new RuntimeException("User not found");
    }

    public Doctor authenticateDoctor(String email, String password) {
        Doctor doctor = doctorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        if (!passwordEncoder.matches(password, doctor.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        return doctor;
    }

    public Technician authenticateTechnician(String email, String password) {
        Technician technician = technicianRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Technician not found"));
        
        if (!passwordEncoder.matches(password, technician.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        return technician;
    }
} 