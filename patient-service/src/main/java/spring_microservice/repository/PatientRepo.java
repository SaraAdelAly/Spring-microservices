package spring_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_microservice.entities.Patient;

import java.util.Optional;
import java.util.UUID;

//@Repository
public interface PatientRepo extends JpaRepository<Patient,UUID> {
    Optional<Patient> findByEmail(String email);
    boolean existsByEmail(String email);
}
