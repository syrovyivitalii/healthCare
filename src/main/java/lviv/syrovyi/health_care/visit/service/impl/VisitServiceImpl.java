package lviv.syrovyi.health_care.visit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lviv.syrovyi.health_care.common.exception.ClientBackendException;
import lviv.syrovyi.health_care.common.exception.ErrorCode;
import lviv.syrovyi.health_care.common.util.timezone.TimezoneService;
import lviv.syrovyi.health_care.doctor.repository.DoctorRepository;
import lviv.syrovyi.health_care.doctor.repository.entity.Doctor;
import lviv.syrovyi.health_care.patient.service.PatientService;
import lviv.syrovyi.health_care.visit.controller.dto.request.VisitRequestDTO;
import lviv.syrovyi.health_care.visit.controller.dto.response.VisitResponseDTO;
import lviv.syrovyi.health_care.visit.mapper.VisitMapper;
import lviv.syrovyi.health_care.visit.repository.VisitRepository;
import lviv.syrovyi.health_care.visit.repository.impl.Visit;
import lviv.syrovyi.health_care.visit.service.VisitService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final PatientService patientService;
    private final DoctorRepository doctorRepository;
    private final TimezoneService timezoneService;

    @Override
    public VisitResponseDTO save(VisitRequestDTO visitRequestDTO){

        log.info("Creating visit: {}", visitRequestDTO);
        
        boolean patientExistence = patientService.existsById(visitRequestDTO.getPatientId());

        if (!patientExistence){
            throw new ClientBackendException(ErrorCode.PATIENT_NOT_FOUND);
        }

        Doctor doctor = doctorRepository.findById(visitRequestDTO.getDoctorId())
                .orElseThrow(() -> new ClientBackendException(ErrorCode.DOCTOR_NOT_FOUND));

        Visit visit = visitMapper.mapToEntity(visitRequestDTO);

        LocalDateTime startVisitUTC = timezoneService.convertToDoctorTimezone(visit.getStart(), doctor.getTimezone());

        LocalDateTime endVisitUTC = timezoneService.convertToDoctorTimezone(visit.getEnd(), doctor.getTimezone());

        boolean existsOverlappingVisit = visitRepository.existsOverlappingVisit(visit.getDoctorId(), startVisitUTC, endVisitUTC);

        if (existsOverlappingVisit){
            throw new ClientBackendException(ErrorCode.OVERLAPPING_VISIT);
        }

        visit.setStart(startVisitUTC);
        visit.setEnd(endVisitUTC);
        visitRepository.save(visit);

        log.info("Visit created successfully for patient {} with doctor {}",
                visitRequestDTO.getPatientId(), visitRequestDTO.getDoctorId());

        return visitMapper.mapToDTO(visit);
    }
}
