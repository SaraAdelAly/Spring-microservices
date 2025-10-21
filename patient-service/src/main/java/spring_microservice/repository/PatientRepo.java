package spring_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_microservice.entities.Patient;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepo extends JpaRepository<Patient,UUID> {
    Optional<Patient> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot (String email, UUID id);
}
