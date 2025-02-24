package lviv.syrovyi.health_care.patient.repository.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PatientProjection {
    UUID getId();
    String getFirstName();
    String getLastName();
    LocalDateTime getStart();
    LocalDateTime getEnd();

    String getDoctorFirstName();
    String getDoctorLastName();
    Integer getTotalPatients();
}

