package ccos.devops.domain;

import ccos.devops.domain.enumeration.Sexe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false)
    private String matricule;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom")
    private String nom;

    @Column(name = "email")
    private String email;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "status")
    private String status;

    @Column(name = "fonction")
    private String fonction;

    @Column(name = "structure")
    private String structure;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<Pointage> pointages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Employee matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Employee prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public Employee nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }

    public Employee dateNaissance(LocalDate dateNaissance) {
        this.setDateNaissance(dateNaissance);
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return this.lieuNaissance;
    }

    public Employee lieuNaissance(String lieuNaissance) {
        this.setLieuNaissance(lieuNaissance);
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Employee sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Employee telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStatus() {
        return this.status;
    }

    public Employee status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFonction() {
        return this.fonction;
    }

    public Employee fonction(String fonction) {
        this.setFonction(fonction);
        return this;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getStructure() {
        return this.structure;
    }

    public Employee structure(String structure) {
        this.setStructure(structure);
        return this;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employee user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Pointage> getPointages() {
        return this.pointages;
    }

    public void setPointages(Set<Pointage> pointages) {
        if (this.pointages != null) {
            this.pointages.forEach(i -> i.setEmployee(null));
        }
        if (pointages != null) {
            pointages.forEach(i -> i.setEmployee(this));
        }
        this.pointages = pointages;
    }

    public Employee pointages(Set<Pointage> pointages) {
        this.setPointages(pointages);
        return this;
    }

    public Employee addPointage(Pointage pointage) {
        this.pointages.add(pointage);
        pointage.setEmployee(this);
        return this;
    }

    public Employee removePointage(Pointage pointage) {
        this.pointages.remove(pointage);
        pointage.setEmployee(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return getId() != null && getId().equals(((Employee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", email='" + getEmail() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", status='" + getStatus() + "'" +
            ", fonction='" + getFonction() + "'" +
            ", structure='" + getStructure() + "'" +
            "}";
    }
}
