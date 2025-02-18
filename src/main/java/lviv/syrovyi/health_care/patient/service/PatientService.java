package lviv.syrovyi.health_care.patient.service;

import lviv.syrovyi.health_care.common.dto.response.PageResponse;
import lviv.syrovyi.health_care.patient.controller.dto.request.PatientRequestDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientResponseDTO;
import lviv.syrovyi.health_care.patient.service.filter.PatientFilter;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    PageResponse<PatientResponseDTO> findAllPatients(PatientFilter patientFilter, Pageable pageable);

    PatientResponseDTO save (PatientRequestDTO patientRequestDTO);
}
