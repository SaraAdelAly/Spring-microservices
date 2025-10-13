package spring_microservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_microservice.dto.PatientRequestDto;
import spring_microservice.dto.PatientResponseDto;
import spring_microservice.entities.Patient;
import spring_microservice.exceptions.DuplicateException;
import spring_microservice.mappers.PatientMapper;
import spring_microservice.mappers.PatientRequestMapper;
import spring_microservice.repository.PatientRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepo patientRepo;
    private final PatientMapper patientMapper;

//    public PatientService(PatientRepo patientRepo, PatientMapper patientMapper, PatientRequestMapper patientRequestMapper) {
//        this.patientRepo = patientRepo;
//        this.patientMapper = patientMapper;
//        this.patientRequestMapper = patientRequestMapper;
//    }

    private final PatientRequestMapper patientRequestMapper;

    public List<PatientResponseDto> getAllPatients() {
        return patientRepo.findAll().stream().
                map(patientMapper::toDto).
                collect(Collectors.toList());
    }

    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto){
        if (patientRepo.existsByEmail(patientRequestDto.getEmail())){
            throw new DuplicateException(
                    "patient with this email " + "already exists"
                            + patientRequestDto.getEmail());
        }
        Patient patient = patientRepo.save(patientRequestMapper.toEntity(patientRequestDto));
        return patientMapper.toDto(patient);
    }





}
