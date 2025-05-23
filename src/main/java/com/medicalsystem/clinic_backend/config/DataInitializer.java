package com.medicalsystem.clinic_backend.config;

import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.model.Technician;
import com.medicalsystem.clinic_backend.model.User;
import com.medicalsystem.clinic_backend.model.enums.Role;
import com.medicalsystem.clinic_backend.repository.DoctorRepository;
import com.medicalsystem.clinic_backend.repository.TechnicianRepository;
import com.medicalsystem.clinic_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final DoctorRepository doctorRepository;
    private final TechnicianRepository technicianRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Initialize doctors if none exist
        if (doctorRepository.count() == 0) {
            User user = new User();
            user.setEmail("doctor@example.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setName("John Doe");
            user.setRole(Role.DOCTOR);
            user.setEnabled(true);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            user = userRepository.save(user);

            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setSpecialization("Cardiology");
            doctor.setPhone("123-456-7890");
            doctor.setEmail("doctor@example.com");
            doctor.setCreatedAt(new Date());
            doctor.setUpdatedAt(new Date());
            doctorRepository.save(doctor);
        }

        // Initialize technicians if none exist
        if (technicianRepository.count() == 0) {
            User user = new User();
            user.setEmail("tech@example.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setName("Jane Smith");
            user.setRole(Role.TECHNICIAN);
            user.setEnabled(true);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            user = userRepository.save(user);

            Technician tech = new Technician();
            tech.setUser(user);
            tech.setDepartment("Laboratory");
            tech.setPhone("123-456-7891");
            tech.setEmail("tech@example.com");
            tech.setCreatedAt(new Date());
            tech.setUpdatedAt(new Date());
            technicianRepository.save(tech);
        }
    }
}