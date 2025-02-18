package lviv.syrovyi.health_care.visit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lviv.syrovyi.health_care.common.exception.ClientBackendException;
import lviv.syrovyi.health_care.common.exception.ErrorCode;
import lviv.syrovyi.health_care.doctor.service.DoctorService;
import lviv.syrovyi.health_care.patient.service.PatientService;
import lviv.syrovyi.health_care.visit.controller.dto.request.VisitRequestDTO;
import lviv.syrovyi.health_care.visit.controller.dto.response.VisitResponseDTO;
import lviv.syrovyi.health_care.visit.mapper.VisitMapper;
import lviv.syrovyi.health_care.visit.repository.VisitRepository;
import lviv.syrovyi.health_care.visit.repository.impl.Visit;
import lviv.syrovyi.health_care.visit.service.VisitService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Override
    public VisitResponseDTO save(VisitRequestDTO visitRequestDTO){
        boolean doctorExistence = doctorService.existsById(visitRequestDTO.getDoctorId());

        if (!doctorExistence){
            throw new ClientBackendException(ErrorCode.DOCTOR_NOT_FOUND);
        }

        boolean patientExistence = patientService.existsById(visitRequestDTO.getPatientId());

        if (!patientExistence){
            throw new ClientBackendException(ErrorCode.PATIENT_NOT_FOUND);
        }

        Visit visit = visitMapper.mapToEntity(visitRequestDTO);

        visitRepository.save(visit);

        return visitMapper.mapToDTO(visit);
    }
}
