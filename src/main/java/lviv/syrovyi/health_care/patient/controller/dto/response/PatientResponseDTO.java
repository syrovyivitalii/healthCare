package lviv.syrovyi.health_care.patient.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lviv.syrovyi.health_care.visit.controller.dto.response.VisitResponseDTO;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private List<VisitResponseDTO> lastVisits;
}
