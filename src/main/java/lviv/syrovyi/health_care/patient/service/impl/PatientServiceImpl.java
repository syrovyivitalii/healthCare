package lviv.syrovyi.health_care.patient.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientResponse;
import lviv.syrovyi.health_care.doctor.controller.dto.response.DoctorOfPatientResponseDTO;
import lviv.syrovyi.health_care.patient.controller.dto.request.PatientRequestDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientResponseDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientVisitsResponseDTO;
import lviv.syrovyi.health_care.patient.mapper.PatientMapper;
import lviv.syrovyi.health_care.patient.repository.PatientRepository;
import lviv.syrovyi.health_care.patient.repository.entity.Patient;
import lviv.syrovyi.health_care.patient.repository.projection.PatientProjection;
import lviv.syrovyi.health_care.patient.service.PatientService;
import lviv.syrovyi.health_care.patient.service.filter.PatientFilter;
import lviv.syrovyi.health_care.visit.controller.dto.response.VisitResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    @Override
    public PatientResponse<PatientVisitsResponseDTO> findAllPatients(PatientFilter patientFilter, Pageable pageable) {
        String search = patientFilter.getSearch();
        Set<UUID> doctorIds = patientFilter.getDoctorIds();

        Page<PatientProjection> allPatients = patientRepository.findAllPatientsWithLastVisits(
                search,
                doctorIds,
                pageable
        );

        Map<UUID, List<PatientProjection>> patientsGroupedById = allPatients.stream()
                .collect(Collectors.groupingBy(PatientProjection::getId));

        List<PatientVisitsResponseDTO> collectedDTOs = patientsGroupedById.values().stream()
                .map(patientProjections -> {
                    PatientProjection firstPatient = patientProjections.get(0);
                    List<VisitResponseDTO> visits = patientProjections.stream()
                            .map(patient -> VisitResponseDTO.builder()
                                    .start(patient.getStart())
                                    .end(patient.getEnd())
                                    .doctor(new DoctorOfPatientResponseDTO(
                                            patient.getDoctorFirstName(),
                                            patient.getDoctorLastName(),
                                            patient.getTotalPatients()
                                    ))
                                    .build())
                            .collect(Collectors.toList());

                    return PatientVisitsResponseDTO.builder()
                            .firstName(firstPatient.getFirstName())
                            .lastName(firstPatient.getLastName())
                            .lastVisits(visits)
                            .build();
                })
                .collect(Collectors.toList());

        return PatientResponse.<PatientVisitsResponseDTO>builder()
                .data(collectedDTOs)
                .count(allPatients.getTotalElements())
                .build();
    }

    @Override
    public PatientResponseDTO save(PatientRequestDTO patientRequestDTO){
        Patient patient = patientMapper.mapToEntity(patientRequestDTO);

        patientRepository.save(patient);

        return patientMapper.mapToDTO(patient);
    }

    @Override
    public boolean existsById(UUID id){
        return patientRepository.existsById(id);
    }

    @Override
    public boolean existsByFirstNameAndLastName(String firstName, String lastName){
        return patientRepository.existsByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Optional<UUID> getRandomPatientId() {
        List<UUID> patientIds = patientRepository.findAllPatientIds();

        if (patientIds.isEmpty()) {
            return Optional.empty();
        }

        Random random = new Random();
        return Optional.of(patientIds.get(random.nextInt(patientIds.size())));
    }
}
