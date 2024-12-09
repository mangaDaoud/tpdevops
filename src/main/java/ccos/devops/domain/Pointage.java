package ccos.devops.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pointage.
 */
@Entity
@Table(name = "pointage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pointage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "datu_du_jour")
    private LocalDate datuDuJour;

    @Column(name = "heure_arrive")
    private String heureArrive;

    @Column(name = "heure_depart")
    private String heureDepart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "pointages" }, allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pointage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatuDuJour() {
        return this.datuDuJour;
    }

    public Pointage datuDuJour(LocalDate datuDuJour) {
        this.setDatuDuJour(datuDuJour);
        return this;
    }

    public void setDatuDuJour(LocalDate datuDuJour) {
        this.datuDuJour = datuDuJour;
    }

    public String getHeureArrive() {
        return this.heureArrive;
    }

    public Pointage heureArrive(String heureArrive) {
        this.setHeureArrive(heureArrive);
        return this;
    }

    public void setHeureArrive(String heureArrive) {
        this.heureArrive = heureArrive;
    }

    public String getHeureDepart() {
        return this.heureDepart;
    }

    public Pointage heureDepart(String heureDepart) {
        this.setHeureDepart(heureDepart);
        return this;
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Pointage employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pointage)) {
            return false;
        }
        return getId() != null && getId().equals(((Pointage) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pointage{" +
            "id=" + getId() +
            ", datuDuJour='" + getDatuDuJour() + "'" +
            ", heureArrive='" + getHeureArrive() + "'" +
            ", heureDepart='" + getHeureDepart() + "'" +
            "}";
    }
}
