package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.SetEntityByOrganization;
import com.saathratri.developer.blog.repository.SetEntityByOrganizationRepository;
import com.saathratri.developer.blog.service.SetEntityByOrganizationService;
import com.saathratri.developer.blog.service.dto.SetEntityByOrganizationDTO;
import com.saathratri.developer.blog.service.mapper.SetEntityByOrganizationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.SetEntityByOrganization}.
 */
@Service
public class SetEntityByOrganizationServiceImpl implements SetEntityByOrganizationService {

    private static final Logger LOG = LoggerFactory.getLogger(SetEntityByOrganizationServiceImpl.class);

    private final SetEntityByOrganizationRepository setEntityByOrganizationRepository;

    private final SetEntityByOrganizationMapper setEntityByOrganizationMapper;

    public SetEntityByOrganizationServiceImpl(
        SetEntityByOrganizationRepository setEntityByOrganizationRepository,
        SetEntityByOrganizationMapper setEntityByOrganizationMapper
    ) {
        this.setEntityByOrganizationRepository = setEntityByOrganizationRepository;
        this.setEntityByOrganizationMapper = setEntityByOrganizationMapper;
    }

    @Override
    public SetEntityByOrganizationDTO save(SetEntityByOrganizationDTO setEntityByOrganizationDTO) {
        LOG.debug("Request to save SetEntityByOrganization : {}", setEntityByOrganizationDTO);
        SetEntityByOrganization setEntityByOrganization = setEntityByOrganizationMapper.toEntity(setEntityByOrganizationDTO);
        setEntityByOrganization = setEntityByOrganizationRepository.save(setEntityByOrganization);
        LOG.debug("Saved setEntityByOrganization : {}", setEntityByOrganization);
        return setEntityByOrganizationMapper.toDto(setEntityByOrganization);
    }

    @Override
    public SetEntityByOrganizationDTO update(SetEntityByOrganizationDTO setEntityByOrganizationDTO) {
        LOG.debug("Request to update SetEntityByOrganization : {}", setEntityByOrganizationDTO);
        SetEntityByOrganization setEntityByOrganization = setEntityByOrganizationMapper.toEntity(setEntityByOrganizationDTO);
        setEntityByOrganization = setEntityByOrganizationRepository.save(setEntityByOrganization);
        LOG.debug("Saved setEntityByOrganization : {}", setEntityByOrganization);
        return setEntityByOrganizationMapper.toDto(setEntityByOrganization);
    }

    @Override
    public Optional<SetEntityByOrganizationDTO> partialUpdate(SetEntityByOrganizationDTO setEntityByOrganizationDTO) {
        LOG.debug("Request to partially update SetEntityByOrganization : {}", setEntityByOrganizationDTO);

        return setEntityByOrganizationRepository
            .findById(setEntityByOrganizationDTO.getOrganizationId())
            .map(existingSetEntityByOrganization -> {
                setEntityByOrganizationMapper.partialUpdate(existingSetEntityByOrganization, setEntityByOrganizationDTO);

                return existingSetEntityByOrganization;
            })
            .map(setEntityByOrganizationRepository::save)
            .map(setEntityByOrganizationMapper::toDto);
    }

    @Override
    public List<SetEntityByOrganizationDTO> findAll() {
        LOG.debug("Request to get all SetEntityByOrganizations");
        return setEntityByOrganizationRepository
            .findAll()
            .stream()
            .map(setEntityByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<SetEntityByOrganizationDTO> findOne(UUID organizationId) {
        LOG.debug("Request to get SetEntityByOrganization : {}", organizationId);
        return setEntityByOrganizationRepository.findById(organizationId).map(setEntityByOrganizationMapper::toDto);
    }

    @Override
    public void delete(UUID organizationId) {
        LOG.debug("Request to delete SetEntityByOrganization : {}", organizationId);
        setEntityByOrganizationRepository.deleteById(organizationId);
    }
}
