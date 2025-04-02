package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.SaathratriEntity6;
import com.saathratri.developer.blog.domain.SaathratriEntity6Id;
import com.saathratri.developer.blog.repository.SaathratriEntity6Repository;
import com.saathratri.developer.blog.service.SaathratriEntity6Service;
import com.saathratri.developer.blog.service.dto.SaathratriEntity6DTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntity6Mapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity6}.
 */
@Service
public class SaathratriEntity6ServiceImpl implements SaathratriEntity6Service {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntity6ServiceImpl.class);

    private final SaathratriEntity6Repository saathratriEntity6Repository;

    private final SaathratriEntity6Mapper saathratriEntity6Mapper;

    public SaathratriEntity6ServiceImpl(
        SaathratriEntity6Repository saathratriEntity6Repository,
        SaathratriEntity6Mapper saathratriEntity6Mapper
    ) {
        this.saathratriEntity6Repository = saathratriEntity6Repository;
        this.saathratriEntity6Mapper = saathratriEntity6Mapper;
    }

    @Override
    public SaathratriEntity6DTO save(SaathratriEntity6DTO saathratriEntity6DTO) {
        LOG.debug("Request to save SaathratriEntity6 : {}", saathratriEntity6DTO);
        SaathratriEntity6 saathratriEntity6 = saathratriEntity6Mapper.toEntity(saathratriEntity6DTO);
        saathratriEntity6 = saathratriEntity6Repository.save(saathratriEntity6);
        LOG.debug("Saved saathratriEntity6 : {}", saathratriEntity6);
        return saathratriEntity6Mapper.toDto(saathratriEntity6);
    }

    @Override
    public SaathratriEntity6DTO update(SaathratriEntity6DTO saathratriEntity6DTO) {
        LOG.debug("Request to update SaathratriEntity6 : {}", saathratriEntity6DTO);
        SaathratriEntity6 saathratriEntity6 = saathratriEntity6Mapper.toEntity(saathratriEntity6DTO);
        saathratriEntity6 = saathratriEntity6Repository.save(saathratriEntity6);
        LOG.debug("Saved saathratriEntity6 : {}", saathratriEntity6);
        return saathratriEntity6Mapper.toDto(saathratriEntity6);
    }

    @Override
    public Optional<SaathratriEntity6DTO> partialUpdate(SaathratriEntity6DTO saathratriEntity6DTO) {
        LOG.debug("Request to partially update SaathratriEntity6 : {}", saathratriEntity6DTO);

        return saathratriEntity6Repository
            .findById(
                new SaathratriEntity6Id(
                    saathratriEntity6DTO.getCompositeId().getOrganizationId(),
                    saathratriEntity6DTO.getCompositeId().getArrivalDate(),
                    saathratriEntity6DTO.getCompositeId().getAccountNumber(),
                    saathratriEntity6DTO.getCompositeId().getCreatedTimeId()
                )
            )
            .map(existingSaathratriEntity6 -> {
                saathratriEntity6Mapper.partialUpdate(existingSaathratriEntity6, saathratriEntity6DTO);

                return existingSaathratriEntity6;
            })
            .map(saathratriEntity6Repository::save)
            .map(saathratriEntity6Mapper::toDto);
    }

    @Override
    public List<SaathratriEntity6DTO> findAll() {
        LOG.debug("Request to get all SaathratriEntity6s");
        return saathratriEntity6Repository
            .findAll()
            .stream()
            .map(saathratriEntity6Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<SaathratriEntity6DTO> findOne(SaathratriEntity6Id compositeId) {
        LOG.debug("Request to get SaathratriEntity6 : {}", compositeId);
        return saathratriEntity6Repository.findById(compositeId).map(saathratriEntity6Mapper::toDto);
    }

    @Override
    public void delete(SaathratriEntity6Id compositeId) {
        LOG.debug("Request to delete SaathratriEntity6 : {}", compositeId);
        saathratriEntity6Repository.deleteById(compositeId);
    }

    @Override
    public List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationId(final UUID organizationId) {
        LOG.debug("Request to findAllByCompositeIdOrganizationId(final UUID organizationId) service in SaathratriEntity6ServiceImpl.");
        return saathratriEntity6Repository
            .findAllByCompositeIdOrganizationId(organizationId)
            .stream()
            .map(saathratriEntity6Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        final UUID organizationId,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(final UUID organizationId, final Long arrivalDate) service in SaathratriEntity6ServiceImpl."
        );
        return saathratriEntity6Repository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(organizationId, arrivalDate)
            .stream()
            .map(saathratriEntity6Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        final UUID organizationId,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(final UUID organizationId, final Long arrivalDate) service in SaathratriEntity6ServiceImpl."
        );
        return saathratriEntity6Repository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(organizationId, arrivalDate)
            .stream()
            .map(saathratriEntity6Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        final UUID organizationId,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(final UUID organizationId, final Long arrivalDate) service in SaathratriEntity6ServiceImpl."
        );
        return saathratriEntity6Repository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(organizationId, arrivalDate)
            .stream()
            .map(saathratriEntity6Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(final UUID organizationId, final Long arrivalDate, final String accountNumber) service in SaathratriEntity6ServiceImpl."
        );
        return saathratriEntity6Repository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
                organizationId,
                arrivalDate,
                accountNumber
            )
            .stream()
            .map(saathratriEntity6Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<
        SaathratriEntity6DTO
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(final UUID organizationId, final Long arrivalDate, final String accountNumber, final UUID createdTimeId) service in SaathratriEntity6ServiceImpl."
        );
        return saathratriEntity6Repository
            .findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId
            )
            .map(saathratriEntity6Mapper::toDto);
    }

    @Override
    public List<
        SaathratriEntity6DTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(final UUID organizationId, final Long arrivalDate, final String accountNumber, final UUID createdTimeId) service in SaathratriEntity6ServiceImpl."
        );
        return saathratriEntity6Repository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId
            )
            .stream()
            .map(saathratriEntity6Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<
        SaathratriEntity6DTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(final UUID organizationId, final Long arrivalDate, final String accountNumber, final UUID createdTimeId) service in SaathratriEntity6ServiceImpl."
        );
        return saathratriEntity6Repository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId
            )
            .stream()
            .map(saathratriEntity6Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public SaathratriEntity6DTO findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    ) {
        LOG.debug(
            "Request to findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(final UUID organizationId, final Long arrivalDate, final String accountNumber) service in SaathratriEntity6ServiceImpl."
        );
        return saathratriEntity6Repository
            .findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
                organizationId,
                arrivalDate,
                accountNumber
            )
            .map(saathratriEntity6Mapper::toDto)
            .orElse(null); // Return null if no record found
    }
}
