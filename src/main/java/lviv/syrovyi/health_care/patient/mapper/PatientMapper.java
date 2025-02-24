package lviv.syrovyi.health_care.patient.mapper;

import lviv.syrovyi.health_care.patient.controller.dto.request.PatientRequestDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientResponseDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientVisitsResponseDTO;
import lviv.syrovyi.health_care.patient.repository.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient mapToEntity (PatientRequestDTO patientRequestDTO);

    PatientResponseDTO mapToDTO (Patient patient);
}
