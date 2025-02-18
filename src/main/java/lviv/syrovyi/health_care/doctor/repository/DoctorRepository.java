package lviv.syrovyi.health_care.doctor.repository;

import lviv.syrovyi.health_care.doctor.repository.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID>, JpaSpecificationExecutor<Doctor> {
}
