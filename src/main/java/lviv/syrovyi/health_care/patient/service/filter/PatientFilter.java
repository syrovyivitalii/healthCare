package lviv.syrovyi.health_care.patient.service.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lviv.syrovyi.health_care.common.util.filter.SearchFilter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = PRIVATE)
public class PatientFilter extends SearchFilter {

}
