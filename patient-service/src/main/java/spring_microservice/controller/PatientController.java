package spring_microservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring_microservice.dto.PatientRequestDto;
import spring_microservice.dto.PatientResponseDto;
import spring_microservice.service.PatientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
//    public PatientController(PatientService patientService){
//        this.patientService= patientService;
//    }


    @GetMapping("/all")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(){
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    @GetMapping("/patient")
    public  ResponseEntity<PatientResponseDto> getPatientByEmail (String email){
        return ResponseEntity.ok(patientService.findByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient (@PathVariable UUID id,
                                                             @RequestBody @Valid PatientRequestDto patientRequestDto ){
        return ResponseEntity.ok().body(patientService.updatePatient(id,patientRequestDto));
    }

    @PostMapping("/addPatient")
    public ResponseEntity<PatientResponseDto> addPatient(@Valid @RequestBody PatientRequestDto patientRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(patientRequestDto));
    }


}
