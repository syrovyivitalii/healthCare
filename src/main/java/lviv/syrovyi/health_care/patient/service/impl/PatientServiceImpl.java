package lviv.syrovyi.health_care.patient.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lviv.syrovyi.health_care.common.dto.response.PageResponse;
import lviv.syrovyi.health_care.patient.controller.dto.request.PatientRequestDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientResponseDTO;
import lviv.syrovyi.health_care.patient.mapper.PatientMapper;
import lviv.syrovyi.health_care.patient.repository.PatientRepository;
import lviv.syrovyi.health_care.patient.repository.entity.Patient;
import lviv.syrovyi.health_care.patient.service.PatientService;
import lviv.syrovyi.health_care.patient.service.filter.PatientFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static lviv.syrovyi.health_care.common.specification.SpecificationCustom.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public PageResponse<PatientResponseDTO> findAllPatients(PatientFilter patientFilter, Pageable pageable) {
        Page <Patient> allPatients = patientRepository.findAll(getSearchSpecification(patientFilter), pageable);

        List<PatientResponseDTO> collectedDTOs = allPatients
                .stream()
                .map(patientMapper::mapToDTO)
                .toList();

        return PageResponse.<PatientResponseDTO>builder()
                .totalPages((long) allPatients.getTotalPages())
                .pageSize((long) pageable.getPageSize())
                .totalElements(allPatients.getTotalElements())
                .content(collectedDTOs)
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

    private Specification<Patient> getSearchSpecification(PatientFilter patientFilter) {
        return Specification.where((Specification<Patient>) searchLikeString("firstName", patientFilter.getSearch()))
                .or((Specification<Patient>) searchLikeString("lastName", patientFilter.getSearch()));
    }
}
