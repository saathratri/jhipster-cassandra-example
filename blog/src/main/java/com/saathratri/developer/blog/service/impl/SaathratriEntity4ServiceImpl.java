package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.SaathratriEntity4;
import com.saathratri.developer.blog.domain.SaathratriEntity4Id;
import com.saathratri.developer.blog.repository.SaathratriEntity4Repository;
import com.saathratri.developer.blog.service.SaathratriEntity4Service;
import com.saathratri.developer.blog.service.dto.SaathratriEntity4DTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntity4Mapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity4}.
 */
@Service
public class SaathratriEntity4ServiceImpl implements SaathratriEntity4Service {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntity4ServiceImpl.class);

    private final SaathratriEntity4Repository saathratriEntity4Repository;

    private final SaathratriEntity4Mapper saathratriEntity4Mapper;

    public SaathratriEntity4ServiceImpl(
        SaathratriEntity4Repository saathratriEntity4Repository,
        SaathratriEntity4Mapper saathratriEntity4Mapper
    ) {
        this.saathratriEntity4Repository = saathratriEntity4Repository;
        this.saathratriEntity4Mapper = saathratriEntity4Mapper;
    }

    @Override
    public SaathratriEntity4DTO save(SaathratriEntity4DTO saathratriEntity4DTO) {
        LOG.debug("Request to save SaathratriEntity4 : {}", saathratriEntity4DTO);
        SaathratriEntity4 saathratriEntity4 = saathratriEntity4Mapper.toEntity(saathratriEntity4DTO);
        saathratriEntity4 = saathratriEntity4Repository.save(saathratriEntity4);
        LOG.debug("Saved saathratriEntity4 : {}", saathratriEntity4);
        return saathratriEntity4Mapper.toDto(saathratriEntity4);
    }

    @Override
    public SaathratriEntity4DTO update(SaathratriEntity4DTO saathratriEntity4DTO) {
        LOG.debug("Request to update SaathratriEntity4 : {}", saathratriEntity4DTO);
        SaathratriEntity4 saathratriEntity4 = saathratriEntity4Mapper.toEntity(saathratriEntity4DTO);
        saathratriEntity4 = saathratriEntity4Repository.save(saathratriEntity4);
        LOG.debug("Saved saathratriEntity4 : {}", saathratriEntity4);
        return saathratriEntity4Mapper.toDto(saathratriEntity4);
    }

    @Override
    public Optional<SaathratriEntity4DTO> partialUpdate(SaathratriEntity4DTO saathratriEntity4DTO) {
        LOG.debug("Request to partially update SaathratriEntity4 : {}", saathratriEntity4DTO);

        return saathratriEntity4Repository
            .findById(
                new SaathratriEntity4Id(
                    saathratriEntity4DTO.getCompositeId().getOrganizationId(),
                    saathratriEntity4DTO.getCompositeId().getAttributeKey()
                )
            )
            .map(existingSaathratriEntity4 -> {
                saathratriEntity4Mapper.partialUpdate(existingSaathratriEntity4, saathratriEntity4DTO);

                return existingSaathratriEntity4;
            })
            .map(saathratriEntity4Repository::save)
            .map(saathratriEntity4Mapper::toDto);
    }

    @Override
    public List<SaathratriEntity4DTO> findAll() {
        LOG.debug("Request to get all SaathratriEntity4s");
        return saathratriEntity4Repository
            .findAll()
            .stream()
            .map(saathratriEntity4Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<SaathratriEntity4DTO> findOne(SaathratriEntity4Id compositeId) {
        LOG.debug("Request to get SaathratriEntity4 : {}", compositeId);
        return saathratriEntity4Repository.findById(compositeId).map(saathratriEntity4Mapper::toDto);
    }

    @Override
    public void delete(SaathratriEntity4Id compositeId) {
        LOG.debug("Request to delete SaathratriEntity4 : {}", compositeId);
        saathratriEntity4Repository.deleteById(compositeId);
    }

    @Override
    public List<SaathratriEntity4DTO> findAllByCompositeIdOrganizationId(final UUID organizationId) {
        LOG.debug("Request to findAllByCompositeIdOrganizationId(final UUID organizationId) service in SaathratriEntity4ServiceImpl.");
        return saathratriEntity4Repository
            .findAllByCompositeIdOrganizationId(organizationId)
            .stream()
            .map(saathratriEntity4Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
