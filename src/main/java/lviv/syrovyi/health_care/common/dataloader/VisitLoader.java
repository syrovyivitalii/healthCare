package lviv.syrovyi.health_care.common.dataloader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lviv.syrovyi.health_care.doctor.repository.DoctorRepository;
import lviv.syrovyi.health_care.doctor.repository.entity.Doctor;
import lviv.syrovyi.health_care.doctor.service.DoctorService;
import lviv.syrovyi.health_care.patient.service.PatientService;
import lviv.syrovyi.health_care.visit.controller.dto.request.VisitRequestDTO;
import lviv.syrovyi.health_care.visit.mapper.VisitMapper;
import lviv.syrovyi.health_care.visit.repository.VisitRepository;
import lviv.syrovyi.health_care.visit.repository.impl.Visit;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class VisitLoader implements Consumer<List<Map<String, Object>>> {
    private final DoctorService doctorService;
    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final DoctorRepository doctorRepository;
    private final PatientService patientService;

    @Override
    public void accept(List<Map<String, Object>> maps) {
        ObjectMapper mapper = createObjectMapper();

        maps.stream()
                .filter(x -> x.containsKey("visits"))
                .map(x -> (List<HashMap>) x.get("visits"))
                .flatMap(Collection::stream)
                .forEach(y -> processVisit(y, mapper));
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    private void processVisit(HashMap<String, Object> y, ObjectMapper mapper) {
        VisitRequestDTO visitRequestDTO = mapper.convertValue(y, VisitRequestDTO.class);
        assignDoctorAndPatient(visitRequestDTO);

        Optional<Doctor> doctor = doctorRepository.findById(visitRequestDTO.getDoctorId());
        doctor.ifPresent(doc -> saveVisitIfNotOverlapping(visitRequestDTO, doc));
    }

    private void assignDoctorAndPatient(VisitRequestDTO visitRequestDTO) {
        if (visitRequestDTO.getDoctorId() == null) {
            doctorService.getRandomDoctorId().ifPresentOrElse(
                    visitRequestDTO::setDoctorId,
                    () -> log.error("No doctor available to assign to visit")
            );
        }

        if (visitRequestDTO.getPatientId() == null) {
            patientService.getRandomPatientId().ifPresentOrElse(
                    visitRequestDTO::setPatientId,
                    () -> log.error("No patient available to assign to visit")
            );
        }
    }

    private void saveVisitIfNotOverlapping(VisitRequestDTO visitRequestDTO, Doctor doctor) {
        Visit visit = visitMapper.mapToEntity(visitRequestDTO, doctor);
        if (!visitRepository.existsOverlappingVisit(doctor.getId(), visit.getStart(), visit.getEnd())) {
            visitRepository.save(visit);
        }
    }

}
