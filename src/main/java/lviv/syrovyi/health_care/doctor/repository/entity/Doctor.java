package lviv.syrovyi.health_care.doctor.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lviv.syrovyi.health_care.common.entity.BaseEntity;
import lviv.syrovyi.health_care.visit.repository.impl.Visit;

import java.util.List;

@Entity
@Table(name = "doctors")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Doctor extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "timezone", nullable = false)
    private String timezone;

    @OneToMany(mappedBy = "doctor", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Visit> visits;
}
