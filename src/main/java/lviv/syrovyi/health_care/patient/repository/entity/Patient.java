package lviv.syrovyi.health_care.patient.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lviv.syrovyi.health_care.common.entity.BaseEntity;
import lviv.syrovyi.health_care.doctor.repository.entity.Doctor;
import lviv.syrovyi.health_care.visit.repository.impl.Visit;

import java.util.List;

@Entity
@Table(name = "patients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Patient extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Visit> lastVisits;

}
