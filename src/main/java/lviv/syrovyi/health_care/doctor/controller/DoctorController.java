package lviv.syrovyi.health_care.doctor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lviv.syrovyi.health_care.common.dto.response.PageResponse;
import lviv.syrovyi.health_care.doctor.controller.dto.request.DoctorPatchRequestDTO;
import lviv.syrovyi.health_care.doctor.controller.dto.request.DoctorRequestDTO;
import lviv.syrovyi.health_care.doctor.controller.dto.response.DoctorResponseDTO;
import lviv.syrovyi.health_care.doctor.service.DoctorService;
import lviv.syrovyi.health_care.doctor.service.filter.DoctorFilter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/pageable")
    public ResponseEntity<PageResponse<DoctorResponseDTO>> getAllDoctors(@ParameterObject DoctorFilter doctorFilter,
                                                                         @SortDefault(sort = "lastName", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        PageResponse<DoctorResponseDTO> allDoctors = doctorService.findAllDoctors(doctorFilter, pageable);

        return ResponseEntity.ok(allDoctors);
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> save (@Valid @RequestBody DoctorRequestDTO doctorRequestDTO) {
        DoctorResponseDTO savedDoctor = doctorService.save(doctorRequestDTO);

        return ResponseEntity.ok(savedDoctor);
    }

    @PatchMapping("/{doctorId}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@PathVariable UUID doctorId,
                                                          @Valid @RequestBody DoctorPatchRequestDTO doctorPatchRequestDTO) {
        DoctorResponseDTO patched = doctorService.patch(doctorId, doctorPatchRequestDTO);

        return ResponseEntity.ok(patched);
    }
}
