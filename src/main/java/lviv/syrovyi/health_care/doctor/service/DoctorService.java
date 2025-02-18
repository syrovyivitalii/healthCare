package lviv.syrovyi.health_care.doctor.service;

import lviv.syrovyi.health_care.common.dto.response.PageResponse;
import lviv.syrovyi.health_care.doctor.controller.dto.request.DoctorPatchRequestDTO;
import lviv.syrovyi.health_care.doctor.controller.dto.request.DoctorRequestDTO;
import lviv.syrovyi.health_care.doctor.controller.dto.response.DoctorResponseDTO;
import lviv.syrovyi.health_care.doctor.service.filter.DoctorFilter;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DoctorService {
    PageResponse<DoctorResponseDTO> findAllDoctors (DoctorFilter doctorFilter, Pageable pageable);

    DoctorResponseDTO save (DoctorRequestDTO doctorRequestDTO);

    DoctorResponseDTO patch (UUID doctorId, DoctorPatchRequestDTO doctorPatchRequestDTO);

    boolean existsById(UUID id);
}
