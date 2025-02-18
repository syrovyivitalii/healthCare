package lviv.syrovyi.health_care.visit.service;

import lviv.syrovyi.health_care.visit.controller.dto.request.VisitRequestDTO;
import lviv.syrovyi.health_care.visit.controller.dto.response.VisitResponseDTO;

public interface VisitService {
    VisitResponseDTO save (VisitRequestDTO visitRequestDTO);
}
