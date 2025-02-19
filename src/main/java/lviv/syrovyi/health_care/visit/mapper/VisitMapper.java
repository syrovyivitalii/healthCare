package lviv.syrovyi.health_care.visit.mapper;

import lviv.syrovyi.health_care.doctor.repository.entity.Doctor;
import lviv.syrovyi.health_care.visit.controller.dto.request.VisitRequestDTO;
import lviv.syrovyi.health_care.visit.controller.dto.response.VisitResponseDTO;
import lviv.syrovyi.health_care.visit.repository.impl.Visit;
import lviv.syrovyi.health_care.common.service.timezone.TimezoneService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class VisitMapper {

    @Autowired
    protected TimezoneService timezoneService;

    @Mapping(target = "start", expression = "java(convertStartToUtc(visitRequestDTO, doctor))")
    @Mapping(target = "end", expression = "java(convertEndToUtc(visitRequestDTO, doctor))")
    public abstract Visit mapToEntity(VisitRequestDTO visitRequestDTO, Doctor doctor);

    @Mapping(target = "start", expression = "java(convertStartToTimezone(visit))")
    @Mapping(target = "end", expression = "java(convertEndToTimezone(visit))")
    public abstract VisitResponseDTO mapToDTO(Visit visit);

    public LocalDateTime convertStartToUtc(VisitRequestDTO visitRequestDTO, Doctor doctor) {
        return timezoneService.convertToUtc(visitRequestDTO.getStart(), doctor.getTimezone());
    }

    public LocalDateTime convertEndToUtc(VisitRequestDTO visitRequestDTO, Doctor doctor) {
        return timezoneService.convertToUtc(visitRequestDTO.getEnd(), doctor.getTimezone());
    }

    public LocalDateTime convertStartToTimezone(Visit visit) {
        return timezoneService.convertToTimezone(visit.getStart(), visit.getDoctor().getTimezone());
    }

    public LocalDateTime convertEndToTimezone(Visit visit) {
        return timezoneService.convertToTimezone(visit.getEnd(), visit.getDoctor().getTimezone());
    }
}
