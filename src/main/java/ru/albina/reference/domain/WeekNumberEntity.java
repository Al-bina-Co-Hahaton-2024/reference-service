package ru.albina.reference.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "week_number")
@Accessors(chain = true)
public class WeekNumberEntity {
    @EmbeddedId
    private WeekNumberEntityId id;

    @NotNull
    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    @Getter
    @Setter
    @Embeddable
    public static class WeekNumberEntityId implements Serializable {
        private static final long serialVersionUID = -7514957247092580506L;
        @NotNull
        @Column(name = "start_date", nullable = false)
        private LocalDate startDate;

        @NotNull
        @Column(name = "end_date", nullable = false)
        private LocalDate endDate;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
            WeekNumberEntityId entity = (WeekNumberEntityId) o;
            return Objects.equals(this.endDate, entity.endDate) &&
                    Objects.equals(this.startDate, entity.startDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(endDate, startDate);
        }

    }
}