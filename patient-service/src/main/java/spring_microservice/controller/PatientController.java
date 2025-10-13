package spring_microservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring_microservice.dto.PatientRequestDto;
import spring_microservice.dto.PatientResponseDto;
import spring_microservice.service.PatientService;

import java.util.List;

@RestController
//@RequiredArgsConstructor
//@AllArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    public PatientController(PatientService patientService){
        this.patientService= patientService;
    }


    @GetMapping("/all")
//    @GetMapping
    public List<PatientResponseDto> getAllPatients(){
        return patientService.getAllPatients();
    }

    @PostMapping("/addPatient")
    public PatientResponseDto addPatient(@Valid @RequestBody PatientRequestDto patientRequestDto){
        return patientService.createPatient(patientRequestDto);
    }
}
