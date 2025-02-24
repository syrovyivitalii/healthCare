package lviv.syrovyi.health_care.doctor.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorOfPatientResponseDTO {
    private String firstName;
    private String lastName;
    private Integer totalPatients;
}
