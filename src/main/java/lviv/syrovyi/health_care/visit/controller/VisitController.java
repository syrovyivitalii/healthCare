package lviv.syrovyi.health_care.visit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lviv.syrovyi.health_care.visit.controller.dto.request.VisitRequestDTO;
import lviv.syrovyi.health_care.visit.controller.dto.response.VisitResponseDTO;
import lviv.syrovyi.health_care.visit.service.VisitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/visits")
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    public ResponseEntity<VisitResponseDTO> save (@Valid @RequestBody VisitRequestDTO visitRequestDTO) {
        VisitResponseDTO saved = visitService.save(visitRequestDTO);

        return ResponseEntity.ok(saved);
    }
}
