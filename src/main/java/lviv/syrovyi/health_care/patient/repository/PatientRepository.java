package lviv.syrovyi.health_care.patient.repository;

import lviv.syrovyi.health_care.patient.repository.entity.Patient;
import lviv.syrovyi.health_care.patient.repository.projection.PatientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PatientRepository extends JpaRepository <Patient, UUID>, JpaSpecificationExecutor<Patient> {
    boolean existsById(UUID id);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT p.id FROM Patient p")
    List<UUID> findAllPatientIds();

    @Query("""
        SELECT p.id AS id,
               p.firstName AS firstName,
               p.lastName AS lastName,
               v.start, CONVERT_TZ(v.start, 'UTC', d.timezone) AS start,
               v.end, CONVERT_TZ(v.end, 'UTC', d.timezone) AS end,
               d.firstName AS doctorFirstName,
               d.lastName AS doctorLastName,
               (SELECT COUNT(DISTINCT v2.patient.id) 
                FROM Visit v2 
                WHERE v2.doctor.id = d.id) AS totalPatients
        FROM Patient p
        LEFT JOIN p.lastVisits v
        LEFT JOIN v.doctor d
        WHERE v.start = (
            SELECT MAX(v2.start)
            FROM Visit v2
            WHERE v2.patient.id = p.id AND v2.doctor.id = d.id
        )
        AND (:search IS NULL OR p.firstName LIKE %:search%)
        AND (:doctorIds IS NULL OR d.id IN :doctorIds)
        """)
    Page<PatientProjection> findAllPatientsWithLastVisits(
            @Param("search") String search,
            @Param("doctorIds") Set<UUID> doctorIds,
            Pageable pageable
    );
}
