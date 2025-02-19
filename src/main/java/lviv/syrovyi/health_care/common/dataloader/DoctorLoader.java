package lviv.syrovyi.health_care.common.dataloader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lviv.syrovyi.health_care.doctor.controller.dto.request.DoctorRequestDTO;
import lviv.syrovyi.health_care.doctor.service.DoctorService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class DoctorLoader implements Consumer<List<Map<String, Object>>> {

    private final DoctorService doctorService;

    @Override
    public void accept(List<Map<String, Object>> maps) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        maps.stream().filter(x -> x.containsKey("doctors"))
                .forEach(x ->
                        ((List<HashMap>) x.get("doctors")).forEach(y -> {
                            DoctorRequestDTO doctorRequestDTO = mapper.convertValue(y, DoctorRequestDTO.class);
                            if (!doctorService.existsByFirstNameAndLastName(doctorRequestDTO.getFirstName(), doctorRequestDTO.getLastName())) {
                                doctorService.save(doctorRequestDTO);
                            }
                        })
                );
    }
}