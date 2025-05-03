package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.AddOnsSelectedByOrganization;
import com.saathratri.developer.blog.domain.AddOnsSelectedByOrganizationId;
import com.saathratri.developer.blog.repository.AddOnsSelectedByOrganizationRepository;
import com.saathratri.developer.blog.service.AddOnsSelectedByOrganizationService;
import com.saathratri.developer.blog.service.dto.AddOnsSelectedByOrganizationDTO;
import com.saathratri.developer.blog.service.mapper.AddOnsSelectedByOrganizationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.AddOnsSelectedByOrganization}.
 */
@Service
public class AddOnsSelectedByOrganizationServiceImpl implements AddOnsSelectedByOrganizationService {

    private static final Logger LOG = LoggerFactory.getLogger(AddOnsSelectedByOrganizationServiceImpl.class);

    private final AddOnsSelectedByOrganizationRepository addOnsSelectedByOrganizationRepository;

    private final AddOnsSelectedByOrganizationMapper addOnsSelectedByOrganizationMapper;

    public AddOnsSelectedByOrganizationServiceImpl(
        AddOnsSelectedByOrganizationRepository addOnsSelectedByOrganizationRepository,
        AddOnsSelectedByOrganizationMapper addOnsSelectedByOrganizationMapper
    ) {
        this.addOnsSelectedByOrganizationRepository = addOnsSelectedByOrganizationRepository;
        this.addOnsSelectedByOrganizationMapper = addOnsSelectedByOrganizationMapper;
    }

    @Override
    public AddOnsSelectedByOrganizationDTO save(AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO) {
        LOG.debug("Request to save AddOnsSelectedByOrganization : {}", addOnsSelectedByOrganizationDTO);
        AddOnsSelectedByOrganization addOnsSelectedByOrganization = addOnsSelectedByOrganizationMapper.toEntity(
            addOnsSelectedByOrganizationDTO
        );
        addOnsSelectedByOrganization = addOnsSelectedByOrganizationRepository.save(addOnsSelectedByOrganization);
        LOG.debug("Saved addOnsSelectedByOrganization : {}", addOnsSelectedByOrganization);
        return addOnsSelectedByOrganizationMapper.toDto(addOnsSelectedByOrganization);
    }

    @Override
    public AddOnsSelectedByOrganizationDTO update(AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO) {
        LOG.debug("Request to update AddOnsSelectedByOrganization : {}", addOnsSelectedByOrganizationDTO);
        AddOnsSelectedByOrganization addOnsSelectedByOrganization = addOnsSelectedByOrganizationMapper.toEntity(
            addOnsSelectedByOrganizationDTO
        );
        addOnsSelectedByOrganization = addOnsSelectedByOrganizationRepository.save(addOnsSelectedByOrganization);
        LOG.debug("Saved addOnsSelectedByOrganization : {}", addOnsSelectedByOrganization);
        return addOnsSelectedByOrganizationMapper.toDto(addOnsSelectedByOrganization);
    }

    @Override
    public Optional<AddOnsSelectedByOrganizationDTO> partialUpdate(AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO) {
        LOG.debug("Request to partially update AddOnsSelectedByOrganization : {}", addOnsSelectedByOrganizationDTO);

        return addOnsSelectedByOrganizationRepository
            .findById(
                new AddOnsSelectedByOrganizationId(
                    addOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId(),
                    addOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate(),
                    addOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber(),
                    addOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId()
                )
            )
            .map(existingAddOnsSelectedByOrganization -> {
                addOnsSelectedByOrganizationMapper.partialUpdate(existingAddOnsSelectedByOrganization, addOnsSelectedByOrganizationDTO);

                return existingAddOnsSelectedByOrganization;
            })
            .map(addOnsSelectedByOrganizationRepository::save)
            .map(addOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<AddOnsSelectedByOrganizationDTO> findAll() {
        LOG.debug("Request to get all AddOnsSelectedByOrganizations");
        return addOnsSelectedByOrganizationRepository
            .findAll()
            .stream()
            .map(addOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<AddOnsSelectedByOrganizationDTO> findOne(AddOnsSelectedByOrganizationId compositeId) {
        LOG.debug("Request to get AddOnsSelectedByOrganization : {}", compositeId);
        return addOnsSelectedByOrganizationRepository.findById(compositeId).map(addOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public void delete(AddOnsSelectedByOrganizationId compositeId) {
        LOG.debug("Request to delete AddOnsSelectedByOrganization : {}", compositeId);
        addOnsSelectedByOrganizationRepository.deleteById(compositeId);
    }

    @Override
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationId(final UUID organizationId) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationId(final UUID organizationId) service in AddOnsSelectedByOrganizationServiceImpl."
        );
        return addOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationId(organizationId)
            .stream()
            .map(addOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        final UUID organizationId,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(final UUID organizationId, final Long arrivalDate) service in AddOnsSelectedByOrganizationServiceImpl."
        );
        return addOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(organizationId, arrivalDate)
            .stream()
            .map(addOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        final UUID organizationId,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(final UUID organizationId, final Long arrivalDate) service in AddOnsSelectedByOrganizationServiceImpl."
        );
        return addOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(organizationId, arrivalDate)
            .stream()
            .map(addOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        final UUID organizationId,
        final Long arrivalDate
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(final UUID organizationId, final Long arrivalDate) service in AddOnsSelectedByOrganizationServiceImpl."
        );
        return addOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(organizationId, arrivalDate)
            .stream()
            .map(addOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(final UUID organizationId, final Long arrivalDate, final String accountNumber) service in AddOnsSelectedByOrganizationServiceImpl."
        );
        return addOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
                organizationId,
                arrivalDate,
                accountNumber
            )
            .stream()
            .map(addOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<
        AddOnsSelectedByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(final UUID organizationId, final Long arrivalDate, final String accountNumber, final UUID createdTimeId) service in AddOnsSelectedByOrganizationServiceImpl."
        );
        return addOnsSelectedByOrganizationRepository
            .findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId
            )
            .map(addOnsSelectedByOrganizationMapper::toDto);
    }

    @Override
    public List<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(final UUID organizationId, final Long arrivalDate, final String accountNumber, final UUID createdTimeId) service in AddOnsSelectedByOrganizationServiceImpl."
        );
        return addOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId
            )
            .stream()
            .map(addOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(final UUID organizationId, final Long arrivalDate, final String accountNumber, final UUID createdTimeId) service in AddOnsSelectedByOrganizationServiceImpl."
        );
        return addOnsSelectedByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
                organizationId,
                arrivalDate,
                accountNumber,
                createdTimeId
            )
            .stream()
            .map(addOnsSelectedByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public AddOnsSelectedByOrganizationDTO findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    ) {
        LOG.debug(
            "Request to findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(final UUID organizationId, final Long arrivalDate, final String accountNumber) service in AddOnsSelectedByOrganizationServiceImpl."
        );
        return addOnsSelectedByOrganizationRepository
            .findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
                organizationId,
                arrivalDate,
                accountNumber
            )
            .map(addOnsSelectedByOrganizationMapper::toDto)
            .orElse(null); // Return null if no record found
    }
}
