package com.medicalsystem.clinic_backend.config;

import com.medicalsystem.clinic_backend.model.*;
import com.medicalsystem.clinic_backend.model.enums.Role;
import com.medicalsystem.clinic_backend.model.enums.TestStatus;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;
import com.medicalsystem.clinic_backend.model.enums.ResultStatus;
import com.medicalsystem.clinic_backend.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final DoctorRepository doctorRepository;
    private final TechnicianRepository technicianRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PatientRepository patientRepository;
    private final LabTestRepository labTestRepository;
    private final MedicationRepository medicationRepository;
    private final ExaminationRepository examinationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final TestResultRepository testResultRepository;
    private final AssignedTestRepository assignedTestRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {
        // Initialize organizations
        Organization mainHospital = createOrganization("Main Medical Center", "123 Medical Street", "123-456-7890", "contact@medicalcenter.com");
        Organization branchClinic = createOrganization("Branch Clinic", "456 Health Avenue", "123-456-7891", "contact@branchclinic.com");

        // Initialize doctors
        Doctor doctor1 = createDoctor("John Doe", "doctor1@example.com", "password", "Cardiology", "DOC123456", mainHospital);
        Doctor doctor2 = createDoctor("Jane Smith", "doctor2@example.com", "password", "Neurology", "DOC789012", mainHospital);
        Doctor doctor3 = createDoctor("Mike Johnson", "doctor3@example.com", "password", "Pediatrics", "DOC345678", branchClinic);

        // Initialize technicians
        Technician tech1 = createTechnician("Alice Brown", "tech1@example.com", "password", "Laboratory", mainHospital);
        Technician tech2 = createTechnician("Bob Wilson", "tech2@example.com", "password", "Radiology", mainHospital);
        Technician tech3 = createTechnician("Carol Davis", "tech3@example.com", "password", "Laboratory", branchClinic);

        // Initialize patients
        Patient patient1 = createPatient("Sarah Miller", "patient1@example.com", "123-456-7892", "1990-01-01", "Female", mainHospital);
        Patient patient2 = createPatient("Tom Wilson", "patient2@example.com", "123-456-7893", "1985-05-15", "Male", mainHospital);
        Patient patient3 = createPatient("Emma Davis", "patient3@example.com", "123-456-7894", "1995-12-30", "Female", branchClinic);

        // Initialize lab tests
        LabTest bloodTest = createLabTest("Complete Blood Count", "CBC", "Blood test to measure different components of blood", 50.00);
        LabTest urineTest = createLabTest("Urinalysis", "UA", "Analysis of urine sample", 30.00);
        LabTest xrayTest = createLabTest("Chest X-Ray", "CXR", "Radiographic image of the chest", 100.00);

        // Verify lab tests were created and saved
        if (bloodTest == null || urineTest == null || xrayTest == null) {
            throw new IllegalStateException("Failed to create lab tests");
        }

        // Ensure all tests have IDs (they should already have IDs from createLabTest)
        if (bloodTest.getId() == null || urineTest.getId() == null || xrayTest.getId() == null) {
            throw new IllegalStateException("Failed to save lab tests - no IDs generated");
        }

        // Fetch the saved tests to ensure they are managed entities with IDs
        LabTest savedBloodTest = labTestRepository.findById(bloodTest.getId()).orElseThrow();
        LabTest savedXrayTest = labTestRepository.findById(xrayTest.getId()).orElseThrow();

        // Initialize medications
        Medication med1 = createMedication("Amoxicillin", "Antibiotic", "500mg", "Oral", 10.00);
        Medication med2 = createMedication("Ibuprofen", "Pain reliever", "400mg", "Oral", 5.00);
        Medication med3 = createMedication("Insulin", "Diabetes medication", "100 units/ml", "Injection", 25.00);

        // Create some examinations only if they don't exist
        Examination exam1 = null;
        Examination exam2 = null;
        Examination exam3 = null;
        if (examinationRepository.count() == 0) {
            exam1 = createExamination(doctor1, patient1, "Regular checkup", "Patient is in good health");
            exam2 = createExamination(doctor2, patient2, "Neurological consultation", "Follow-up required");
            exam3 = createExamination(doctor3, patient3, "Pediatric checkup", "Vaccination needed");
        } else {
            List<Examination> existingExams = examinationRepository.findAll();
            if (!existingExams.isEmpty()) {
                exam1 = existingExams.get(0);
                if (existingExams.size() > 1) {
                    exam2 = existingExams.get(1);
                }
                if (existingExams.size() > 2) {
                    exam3 = existingExams.get(2);
                }
            }
        }

        // Create some prescriptions only if we have valid examinations
        if (prescriptionRepository.count() == 0 && exam1 != null) {
            Set<Medication> medications1 = new HashSet<>();
            if (med1 != null) medications1.add(med1);
            if (med2 != null) medications1.add(med2);
            
            Set<Medication> medications2 = new HashSet<>();
            if (med3 != null) medications2.add(med3);
            
            createPrescription(doctor1, patient1, "Take medication for 7 days", medications1, exam1);
            if (exam2 != null) {
                createPrescription(doctor2, patient2, "Take medication for 14 days", medications2, exam2);
            }
        }

        // Create some assigned tests first
        AssignedTest assignedTest1 = null;
        AssignedTest assignedTest2 = null;
        if (assignedTestRepository.count() == 0) {
            // Ensure we have valid test entities with IDs
            if (savedBloodTest == null || savedBloodTest.getId() == null) {
                throw new IllegalStateException("Blood test not properly saved before creating assigned test");
            }
            if (savedXrayTest == null || savedXrayTest.getId() == null) {
                throw new IllegalStateException("X-ray test not properly saved before creating assigned test");
            }
            
            // Re-fetch the tests to ensure they are managed entities
            LabTest managedBloodTest = labTestRepository.findById(savedBloodTest.getId())
                .orElseThrow(() -> new IllegalStateException("Blood test not found after saving"));
            LabTest managedXrayTest = labTestRepository.findById(savedXrayTest.getId())
                .orElseThrow(() -> new IllegalStateException("X-ray test not found after saving"));
            
            // Create assigned tests with managed test entities
            assignedTest1 = createAssignedTest(doctor1, patient1, managedBloodTest, tech1, TestStatus.PENDING);
            assignedTest2 = createAssignedTest(doctor2, patient2, managedXrayTest, tech2, TestStatus.COMPLETED);
        } else {
            List<AssignedTest> existingTests = assignedTestRepository.findAll();
            if (!existingTests.isEmpty()) {
                assignedTest1 = existingTests.get(0);
                if (existingTests.size() > 1) {
                    assignedTest2 = existingTests.get(1);
                }
            }
        }

        // Create test results only if we have assigned tests
        if (testResultRepository.count() == 0 && assignedTest1 != null) {
            createTestResult(tech1, patient1, savedBloodTest, "Normal", "All parameters within range", assignedTest1);
            if (assignedTest2 != null) {
                createTestResult(tech2, patient2, savedXrayTest, "Clear", "No abnormalities detected", assignedTest2);
            }
        }
    }

    private Organization createOrganization(String name, String address, String phone, String email) {
        if (organizationRepository.count() == 0) {
            Organization org = new Organization();
            org.setName(name);
            org.setAddress(address);
            org.setPhone(phone);
            org.setEmail(email);
            org.setCreatedAt(new Date());
            org.setUpdatedAt(new Date());
            return organizationRepository.save(org);
        }
        return organizationRepository.findAll().get(0);
    }

    private Doctor createDoctor(String name, String email, String password, String specialization, String licenseNumber, Organization org) {
        // Check if a doctor with this email already exists
        Doctor existingDoctor = doctorRepository.findByEmail(email).orElse(null);
        if (existingDoctor != null) {
            return existingDoctor;
        }

        // Create new doctor if doesn't exist
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setRole(Role.DOCTOR);
        user.setEnabled(true);
        user.setOrganization(org);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user = userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSpecialization(specialization);
        doctor.setPhone("123-456-7890");
        doctor.setEmail(email);
        doctor.setLicenseNumber(licenseNumber);
        doctor.setCreatedAt(new Date());
        doctor.setUpdatedAt(new Date());
        return doctorRepository.save(doctor);
    }

    private Technician createTechnician(String name, String email, String password, String department, Organization org) {
        if (technicianRepository.count() == 0) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setRole(Role.TECHNICIAN);
            user.setEnabled(true);
            user.setOrganization(org);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            user = userRepository.save(user);

            Technician tech = new Technician();
            tech.setUser(user);
            tech.setDepartment(department);
            tech.setPhone("123-456-7891");
            tech.setEmail(email);
            tech.setCreatedAt(new Date());
            tech.setUpdatedAt(new Date());
            return technicianRepository.save(tech);
        }
        return null;
    }

    private Patient createPatient(String name, String email, String phone, String dateOfBirth, String gender, Organization org) {
        // Check if a user with this email already exists
        User existingUser = userRepository.findByEmail(email).orElse(null);
        if (existingUser != null) {
            // If a patient with this user exists, return it
            Patient existingPatient = patientRepository.findByEmail(email).orElse(null);
            if (existingPatient != null) {
                return existingPatient;
            }
            // If user exists but not as a patient, create patient for this user
            Patient patient = new Patient();
            patient.setUser(existingUser);
            patient.setName(name);
            patient.setPhone(phone);
            patient.setEmail(email);
            patient.setDateOfBirth(dateOfBirth);
            patient.setGender(gender);
            patient.setAddress("123 Main Street");
            patient.setCreatedAt(new Date());
            patient.setUpdatedAt(new Date());
            return patientRepository.save(patient);
        }
        // Otherwise, create new user and patient
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("password"));
        user.setName(name);
        user.setRole(Role.PATIENT);
        user.setEnabled(true);
        user.setOrganization(org);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user = userRepository.save(user);

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setName(name);
        patient.setPhone(phone);
        patient.setEmail(email);
        patient.setDateOfBirth(dateOfBirth);
        patient.setGender(gender);
        patient.setAddress("123 Main Street");
        patient.setCreatedAt(new Date());
        patient.setUpdatedAt(new Date());
        return patientRepository.save(patient);
    }

    private LabTest createLabTest(String name, String code, String description, double price) {
        // Check if a test with this code already exists
        LabTest existingTest = labTestRepository.findByCode(code).orElse(null);
        if (existingTest != null) {
            // Update the existing test with new values
            existingTest.setName(name);
            existingTest.setDescription(description);
            existingTest.setPrice(price);
            existingTest.setStatus("ACTIVE");
            existingTest.setUpdatedAt(new Date());
            
            // Ensure the test is properly managed
            existingTest = labTestRepository.save(existingTest);
            if (existingTest.getId() == null) {
                throw new IllegalStateException("Failed to save existing test - no ID generated");
            }
            
            return existingTest;
        }

        // Create new test if it doesn't exist
        LabTest test = new LabTest();
        test.setName(name);
        test.setCode(code);
        test.setDescription(description);
        test.setPrice(price);
        test.setStatus("ACTIVE");
        test.setCreatedAt(new Date());
        test.setUpdatedAt(new Date());
        
        test = labTestRepository.save(test);
        
        // Validate that the test was saved correctly
        if (test == null) {
            throw new IllegalStateException("Failed to create new test - test is null");
        }
        if (test.getId() == null) {
            throw new IllegalStateException("Failed to create new test - no ID generated");
        }
        
        return test;
    }

    private Medication createMedication(String name, String description, String dosage, String route, double price) {
        if (medicationRepository.count() == 0) {
            Medication medication = new Medication();
            medication.setName(name);
            medication.setDescription(description);
            medication.setDosage(dosage);
            medication.setDosageInfo(dosage);
            medication.setRoute(route);
            medication.setPrice(price);
            medication.setCreatedAt(new Date());
            medication.setUpdatedAt(new Date());
            return medicationRepository.save(medication);
        }
        return null;
    }

    private Examination createExamination(Doctor doctor, Patient patient, String diagnosis, String notes) {
        if (examinationRepository.count() == 0) {
            Examination exam = new Examination();
            exam.setDoctor(doctor);
            exam.setPatient(patient);
            exam.setDiagnosis(diagnosis);
            exam.setNotes(notes);
            exam.setExaminationDate(new Date());
            exam.setSymptoms("Initial consultation");
            exam.setStatus(ExaminationStatus.SCHEDULED);
            exam.setCreatedAt(new Date());
            exam.setUpdatedAt(new Date());
            return examinationRepository.save(exam);
        }
        return null;
    }

    private Prescription createPrescription(Doctor doctor, Patient patient, String instructions, Set<Medication> medications, Examination examination) {
        if (prescriptionRepository.count() == 0) {
            Prescription prescription = new Prescription();
            prescription.setDoctor(doctor);
            prescription.setPatient(patient);
            prescription.setInstructions(instructions);
            prescription.setMedications(medications != null ? medications : new HashSet<>());
            prescription.setDiagnosis("Initial diagnosis");
            prescription.setNotes("Follow-up required");
            prescription.setExaminationId(examination.getId());
            prescription.setPrescriptionDate(new Date());
            prescription.setCreatedAt(new Date());
            prescription.setUpdatedAt(new Date());
            return prescriptionRepository.save(prescription);
        }
        return null;
    }

    private TestResult createTestResult(Technician technician, Patient patient, LabTest test, String result, String notes, AssignedTest assignedTest) {
        if (testResultRepository.count() == 0) {
            TestResult testResult = new TestResult();
            testResult.setTechnician(technician);
            testResult.setPatient(patient);
            testResult.setTest(test);
            testResult.setResult(result);
            testResult.setNotes(notes);
            testResult.setAssignedTestId(assignedTest.getId());
            testResult.setStatus(ResultStatus.COMPLETED);
            testResult.setResultDate(new Date());
            testResult.setCreatedAt(new Date());
            testResult.setUpdatedAt(new Date());
            return testResultRepository.save(testResult);
        }
        return null;
    }

    private AssignedTest createAssignedTest(Doctor doctor, Patient patient, LabTest test, Technician technician, TestStatus status) {
        // Ensure all entities are managed
        Doctor managedDoctor = doctorRepository.findById(doctor.getId()).orElseThrow(() -> 
            new IllegalArgumentException("Doctor not found: " + doctor.getId()));
        Patient managedPatient = patientRepository.findById(patient.getId()).orElseThrow(() -> 
            new IllegalArgumentException("Patient not found: " + patient.getId()));
        LabTest managedTest = labTestRepository.findById(test.getId()).orElseThrow(() -> 
            new IllegalArgumentException("LabTest not found: " + test.getId()));
        
        // Get or create the examination first
        Examination examination;
        List<Examination> patientExaminations = examinationRepository.findByPatientId(patient.getId());
        if (!patientExaminations.isEmpty()) {
            examination = patientExaminations.get(0);
        } else {
            // Create a new examination if none exists
            examination = new Examination();
            examination.setDoctor(managedDoctor);
            examination.setPatient(managedPatient);
            examination.setStatus(ExaminationStatus.IN_PROGRESS);
            examination.setExaminationDate(new Date());
            examination.setSymptoms("Lab test assignment");
            examination.setDiagnosis("Initial assessment");
            examination.setNotes("Created for lab test assignment");
            examination.setCreatedAt(new Date());
            examination.setUpdatedAt(new Date());
            examination = examinationRepository.save(examination);
        }
        
        // Create the assigned test
        AssignedTest assignedTest = new AssignedTest();
        assignedTest.setExamination(examination);
        assignedTest.setDoctor(managedDoctor);
        assignedTest.setPatient(managedPatient);
        assignedTest.setTechnician(technician);
        assignedTest.setStatus(status);
        assignedTest.setAssignedDate(new Date());
        
        // Set up bidirectional relationship with test
        // Don't call both setTest and addAssignedTest - setTest already handles the bidirectional relationship
        assignedTest.setTest(managedTest);
        
        // Save the assigned test
        AssignedTest savedTest = assignedTestRepository.save(assignedTest);
        
        // Update the bidirectional relationship after saving
        if (managedTest != null && !managedTest.getAssignedTests().contains(savedTest)) {
            managedTest.addAssignedTest(savedTest);
        }
        
        // Validate after save
        if (savedTest.getTest() == null || savedTest.getTest().getId() == null) {
            throw new IllegalStateException("Test relationship was lost after saving - test ID: " + 
                (savedTest.getTest() != null ? savedTest.getTest().getId() : "null"));
        }
        if (savedTest.getDoctor() == null || savedTest.getDoctor().getId() == null) {
            throw new IllegalStateException("Doctor relationship was lost after saving");
        }
        if (savedTest.getPatient() == null || savedTest.getPatient().getId() == null) {
            throw new IllegalStateException("Patient relationship was lost after saving");
        }
        
        // Final validation of test relationship
        if (savedTest.getTest() == null) {
            throw new IllegalStateException("Test relationship is null after save");
        }
        if (savedTest.getTest().getId() == null) {
            throw new IllegalStateException("Test ID is null after save");
        }
        
        return savedTest;
    }
}