package lviv.syrovyi.health_care.visit.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class VisitRequestDTO {

    @NotNull(message = "Start time of visit is mandatory")
    private LocalDateTime start;

    @NotNull(message = "End time of visit is mandatory")
    private LocalDateTime end;

    @NotNull(message = "Patient id is mandatory")
    private UUID patientId;

    @NotNull(message = "Doctor id is mandatory")
    private UUID doctorId;

}
