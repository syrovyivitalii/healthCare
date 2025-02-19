package lviv.syrovyi.health_care.visit.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lviv.syrovyi.health_care.doctor.controller.dto.response.DoctorOfPatientResponseDTO;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitResponseDTO {

    private LocalDateTime start;
    private LocalDateTime end;
    private DoctorOfPatientResponseDTO doctor;
}
