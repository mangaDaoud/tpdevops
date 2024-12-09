package ccos.devops.repository;

import ccos.devops.domain.Pointage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pointage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointageRepository extends JpaRepository<Pointage, Long> {}
