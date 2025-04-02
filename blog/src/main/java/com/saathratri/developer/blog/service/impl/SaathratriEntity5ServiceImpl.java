package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.SaathratriEntity5;
import com.saathratri.developer.blog.domain.SaathratriEntity5Id;
import com.saathratri.developer.blog.repository.SaathratriEntity5Repository;
import com.saathratri.developer.blog.service.SaathratriEntity5Service;
import com.saathratri.developer.blog.service.dto.SaathratriEntity5DTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntity5Mapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity5}.
 */
@Service
public class SaathratriEntity5ServiceImpl implements SaathratriEntity5Service {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntity5ServiceImpl.class);

    private final SaathratriEntity5Repository saathratriEntity5Repository;

    private final SaathratriEntity5Mapper saathratriEntity5Mapper;

    public SaathratriEntity5ServiceImpl(
        SaathratriEntity5Repository saathratriEntity5Repository,
        SaathratriEntity5Mapper saathratriEntity5Mapper
    ) {
        this.saathratriEntity5Repository = saathratriEntity5Repository;
        this.saathratriEntity5Mapper = saathratriEntity5Mapper;
    }

    @Override
    public SaathratriEntity5DTO save(SaathratriEntity5DTO saathratriEntity5DTO) {
        LOG.debug("Request to save SaathratriEntity5 : {}", saathratriEntity5DTO);
        SaathratriEntity5 saathratriEntity5 = saathratriEntity5Mapper.toEntity(saathratriEntity5DTO);
        saathratriEntity5 = saathratriEntity5Repository.save(saathratriEntity5);
        LOG.debug("Saved saathratriEntity5 : {}", saathratriEntity5);
        return saathratriEntity5Mapper.toDto(saathratriEntity5);
    }

    @Override
    public SaathratriEntity5DTO update(SaathratriEntity5DTO saathratriEntity5DTO) {
        LOG.debug("Request to update SaathratriEntity5 : {}", saathratriEntity5DTO);
        SaathratriEntity5 saathratriEntity5 = saathratriEntity5Mapper.toEntity(saathratriEntity5DTO);
        saathratriEntity5 = saathratriEntity5Repository.save(saathratriEntity5);
        LOG.debug("Saved saathratriEntity5 : {}", saathratriEntity5);
        return saathratriEntity5Mapper.toDto(saathratriEntity5);
    }

    @Override
    public Optional<SaathratriEntity5DTO> partialUpdate(SaathratriEntity5DTO saathratriEntity5DTO) {
        LOG.debug("Request to partially update SaathratriEntity5 : {}", saathratriEntity5DTO);

        return saathratriEntity5Repository
            .findById(
                new SaathratriEntity5Id(
                    saathratriEntity5DTO.getCompositeId().getOrganizationId(),
                    saathratriEntity5DTO.getCompositeId().getEntityType(),
                    saathratriEntity5DTO.getCompositeId().getEntityId(),
                    saathratriEntity5DTO.getCompositeId().getAddOnId()
                )
            )
            .map(existingSaathratriEntity5 -> {
                saathratriEntity5Mapper.partialUpdate(existingSaathratriEntity5, saathratriEntity5DTO);

                return existingSaathratriEntity5;
            })
            .map(saathratriEntity5Repository::save)
            .map(saathratriEntity5Mapper::toDto);
    }

    @Override
    public List<SaathratriEntity5DTO> findAll() {
        LOG.debug("Request to get all SaathratriEntity5s");
        return saathratriEntity5Repository
            .findAll()
            .stream()
            .map(saathratriEntity5Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<SaathratriEntity5DTO> findOne(SaathratriEntity5Id compositeId) {
        LOG.debug("Request to get SaathratriEntity5 : {}", compositeId);
        return saathratriEntity5Repository.findById(compositeId).map(saathratriEntity5Mapper::toDto);
    }

    @Override
    public void delete(SaathratriEntity5Id compositeId) {
        LOG.debug("Request to delete SaathratriEntity5 : {}", compositeId);
        saathratriEntity5Repository.deleteById(compositeId);
    }

    @Override
    public List<SaathratriEntity5DTO> findAllByCompositeIdOrganizationId(final UUID organizationId) {
        LOG.debug("Request to findAllByCompositeIdOrganizationId(final UUID organizationId) service in SaathratriEntity5ServiceImpl.");
        return saathratriEntity5Repository
            .findAllByCompositeIdOrganizationId(organizationId)
            .stream()
            .map(saathratriEntity5Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<SaathratriEntity5DTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        final UUID organizationId,
        final String entityType
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(final UUID organizationId, final String entityType) service in SaathratriEntity5ServiceImpl."
        );
        return saathratriEntity5Repository
            .findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(organizationId, entityType)
            .stream()
            .map(saathratriEntity5Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<SaathratriEntity5DTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(final UUID organizationId, final String entityType, final UUID entityId) service in SaathratriEntity5ServiceImpl."
        );
        return saathratriEntity5Repository
            .findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(organizationId, entityType, entityId)
            .stream()
            .map(saathratriEntity5Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<
        SaathratriEntity5DTO
    > findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        final UUID addOnId
    ) {
        LOG.debug(
            "Request to findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(final UUID organizationId, final String entityType, final UUID entityId, final UUID addOnId) service in SaathratriEntity5ServiceImpl."
        );
        return saathratriEntity5Repository
            .findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
                organizationId,
                entityType,
                entityId,
                addOnId
            )
            .map(saathratriEntity5Mapper::toDto);
    }
}
