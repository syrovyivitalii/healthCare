package lviv.syrovyi.health_care.doctor.repository;

import lviv.syrovyi.health_care.doctor.repository.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.print.Doc;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID>, JpaSpecificationExecutor<Doctor> {

    Doctor findByFirstNameAndLastName (String firstName, String lastName);

    boolean existsByFirstNameAndLastName (String firstName, String lastName);
}
