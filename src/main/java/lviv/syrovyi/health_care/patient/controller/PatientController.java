package lviv.syrovyi.health_care.patient.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientResponse;
import lviv.syrovyi.health_care.patient.controller.dto.request.PatientRequestDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientResponseDTO;
import lviv.syrovyi.health_care.patient.controller.dto.response.PatientVisitsResponseDTO;
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
    public ResponseEntity<PatientResponse<PatientVisitsResponseDTO>> getAllPatients (@ParameterObject PatientFilter playerFilter,
                                                                               @SortDefault(sort = "lastName", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable){
        PatientResponse<PatientVisitsResponseDTO> allPatients = patientService.findAllPatients(playerFilter, pageable);

        return ResponseEntity.ok(allPatients);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> save (@Valid @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.save(patientRequestDTO);

        return ResponseEntity.ok(patientResponseDTO);
    }
}
