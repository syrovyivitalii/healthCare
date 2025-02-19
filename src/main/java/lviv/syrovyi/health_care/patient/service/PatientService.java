package lviv.syrovyi.health_care.patient.service;

import lviv.syrovyi.health_care.common.dto.response.PageResponse;
import lviv.syrovyi.health_care.common.dto.response.PatientResponse;
import lviv.syrovyi.health_care.patient.controller.dto.request.PatientRequestDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientResponseDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientVisitsResponseDTO;
import lviv.syrovyi.health_care.patient.service.filter.PatientFilter;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PatientService {

    PatientResponse<PatientVisitsResponseDTO> findAllPatients(PatientFilter patientFilter, Pageable pageable);

    PatientResponseDTO save (PatientRequestDTO patientRequestDTO);

    boolean existsById(UUID id);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    Optional<UUID> getRandomPatientId();
}
