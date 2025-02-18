package lviv.syrovyi.health_care.patient.repository;

import lviv.syrovyi.health_care.patient.repository.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PatientRepository extends JpaRepository <Patient, UUID>, JpaSpecificationExecutor<Patient> {
}
