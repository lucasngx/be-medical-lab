package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.dto.AuthRequest;
import com.medicalsystem.clinic_backend.dto.AuthResponse;
import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.repository.DoctorRepository;
import com.medicalsystem.clinic_backend.repository.TechnicianRepository;
import com.medicalsystem.clinic_backend.security.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final DoctorRepository doctorRepository;
    private final TechnicianRepository technicianRepository;

    public AuthService(AuthenticationManager authenticationManager,
                      UserDetailsService userDetailsService,
                      JwtTokenUtil jwtTokenUtil,
                      DoctorRepository doctorRepository,
                      TechnicianRepository technicianRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.doctorRepository = doctorRepository;
        this.technicianRepository = technicianRepository;
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);
        
        // Check if user is a doctor
        Doctor doctor = doctorRepository.findByEmail(request.getEmail());
        if (doctor != null) {
            return new AuthResponse(token, doctor.getEmail(), doctor.getRole());
        }
        
        // If not a doctor, must be a technician
        Technician technician = technicianRepository.findByEmail(request.getEmail());
        return new AuthResponse(token, technician.getEmail(), technician.getRole());
    }
} 