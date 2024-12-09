package ccos.devops.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link ccos.devops.domain.Pointage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PointageDTO implements Serializable {

    private Long id;

    private LocalDate datuDuJour;

    private String heureArrive;

    private String heureDepart;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatuDuJour() {
        return datuDuJour;
    }

    public void setDatuDuJour(LocalDate datuDuJour) {
        this.datuDuJour = datuDuJour;
    }

    public String getHeureArrive() {
        return heureArrive;
    }

    public void setHeureArrive(String heureArrive) {
        this.heureArrive = heureArrive;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointageDTO)) {
            return false;
        }

        PointageDTO pointageDTO = (PointageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pointageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointageDTO{" +
            "id=" + getId() +
            ", datuDuJour='" + getDatuDuJour() + "'" +
            ", heureArrive='" + getHeureArrive() + "'" +
            ", heureDepart='" + getHeureDepart() + "'" +
            ", employee=" + getEmployee() +
            "}";
    }
}
