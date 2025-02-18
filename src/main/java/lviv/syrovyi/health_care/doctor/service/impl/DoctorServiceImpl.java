package lviv.syrovyi.health_care.doctor.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lviv.syrovyi.health_care.common.dto.response.PageResponse;
import lviv.syrovyi.health_care.common.exception.ClientBackendException;
import lviv.syrovyi.health_care.common.exception.ErrorCode;
import lviv.syrovyi.health_care.doctor.controller.dto.request.DoctorPatchRequestDTO;
import lviv.syrovyi.health_care.doctor.controller.dto.request.DoctorRequestDTO;
import lviv.syrovyi.health_care.doctor.controller.dto.response.DoctorResponseDTO;
import lviv.syrovyi.health_care.doctor.mapper.DoctorMapper;
import lviv.syrovyi.health_care.doctor.repository.DoctorRepository;
import lviv.syrovyi.health_care.doctor.repository.entity.Doctor;
import lviv.syrovyi.health_care.doctor.service.DoctorService;
import lviv.syrovyi.health_care.doctor.service.filter.DoctorFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static lviv.syrovyi.health_care.common.specification.SpecificationCustom.searchLikeString;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public PageResponse<DoctorResponseDTO> findAllDoctors(DoctorFilter doctorFilter, Pageable pageable) {
        Page<Doctor> allPatients = doctorRepository.findAll(getSearchSpecification(doctorFilter), pageable);

        List<DoctorResponseDTO> collectedDTOs = allPatients
                .stream()
                .map(doctorMapper::mapToDTO)
                .toList();

        return PageResponse.<DoctorResponseDTO>builder()
                .totalPages((long) allPatients.getTotalPages())
                .pageSize((long) pageable.getPageSize())
                .totalElements(allPatients.getTotalElements())
                .content(collectedDTOs)
                .build();

    }

    @Override
    public DoctorResponseDTO save(DoctorRequestDTO doctorRequestDTO){
        Doctor doctor = doctorMapper.mapToEntity(doctorRequestDTO);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.mapToDTO(savedDoctor);
    }

    @Override
    @Transactional
    public DoctorResponseDTO patch(UUID doctorId, DoctorPatchRequestDTO doctorPatchRequestDTO){
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ClientBackendException(ErrorCode.DOCTOR_NOT_FOUND));

        doctorMapper.patchMerge(doctorPatchRequestDTO, doctor);

        return doctorMapper.mapToDTO(doctor);
    }

    private Specification<Doctor> getSearchSpecification(DoctorFilter doctorFilter) {
        return Specification.where((Specification<Doctor>) searchLikeString("firstName", doctorFilter.getSearch()))
                .or((Specification<Doctor>) searchLikeString("lastName", doctorFilter.getSearch()));
    }
}
