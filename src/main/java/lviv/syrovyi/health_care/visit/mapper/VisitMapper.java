package lviv.syrovyi.health_care.visit.mapper;

import lviv.syrovyi.health_care.visit.controller.dto.request.VisitRequestDTO;
import lviv.syrovyi.health_care.visit.controller.dto.response.VisitResponseDTO;
import lviv.syrovyi.health_care.visit.repository.impl.Visit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    Visit mapToEntity (VisitRequestDTO visitRequestDTO);

    VisitResponseDTO mapToDTO (Visit visit);
}
