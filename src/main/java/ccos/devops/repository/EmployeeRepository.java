package ccos.devops.repository;

import ccos.devops.domain.Employee;
import ccos.devops.service.dto.EmployeeDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findOneByMatricule(String matricule);
}
