package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.SaathratriEntity3;
import com.saathratri.developer.blog.domain.SaathratriEntity3Id;
import com.saathratri.developer.blog.repository.SaathratriEntity3Repository;
import com.saathratri.developer.blog.service.SaathratriEntity3Service;
import com.saathratri.developer.blog.service.dto.SaathratriEntity3DTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntity3Mapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity3}.
 */
@Service
public class SaathratriEntity3ServiceImpl implements SaathratriEntity3Service {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntity3ServiceImpl.class);

    private final SaathratriEntity3Repository saathratriEntity3Repository;

    private final SaathratriEntity3Mapper saathratriEntity3Mapper;

    public SaathratriEntity3ServiceImpl(
        SaathratriEntity3Repository saathratriEntity3Repository,
        SaathratriEntity3Mapper saathratriEntity3Mapper
    ) {
        this.saathratriEntity3Repository = saathratriEntity3Repository;
        this.saathratriEntity3Mapper = saathratriEntity3Mapper;
    }

    @Override
    public SaathratriEntity3DTO save(SaathratriEntity3DTO saathratriEntity3DTO) {
        LOG.debug("Request to save SaathratriEntity3 : {}", saathratriEntity3DTO);
        SaathratriEntity3 saathratriEntity3 = saathratriEntity3Mapper.toEntity(saathratriEntity3DTO);
        saathratriEntity3 = saathratriEntity3Repository.save(saathratriEntity3);
        LOG.debug("Saved saathratriEntity3 : {}", saathratriEntity3);
        return saathratriEntity3Mapper.toDto(saathratriEntity3);
    }

    @Override
    public SaathratriEntity3DTO update(SaathratriEntity3DTO saathratriEntity3DTO) {
        LOG.debug("Request to update SaathratriEntity3 : {}", saathratriEntity3DTO);
        SaathratriEntity3 saathratriEntity3 = saathratriEntity3Mapper.toEntity(saathratriEntity3DTO);
        saathratriEntity3 = saathratriEntity3Repository.save(saathratriEntity3);
        LOG.debug("Saved saathratriEntity3 : {}", saathratriEntity3);
        return saathratriEntity3Mapper.toDto(saathratriEntity3);
    }

    @Override
    public Optional<SaathratriEntity3DTO> partialUpdate(SaathratriEntity3DTO saathratriEntity3DTO) {
        LOG.debug("Request to partially update SaathratriEntity3 : {}", saathratriEntity3DTO);

        return saathratriEntity3Repository
            .findById(
                new SaathratriEntity3Id(
                    saathratriEntity3DTO.getCompositeId().getEntityType(),
                    saathratriEntity3DTO.getCompositeId().getCreatedTimeId()
                )
            )
            .map(existingSaathratriEntity3 -> {
                saathratriEntity3Mapper.partialUpdate(existingSaathratriEntity3, saathratriEntity3DTO);

                return existingSaathratriEntity3;
            })
            .map(saathratriEntity3Repository::save)
            .map(saathratriEntity3Mapper::toDto);
    }

    @Override
    public List<SaathratriEntity3DTO> findAll() {
        LOG.debug("Request to get all SaathratriEntity3s");
        return saathratriEntity3Repository
            .findAll()
            .stream()
            .map(saathratriEntity3Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<SaathratriEntity3DTO> findOne(SaathratriEntity3Id compositeId) {
        LOG.debug("Request to get SaathratriEntity3 : {}", compositeId);
        return saathratriEntity3Repository.findById(compositeId).map(saathratriEntity3Mapper::toDto);
    }

    @Override
    public void delete(SaathratriEntity3Id compositeId) {
        LOG.debug("Request to delete SaathratriEntity3 : {}", compositeId);
        saathratriEntity3Repository.deleteById(compositeId);
    }

    @Override
    public List<SaathratriEntity3DTO> findAllByCompositeIdEntityType(final String entityType) {
        LOG.debug("Request to findAllByCompositeIdEntityType(final String entityType) service in SaathratriEntity3ServiceImpl.");
        return saathratriEntity3Repository
            .findAllByCompositeIdEntityType(entityType)
            .stream()
            .map(saathratriEntity3Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<SaathratriEntity3DTO> findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(
        final String entityType,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(final String entityType, final UUID createdTimeId) service in SaathratriEntity3ServiceImpl."
        );
        return saathratriEntity3Repository
            .findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(entityType, createdTimeId)
            .map(saathratriEntity3Mapper::toDto);
    }

    @Override
    public List<SaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(
        final String entityType,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(final String entityType, final UUID createdTimeId) service in SaathratriEntity3ServiceImpl."
        );
        return saathratriEntity3Repository
            .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(entityType, createdTimeId)
            .stream()
            .map(saathratriEntity3Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<SaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(
        final String entityType,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(final String entityType, final UUID createdTimeId) service in SaathratriEntity3ServiceImpl."
        );
        return saathratriEntity3Repository
            .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(entityType, createdTimeId)
            .stream()
            .map(saathratriEntity3Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public SaathratriEntity3DTO findLatestByCompositeIdEntityType(final String entityType) {
        LOG.debug("Request to findLatestByCompositeIdEntityType(final String entityType) service in SaathratriEntity3ServiceImpl.");
        return saathratriEntity3Repository.findLatestByCompositeIdEntityType(entityType).map(saathratriEntity3Mapper::toDto).orElse(null); // Return null if no record found
    }
}
