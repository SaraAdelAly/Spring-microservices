package spring_microservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_microservice.dto.PatientRequestDto;
import spring_microservice.dto.PatientResponseDto;
import spring_microservice.entities.Patient;
import spring_microservice.exceptions.DuplicateException;
import spring_microservice.exceptions.ResourceNotFoundException;
import spring_microservice.mappers.PatientMapper;
import spring_microservice.mappers.PatientRequestMapper;
import spring_microservice.repository.PatientRepo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepo patientRepo;
    private final PatientMapper patientMapper;
    private final PatientRequestMapper patientRequestMapper;


//    public PatientService(PatientRepo patientRepo, PatientMapper patientMapper, PatientRequestMapper patientRequestMapper) {
//        this.patientRepo = patientRepo;
//        this.patientMapper = patientMapper;
//        this.patientRequestMapper = patientRequestMapper;
//    }


    public List<PatientResponseDto> getAllPatients() {
        return patientRepo.findAll().stream().
                map(patientMapper::toDto).
                collect(Collectors.toList());
    }

    public PatientResponseDto findByEmail(String email) {
        return patientMapper.toDto(patientRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("there is no patient with this email %s" + email)));
    }

    public PatientResponseDto updatePatient(UUID id, PatientRequestDto patientRequestDto) {
        Patient existingPatient = patientRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found with this id: %s" + id));

        if (patientRepo.existsByEmail(patientRequestDto.getEmail())) {
            throw new DuplicateException("Patient", "email", patientRequestDto.getEmail());
        }
        existingPatient.setName(patientRequestDto.getName());
        existingPatient.setAddress(patientRequestDto.getAddress());
        existingPatient.setEmail(patientRequestDto.getEmail());
        existingPatient.setDateOfBirth(patientMapper.stringToLocalDate(patientRequestDto.getDateOfBirth()));

        return patientMapper.toDto(patientRepo.save(existingPatient));
    }


    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        if (patientRepo.existsByEmail(patientRequestDto.getEmail())) {
            throw new DuplicateException("patient", "email", patientRequestDto.getEmail());
        }
        Patient patient = patientRepo.save(patientRequestMapper.toEntity(patientRequestDto));
        return patientMapper.toDto(patient);
    }


}
