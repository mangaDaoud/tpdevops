package ccos.devops.service.impl;

import ccos.devops.domain.Pointage;
import ccos.devops.repository.PointageRepository;
import ccos.devops.service.PointageService;
import ccos.devops.service.dto.PointageDTO;
import ccos.devops.service.mapper.PointageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ccos.devops.domain.Pointage}.
 */
@Service
@Transactional
public class PointageServiceImpl implements PointageService {

    private final Logger log = LoggerFactory.getLogger(PointageServiceImpl.class);

    private final PointageRepository pointageRepository;

    private final PointageMapper pointageMapper;

    public PointageServiceImpl(PointageRepository pointageRepository, PointageMapper pointageMapper) {
        this.pointageRepository = pointageRepository;
        this.pointageMapper = pointageMapper;
    }

    @Override
    public PointageDTO save(PointageDTO pointageDTO) {
        log.debug("Request to save Pointage : {}", pointageDTO);
        Pointage pointage = pointageMapper.toEntity(pointageDTO);
        pointage = pointageRepository.save(pointage);
        return pointageMapper.toDto(pointage);
    }

    @Override
    public PointageDTO update(PointageDTO pointageDTO) {
        log.debug("Request to update Pointage : {}", pointageDTO);
        Pointage pointage = pointageMapper.toEntity(pointageDTO);
        pointage = pointageRepository.save(pointage);
        return pointageMapper.toDto(pointage);
    }

    @Override
    public Optional<PointageDTO> partialUpdate(PointageDTO pointageDTO) {
        log.debug("Request to partially update Pointage : {}", pointageDTO);

        return pointageRepository
            .findById(pointageDTO.getId())
            .map(existingPointage -> {
                pointageMapper.partialUpdate(existingPointage, pointageDTO);

                return existingPointage;
            })
            .map(pointageRepository::save)
            .map(pointageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PointageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pointages");
        return pointageRepository.findAll(pageable).map(pointageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PointageDTO> findOne(Long id) {
        log.debug("Request to get Pointage : {}", id);
        return pointageRepository.findById(id).map(pointageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pointage : {}", id);
        pointageRepository.deleteById(id);
    }
}
