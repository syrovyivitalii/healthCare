package lviv.syrovyi.health_care.visit.repository;

import lviv.syrovyi.health_care.visit.repository.impl.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VisitRepository extends JpaRepository<Visit, UUID> {
}
