package lviv.syrovyi.health_care.doctor.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String timezone;
}
