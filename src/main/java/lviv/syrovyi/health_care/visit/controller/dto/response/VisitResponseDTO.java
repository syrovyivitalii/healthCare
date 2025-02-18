package lviv.syrovyi.health_care.visit.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitResponseDTO {

    private UUID id;
    private LocalDateTime start;
    private LocalDateTime end;
    private UUID patientId;
    private UUID doctorId;

}
