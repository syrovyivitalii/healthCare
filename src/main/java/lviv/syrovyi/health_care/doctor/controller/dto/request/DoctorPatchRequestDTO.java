package lviv.syrovyi.health_care.doctor.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorPatchRequestDTO {

    @NotBlank(message = "Timezone is mandatory")
    private String timezone;

}
