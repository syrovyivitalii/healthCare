package lviv.syrovyi.health_care.patient.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lviv.syrovyi.health_care.common.dto.response.PageResponse;
import lviv.syrovyi.health_care.patient.controller.dto.request.PatientRequestDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientResponseDTO;
import lviv.syrovyi.health_care.patient.service.PatientService;
import lviv.syrovyi.health_care.patient.service.filter.PatientFilter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/pageable")
    public ResponseEntity<PageResponse<PatientResponseDTO>> getAllPatients (@ParameterObject PatientFilter playerFilter,
                                                                           @SortDefault(sort = "lastName", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable){
        PageResponse<PatientResponseDTO> allPatients = patientService.findAllPatients(playerFilter, pageable);

        return ResponseEntity.ok(allPatients);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> save (@Valid @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.save(patientRequestDTO);

        return ResponseEntity.ok(patientResponseDTO);
    }
}
