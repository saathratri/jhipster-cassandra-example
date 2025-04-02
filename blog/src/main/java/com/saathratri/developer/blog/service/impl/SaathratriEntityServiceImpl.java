package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.SaathratriEntity;
import com.saathratri.developer.blog.repository.SaathratriEntityRepository;
import com.saathratri.developer.blog.service.SaathratriEntityService;
import com.saathratri.developer.blog.service.dto.SaathratriEntityDTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntityMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity}.
 */
@Service
public class SaathratriEntityServiceImpl implements SaathratriEntityService {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntityServiceImpl.class);

    private final SaathratriEntityRepository saathratriEntityRepository;

    private final SaathratriEntityMapper saathratriEntityMapper;

    public SaathratriEntityServiceImpl(
        SaathratriEntityRepository saathratriEntityRepository,
        SaathratriEntityMapper saathratriEntityMapper
    ) {
        this.saathratriEntityRepository = saathratriEntityRepository;
        this.saathratriEntityMapper = saathratriEntityMapper;
    }

    @Override
    public SaathratriEntityDTO save(SaathratriEntityDTO saathratriEntityDTO) {
        LOG.debug("Request to save SaathratriEntity : {}", saathratriEntityDTO);
        SaathratriEntity saathratriEntity = saathratriEntityMapper.toEntity(saathratriEntityDTO);
        saathratriEntity = saathratriEntityRepository.save(saathratriEntity);
        LOG.debug("Saved saathratriEntity : {}", saathratriEntity);
        return saathratriEntityMapper.toDto(saathratriEntity);
    }

    @Override
    public SaathratriEntityDTO update(SaathratriEntityDTO saathratriEntityDTO) {
        LOG.debug("Request to update SaathratriEntity : {}", saathratriEntityDTO);
        SaathratriEntity saathratriEntity = saathratriEntityMapper.toEntity(saathratriEntityDTO);
        saathratriEntity = saathratriEntityRepository.save(saathratriEntity);
        LOG.debug("Saved saathratriEntity : {}", saathratriEntity);
        return saathratriEntityMapper.toDto(saathratriEntity);
    }

    @Override
    public Optional<SaathratriEntityDTO> partialUpdate(SaathratriEntityDTO saathratriEntityDTO) {
        LOG.debug("Request to partially update SaathratriEntity : {}", saathratriEntityDTO);

        return saathratriEntityRepository
            .findById(saathratriEntityDTO.getEntityId())
            .map(existingSaathratriEntity -> {
                saathratriEntityMapper.partialUpdate(existingSaathratriEntity, saathratriEntityDTO);

                return existingSaathratriEntity;
            })
            .map(saathratriEntityRepository::save)
            .map(saathratriEntityMapper::toDto);
    }

    @Override
    public List<SaathratriEntityDTO> findAll() {
        LOG.debug("Request to get all SaathratriEntities");
        return saathratriEntityRepository
            .findAll()
            .stream()
            .map(saathratriEntityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<SaathratriEntityDTO> findOne(UUID entityId) {
        LOG.debug("Request to get SaathratriEntity : {}", entityId);
        return saathratriEntityRepository.findById(entityId).map(saathratriEntityMapper::toDto);
    }

    @Override
    public void delete(UUID entityId) {
        LOG.debug("Request to delete SaathratriEntity : {}", entityId);
        saathratriEntityRepository.deleteById(entityId);
    }
}
