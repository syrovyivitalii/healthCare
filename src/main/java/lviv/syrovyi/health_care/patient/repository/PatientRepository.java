package lviv.syrovyi.health_care.patient.repository;

import lviv.syrovyi.health_care.patient.repository.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PatientRepository extends JpaRepository <Patient, UUID>, JpaSpecificationExecutor<Patient> {
    boolean existsById(UUID id);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT p.id FROM Patient p")
    List<UUID> findAllPatientIds();
}
