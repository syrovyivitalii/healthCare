package lviv.syrovyi.health_care.visit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public VisitResponseDTO save(VisitRequestDTO visitRequestDTO){
        Visit visit = visitMapper.mapToEntity(visitRequestDTO);

        visitRepository.save(visit);

        return visitMapper.mapToDTO(visit);
    }
}
