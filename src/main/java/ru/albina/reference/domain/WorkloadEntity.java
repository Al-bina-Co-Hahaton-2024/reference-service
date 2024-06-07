package ru.albina.reference.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "workload")
@Accessors(chain = true)
public class WorkloadEntity {
    @EmbeddedId
    private WorkloadId id;

    @Column(name = "manual_value")
    private Long manualValue;

    @Column(name = "generated_value")
    private Long generatedValue;

    @Getter
    @Setter
    @Accessors(chain = true)
    @Embeddable
    public static class WorkloadId implements Serializable {
        private static final long serialVersionUID = 8447058825724154816L;
        @NotNull
        @Column(name = "year", nullable = false)
        private Integer year;

        @NotNull
        @Column(name = "week", nullable = false)
        private Integer week;

        @Size(max = 15)
        @NotNull
        @Column(name = "modality", nullable = false, length = 15)
        @Enumerated(EnumType.STRING)
        private Modality modality;

        @Size(max = 15)
        @NotNull
        @Column(name = "type_modality", nullable = false, length = 15)
        @Enumerated(EnumType.STRING)
        private TypeModality typeModality;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
            WorkloadId entity = (WorkloadId) o;
            return Objects.equals(this.week, entity.week) &&
                    Objects.equals(this.modality, entity.modality) &&
                    Objects.equals(this.year, entity.year) &&
                    Objects.equals(this.typeModality, entity.typeModality);
        }

        @Override
        public int hashCode() {
            return Objects.hash(week, modality, year, typeModality);
        }

    }
}