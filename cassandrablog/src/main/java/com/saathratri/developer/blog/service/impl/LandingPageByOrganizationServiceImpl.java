package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.LandingPageByOrganization;
import com.saathratri.developer.blog.repository.LandingPageByOrganizationRepository;
import com.saathratri.developer.blog.service.LandingPageByOrganizationService;
import com.saathratri.developer.blog.service.dto.LandingPageByOrganizationDTO;
import com.saathratri.developer.blog.service.mapper.LandingPageByOrganizationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.LandingPageByOrganization}.
 */
@Service
public class LandingPageByOrganizationServiceImpl implements LandingPageByOrganizationService {

    private static final Logger LOG = LoggerFactory.getLogger(LandingPageByOrganizationServiceImpl.class);

    private final LandingPageByOrganizationRepository landingPageByOrganizationRepository;

    private final LandingPageByOrganizationMapper landingPageByOrganizationMapper;

    public LandingPageByOrganizationServiceImpl(
        LandingPageByOrganizationRepository landingPageByOrganizationRepository,
        LandingPageByOrganizationMapper landingPageByOrganizationMapper
    ) {
        this.landingPageByOrganizationRepository = landingPageByOrganizationRepository;
        this.landingPageByOrganizationMapper = landingPageByOrganizationMapper;
    }

    @Override
    public LandingPageByOrganizationDTO save(LandingPageByOrganizationDTO landingPageByOrganizationDTO) {
        LOG.debug("Request to save LandingPageByOrganization : {}", landingPageByOrganizationDTO);
        LandingPageByOrganization landingPageByOrganization = landingPageByOrganizationMapper.toEntity(landingPageByOrganizationDTO);
        landingPageByOrganization = landingPageByOrganizationRepository.save(landingPageByOrganization);
        LOG.debug("Saved landingPageByOrganization : {}", landingPageByOrganization);
        return landingPageByOrganizationMapper.toDto(landingPageByOrganization);
    }

    @Override
    public LandingPageByOrganizationDTO update(LandingPageByOrganizationDTO landingPageByOrganizationDTO) {
        LOG.debug("Request to update LandingPageByOrganization : {}", landingPageByOrganizationDTO);
        LandingPageByOrganization landingPageByOrganization = landingPageByOrganizationMapper.toEntity(landingPageByOrganizationDTO);
        landingPageByOrganization = landingPageByOrganizationRepository.save(landingPageByOrganization);
        LOG.debug("Saved landingPageByOrganization : {}", landingPageByOrganization);
        return landingPageByOrganizationMapper.toDto(landingPageByOrganization);
    }

    @Override
    public Optional<LandingPageByOrganizationDTO> partialUpdate(LandingPageByOrganizationDTO landingPageByOrganizationDTO) {
        LOG.debug("Request to partially update LandingPageByOrganization : {}", landingPageByOrganizationDTO);

        return landingPageByOrganizationRepository
            .findById(landingPageByOrganizationDTO.getOrganizationId())
            .map(existingLandingPageByOrganization -> {
                landingPageByOrganizationMapper.partialUpdate(existingLandingPageByOrganization, landingPageByOrganizationDTO);

                return existingLandingPageByOrganization;
            })
            .map(landingPageByOrganizationRepository::save)
            .map(landingPageByOrganizationMapper::toDto);
    }

    @Override
    public List<LandingPageByOrganizationDTO> findAll() {
        LOG.debug("Request to get all LandingPageByOrganizations");
        return landingPageByOrganizationRepository
            .findAll()
            .stream()
            .map(landingPageByOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<LandingPageByOrganizationDTO> findOne(UUID organizationId) {
        LOG.debug("Request to get LandingPageByOrganization : {}", organizationId);
        return landingPageByOrganizationRepository.findById(organizationId).map(landingPageByOrganizationMapper::toDto);
    }

    @Override
    public void delete(UUID organizationId) {
        LOG.debug("Request to delete LandingPageByOrganization : {}", organizationId);
        landingPageByOrganizationRepository.deleteById(organizationId);
    }
}
