package lviv.syrovyi.health_care.visit.repository.impl;

import jakarta.persistence.*;
import lombok.*;
import lviv.syrovyi.health_care.common.entity.BaseEntity;
import lviv.syrovyi.health_care.doctor.repository.entity.Doctor;
import lviv.syrovyi.health_care.patient.repository.entity.Patient;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "visits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Visit extends BaseEntity {

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime start;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime end;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "doctor_id", nullable = false)
    private UUID doctorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Doctor doctor;
}
