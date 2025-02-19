package lviv.syrovyi.health_care.common.dataloader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lviv.syrovyi.health_care.patient.controller.dto.request.PatientRequestDTO;
import lviv.syrovyi.health_care.patient.service.PatientService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatientLoader implements Consumer<List<Map<String, Object>>> {

    private final PatientService patientService;

    @Override
    public void accept(List<Map<String, Object>> maps) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        maps.stream().filter(x -> x.containsKey("patients"))
                .forEach(x ->
                        ((List<HashMap>) x.get("patients")).forEach(y -> {
                            PatientRequestDTO patientRequestDTO = mapper.convertValue(y, PatientRequestDTO.class);
                            if (!patientService.existsByFirstNameAndLastName(patientRequestDTO.getFirstName(), patientRequestDTO.getLastName())) {
                                patientService.save(patientRequestDTO);
                            }
                        })
                );
    }
}
