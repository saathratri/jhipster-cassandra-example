package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.SaathratriEntity2;
import com.saathratri.developer.blog.domain.SaathratriEntity2Id;
import com.saathratri.developer.blog.repository.SaathratriEntity2Repository;
import com.saathratri.developer.blog.service.SaathratriEntity2Service;
import com.saathratri.developer.blog.service.dto.SaathratriEntity2DTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntity2Mapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity2}.
 */
@Service
public class SaathratriEntity2ServiceImpl implements SaathratriEntity2Service {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntity2ServiceImpl.class);

    private final SaathratriEntity2Repository saathratriEntity2Repository;

    private final SaathratriEntity2Mapper saathratriEntity2Mapper;

    public SaathratriEntity2ServiceImpl(
        SaathratriEntity2Repository saathratriEntity2Repository,
        SaathratriEntity2Mapper saathratriEntity2Mapper
    ) {
        this.saathratriEntity2Repository = saathratriEntity2Repository;
        this.saathratriEntity2Mapper = saathratriEntity2Mapper;
    }

    @Override
    public SaathratriEntity2DTO save(SaathratriEntity2DTO saathratriEntity2DTO) {
        LOG.debug("Request to save SaathratriEntity2 : {}", saathratriEntity2DTO);
        SaathratriEntity2 saathratriEntity2 = saathratriEntity2Mapper.toEntity(saathratriEntity2DTO);
        saathratriEntity2 = saathratriEntity2Repository.save(saathratriEntity2);
        LOG.debug("Saved saathratriEntity2 : {}", saathratriEntity2);
        return saathratriEntity2Mapper.toDto(saathratriEntity2);
    }

    @Override
    public SaathratriEntity2DTO update(SaathratriEntity2DTO saathratriEntity2DTO) {
        LOG.debug("Request to update SaathratriEntity2 : {}", saathratriEntity2DTO);
        SaathratriEntity2 saathratriEntity2 = saathratriEntity2Mapper.toEntity(saathratriEntity2DTO);
        saathratriEntity2 = saathratriEntity2Repository.save(saathratriEntity2);
        LOG.debug("Saved saathratriEntity2 : {}", saathratriEntity2);
        return saathratriEntity2Mapper.toDto(saathratriEntity2);
    }

    @Override
    public Optional<SaathratriEntity2DTO> partialUpdate(SaathratriEntity2DTO saathratriEntity2DTO) {
        LOG.debug("Request to partially update SaathratriEntity2 : {}", saathratriEntity2DTO);

        return saathratriEntity2Repository
            .findById(
                new SaathratriEntity2Id(
                    saathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    saathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    saathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    saathratriEntity2DTO.getCompositeId().getBlogId()
                )
            )
            .map(existingSaathratriEntity2 -> {
                saathratriEntity2Mapper.partialUpdate(existingSaathratriEntity2, saathratriEntity2DTO);

                return existingSaathratriEntity2;
            })
            .map(saathratriEntity2Repository::save)
            .map(saathratriEntity2Mapper::toDto);
    }

    @Override
    public List<SaathratriEntity2DTO> findAll() {
        LOG.debug("Request to get all SaathratriEntity2s");
        return saathratriEntity2Repository
            .findAll()
            .stream()
            .map(saathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<SaathratriEntity2DTO> findOne(SaathratriEntity2Id compositeId) {
        LOG.debug("Request to get SaathratriEntity2 : {}", compositeId);
        return saathratriEntity2Repository.findById(compositeId).map(saathratriEntity2Mapper::toDto);
    }

    @Override
    public void delete(SaathratriEntity2Id compositeId) {
        LOG.debug("Request to delete SaathratriEntity2 : {}", compositeId);
        saathratriEntity2Repository.deleteById(compositeId);
    }

    @Override
    public List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeId(final UUID entityTypeId) {
        LOG.debug("Request to findAllByCompositeIdEntityTypeId(final UUID entityTypeId) service in SaathratriEntity2ServiceImpl.");
        return saathratriEntity2Repository
            .findAllByCompositeIdEntityTypeId(entityTypeId)
            .stream()
            .map(saathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(
        final UUID entityTypeId,
        final Long yearOfDateAdded
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(final UUID entityTypeId, final Long yearOfDateAdded) service in SaathratriEntity2ServiceImpl."
        );
        return saathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(entityTypeId, yearOfDateAdded)
            .stream()
            .map(saathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate) service in SaathratriEntity2ServiceImpl."
        );
        return saathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate
            )
            .stream()
            .map(saathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate) service in SaathratriEntity2ServiceImpl."
        );
        return saathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate
            )
            .stream()
            .map(saathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate) service in SaathratriEntity2ServiceImpl."
        );
        return saathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate
            )
            .stream()
            .map(saathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<
        SaathratriEntity2DTO
    > findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    ) {
        LOG.debug(
            "Request to findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate, final UUID blogId) service in SaathratriEntity2ServiceImpl."
        );
        return saathratriEntity2Repository
            .findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId
            )
            .map(saathratriEntity2Mapper::toDto);
    }

    @Override
    public List<
        SaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate, final UUID blogId) service in SaathratriEntity2ServiceImpl."
        );
        return saathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId
            )
            .stream()
            .map(saathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<
        SaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate, final UUID blogId) service in SaathratriEntity2ServiceImpl."
        );
        return saathratriEntity2Repository
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId
            )
            .stream()
            .map(saathratriEntity2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public SaathratriEntity2DTO findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(final UUID entityTypeId, final Long yearOfDateAdded, final Long arrivalDate) service in SaathratriEntity2ServiceImpl."
        );
        return saathratriEntity2Repository
            .findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate
            )
            .map(saathratriEntity2Mapper::toDto)
            .orElse(null); // Return null if no record found
    }
}
