package com.patient.management.patientservice.service;

import com.patient.management.patientservice.dto.PatientRequestDTO;
import com.patient.management.patientservice.dto.PatientResponseDTO;
import com.patient.management.patientservice.exception.EmailAlreadyExistsException;
import com.patient.management.patientservice.mapping.PatientMapper;
import com.patient.management.patientservice.model.Patient;
import com.patient.management.patientservice.repository.PatientRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toPatientResponseDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with the email " + patientRequestDTO.getEmail() + " already exists");
        }

        Patient newPatient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));
        return PatientMapper.toPatientResponseDTO(newPatient);
    }
}
