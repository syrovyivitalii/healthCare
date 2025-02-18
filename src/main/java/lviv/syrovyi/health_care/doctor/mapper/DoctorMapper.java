package lviv.syrovyi.health_care.doctor.mapper;

import lviv.syrovyi.health_care.doctor.controller.dto.request.DoctorPatchRequestDTO;
import lviv.syrovyi.health_care.doctor.controller.dto.request.DoctorRequestDTO;
import lviv.syrovyi.health_care.doctor.controller.dto.response.DoctorResponseDTO;
import lviv.syrovyi.health_care.doctor.repository.entity.Doctor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor mapToEntity (DoctorRequestDTO doctorRequestDTO);

    DoctorResponseDTO mapToDTO (Doctor doctor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchMerge(DoctorPatchRequestDTO doctorPatchRequestDTO, @MappingTarget Doctor doctor);
}
