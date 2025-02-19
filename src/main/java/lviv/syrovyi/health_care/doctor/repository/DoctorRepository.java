package lviv.syrovyi.health_care.doctor.repository;

import lviv.syrovyi.health_care.doctor.repository.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID>, JpaSpecificationExecutor<Doctor> {

    Doctor findByFirstNameAndLastName (String firstName, String lastName);

    boolean existsByFirstNameAndLastName (String firstName, String lastName);

    @Query("SELECT d.id FROM Doctor d")
    List<UUID> findAllDoctorIds();

}
