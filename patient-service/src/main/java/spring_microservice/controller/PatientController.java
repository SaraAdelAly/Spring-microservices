package spring_microservice.controller;

//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring_microservice.dto.PatientRequestDto;
import spring_microservice.dto.PatientResponseDto;
import spring_microservice.dto.validators.CreatePatientValidationGroup;
import spring_microservice.service.PatientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
@Tag(name = "Patient", description = "Api from managing patients")
public class PatientController {

    private final PatientService patientService;
//    public PatientController(PatientService patientService){
//        this.patientService= patientService;
//    }


    @GetMapping("/all")
    @Operation(summary = "Get All system patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/patient")
    public ResponseEntity<PatientResponseDto> getPatientByEmail(String email) {
        return ResponseEntity.ok(patientService.findByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id,
                                                            @RequestBody @Validated({Default.class})
                                                            PatientRequestDto patientRequestDto) {
        return ResponseEntity.ok().body(patientService.updatePatient(id, patientRequestDto));
    }

    @PostMapping("/addPatient")
    public ResponseEntity<PatientResponseDto> addPatient(@Validated({Default.class, CreatePatientValidationGroup.class})
                                                             @RequestBody
                                                             PatientRequestDto patientRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(patientRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id );
        return ResponseEntity.noContent().build();
    }
}
