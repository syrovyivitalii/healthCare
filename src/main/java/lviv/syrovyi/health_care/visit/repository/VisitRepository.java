package lviv.syrovyi.health_care.visit.repository;

import lviv.syrovyi.health_care.visit.repository.impl.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface VisitRepository extends JpaRepository<Visit, UUID> {
    @Query("""
        SELECT COUNT(v) > 0 FROM Visit v 
        WHERE v.doctor.id = :doctorId 
        AND (
            :start BETWEEN v.start AND v.end 
            OR :end BETWEEN v.start AND v.end
            OR v.start BETWEEN :start AND :end
        )
    """)
    boolean existsOverlappingVisit(@Param("doctorId") UUID doctorId,
                                   @Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);
}
