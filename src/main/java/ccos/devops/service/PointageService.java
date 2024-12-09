package ccos.devops.service;

import ccos.devops.service.dto.PointageDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ccos.devops.domain.Pointage}.
 */
public interface PointageService {
    /**
     * Save a pointage.
     *
     * @param pointageDTO the entity to save.
     * @return the persisted entity.
     */
    PointageDTO save(PointageDTO pointageDTO);

    /**
     * Updates a pointage.
     *
     * @param pointageDTO the entity to update.
     * @return the persisted entity.
     */
    PointageDTO update(PointageDTO pointageDTO);

    /**
     * Partially updates a pointage.
     *
     * @param pointageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PointageDTO> partialUpdate(PointageDTO pointageDTO);

    /**
     * Get all the pointages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PointageDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pointage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PointageDTO> findOne(Long id);

    /**
     * Delete the "id" pointage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
