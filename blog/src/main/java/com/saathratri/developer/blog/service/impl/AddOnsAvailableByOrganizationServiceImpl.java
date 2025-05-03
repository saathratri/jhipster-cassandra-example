package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.AddOnsAvailableByOrganization;
import com.saathratri.developer.blog.domain.AddOnsAvailableByOrganizationId;
import com.saathratri.developer.blog.repository.AddOnsAvailableByOrganizationRepository;
import com.saathratri.developer.blog.service.AddOnsAvailableByOrganizationService;
import com.saathratri.developer.blog.service.dto.AddOnsAvailableByOrganizationDTO;
import com.saathratri.developer.blog.service.mapper.AddOnsAvailableByOrganizationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.AddOnsAvailableByOrganization}.
 */
@Service
public class AddOnsAvailableByOrganizationServiceImpl implements AddOnsAvailableByOrganizationService {

    private static final Logger LOG = LoggerFactory.getLogger(AddOnsAvailableByOrganizationServiceImpl.class);

    private final AddOnsAvailableByOrganizationRepository addOnsAvailableByOrganizationRepository;

    private final AddOnsAvailableByOrganizationMapper addOnsAvailableByOrganizationMapper;

    public AddOnsAvailableByOrganizationServiceImpl(
        AddOnsAvailableByOrganizationRepository addOnsAvailableByOrganizationRepository,
        AddOnsAvailableByOrganizationMapper addOnsAvailableByOrganizationMapper
    ) {
        this.addOnsAvailableByOrganizationRepository = addOnsAvailableByOrganizationRepository;
        this.addOnsAvailableByOrganizationMapper = addOnsAvailableByOrganizationMapper;
    }

    @Override
    public AddOnsAvailableByOrganizationDTO save(AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO) {
        LOG.debug("Request to save AddOnsAvailableByOrganization : {}", addOnsAvailableByOrganizationDTO);
        AddOnsAvailableByOrganization addOnsAvailableByOrganization = addOnsAvailableByOrganizationMapper.toEntity(
            addOnsAvailableByOrganizationDTO
        );
        addOnsAvailableByOrganization = addOnsAvailableByOrganizationRepository.save(addOnsAvailableByOrganization);
        LOG.debug("Saved addOnsAvailableByOrganization : {}", addOnsAvailableByOrganization);
        return addOnsAvailableByOrganizationMapper.toDto(addOnsAvailableByOrganization);
    }

    @Override
    public AddOnsAvailableByOrganizationDTO update(AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO) {
        LOG.debug("Request to update AddOnsAvailableByOrganization : {}", addOnsAvailableByOrganizationDTO);
        AddOnsAvailableByOrganization addOnsAvailableByOrganization = addOnsAvailableByOrganizationMapper.toEntity(
            addOnsAvailableByOrganizationDTO
        );
        addOnsAvailableByOrganization = addOnsAvailableByOrganizationRepository.save(addOnsAvailableByOrganization);
        LOG.debug("Saved addOnsAvailableByOrganization : {}", addOnsAvailableByOrganization);
        return addOnsAvailableByOrganizationMapper.toDto(addOnsAvailableByOrganization);
    }

    @Override
    public Optional<AddOnsAvailableByOrganizationDTO> partialUpdate(AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO) {
        LOG.debug("Request to partially update AddOnsAvailableByOrganization : {}", addOnsAvailableByOrganizationDTO);

        return addOnsAvailableByOrganizationRepository
            .findById(
                new AddOnsAvailableByOrganizationId(
                    addOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId(),
                    addOnsAvailableByOrganizationDTO.getCompositeId().getEntityType(),
                    addOnsAvailableByOrganizationDTO.getCompositeId().getEntityId(),
                    addOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
                )
            )
            .map(existingAddOnsAvailableByOrganization -> {
                addOnsAvailableByOrganizationMapper.partialUpdate(existingAddOnsAvailableByOrganization, addOnsAvailableByOrganizationDTO);

                return existingAddOnsAvailableByOrganization;
            })
            .map(addOnsAvailableByOrganizationRepository::save)
            .map(addOnsAvailableByOrganizationMapper::toDto);
    }

    @Override
    public List<AddOnsAvailableByOrganizationDTO> findAll() {
        LOG.debug("Request to get all AddOnsAvailableByOrganizations");
        return addOnsAvailableByOrganizationRepository
            .findAll()
            .stream()
            .map(addOnsAvailableByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<AddOnsAvailableByOrganizationDTO> findOne(AddOnsAvailableByOrganizationId compositeId) {
        LOG.debug("Request to get AddOnsAvailableByOrganization : {}", compositeId);
        return addOnsAvailableByOrganizationRepository.findById(compositeId).map(addOnsAvailableByOrganizationMapper::toDto);
    }

    @Override
    public void delete(AddOnsAvailableByOrganizationId compositeId) {
        LOG.debug("Request to delete AddOnsAvailableByOrganization : {}", compositeId);
        addOnsAvailableByOrganizationRepository.deleteById(compositeId);
    }

    @Override
    public List<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationId(final UUID organizationId) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationId(final UUID organizationId) service in AddOnsAvailableByOrganizationServiceImpl."
        );
        return addOnsAvailableByOrganizationRepository
            .findAllByCompositeIdOrganizationId(organizationId)
            .stream()
            .map(addOnsAvailableByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        final UUID organizationId,
        final String entityType
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(final UUID organizationId, final String entityType) service in AddOnsAvailableByOrganizationServiceImpl."
        );
        return addOnsAvailableByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(organizationId, entityType)
            .stream()
            .map(addOnsAvailableByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(final UUID organizationId, final String entityType, final UUID entityId) service in AddOnsAvailableByOrganizationServiceImpl."
        );
        return addOnsAvailableByOrganizationRepository
            .findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(organizationId, entityType, entityId)
            .stream()
            .map(addOnsAvailableByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<
        AddOnsAvailableByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        final UUID addOnId
    ) {
        LOG.debug(
            "Request to findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(final UUID organizationId, final String entityType, final UUID entityId, final UUID addOnId) service in AddOnsAvailableByOrganizationServiceImpl."
        );
        return addOnsAvailableByOrganizationRepository
            .findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
                organizationId,
                entityType,
                entityId,
                addOnId
            )
            .map(addOnsAvailableByOrganizationMapper::toDto);
    }
}
