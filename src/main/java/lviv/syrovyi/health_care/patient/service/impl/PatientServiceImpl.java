package lviv.syrovyi.health_care.patient.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lviv.syrovyi.health_care.common.dto.response.PatientResponse;
import lviv.syrovyi.health_care.common.service.timezone.TimezoneService;
import lviv.syrovyi.health_care.doctor.controller.dto.response.DoctorResponseDTO;
import lviv.syrovyi.health_care.doctor.repository.DoctorRepository;
import lviv.syrovyi.health_care.doctor.repository.entity.Doctor;
import lviv.syrovyi.health_care.patient.controller.dto.request.PatientRequestDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientResponseDTO;
import lviv.syrovyi.health_care.patient.mapper.PatientMapper;
import lviv.syrovyi.health_care.patient.repository.PatientRepository;
import lviv.syrovyi.health_care.patient.repository.entity.Patient;
import lviv.syrovyi.health_care.patient.service.PatientService;
import lviv.syrovyi.health_care.patient.service.filter.PatientFilter;
import lviv.syrovyi.health_care.visit.controller.dto.response.VisitResponseDTO;
import lviv.syrovyi.health_care.visit.repository.VisitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static lviv.syrovyi.health_care.common.specification.SpecificationCustom.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final VisitRepository visitRepository;
    private final TimezoneService timezoneService;
    private final DoctorRepository doctorRepository;

    @Override
    public PatientResponse<PatientResponseDTO> findAllPatients(PatientFilter patientFilter, Pageable pageable) {
        Page<Patient> allPatients = patientRepository.findAll(getSearchSpecification(patientFilter), pageable);

        List<PatientResponseDTO> collectedDTOs = allPatients
                .stream()
                .map(patient -> {
                    PatientResponseDTO patientResponseDTO = patientMapper.mapToDTO(patient);

                    Map<String, VisitResponseDTO> lastVisitPerDoctor = patientResponseDTO.getLastVisits()
                            .stream()
                            .collect(Collectors.toMap(
                                    visit -> visit.getDoctor().getFirstName() + " " + visit.getDoctor().getLastName(),
                                    visit -> visit,
                                    (existing, replacement) -> existing.getStart().isAfter(replacement.getStart()) ? existing : replacement
                            ));

                    List<VisitResponseDTO> lastVisits = getVisits(lastVisitPerDoctor);

                    patientResponseDTO.setLastVisits(lastVisits);
                    return patientResponseDTO;
                })
                .collect(Collectors.toList());

        return PatientResponse.<PatientResponseDTO>builder()
                .data(collectedDTOs)
                .count(allPatients.getTotalElements())
                .build();
    }

    private List<VisitResponseDTO> getVisits(Map<String, VisitResponseDTO> lastVisitPerDoctor) {
        List<VisitResponseDTO> lastVisits = new ArrayList<>(lastVisitPerDoctor.values());

        lastVisits.forEach(visit -> {
            DoctorResponseDTO doctorDTO = visit.getDoctor();
            int totalPatients = visitRepository.countPatientsByDoctorName(doctorDTO.getFirstName(), doctorDTO.getLastName());
            doctorDTO.setTotalPatients(totalPatients);

            Doctor doctor = doctorRepository.findByFirstNameAndLastName(doctorDTO.getFirstName(), doctorDTO.getLastName());

            visit.setStart(timezoneService.convertToTimezone(visit.getStart(), doctor.getTimezone()));
            visit.setEnd(timezoneService.convertToTimezone(visit.getEnd(), doctor.getTimezone()));
        });
        return lastVisits;
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
        List<Patient> allPatients = patientRepository.findAll();

        if (allPatients.isEmpty()) {
            return Optional.empty();
        }

        Random random = new Random();

        Patient patient = allPatients.get(random.nextInt(allPatients.size()));

        return Optional.of(patient.getId());
    }

    private Specification<Patient> getSearchSpecification(PatientFilter patientFilter) {
        return Specification.where((Specification<Patient>) searchLikeString("lastName", patientFilter.getSearch()))
                .and((Specification<Patient>) searchFieldInCollectionOfJoinedIds("lastVisits", "doctorId", patientFilter.getDoctorIds()));
    }
}
