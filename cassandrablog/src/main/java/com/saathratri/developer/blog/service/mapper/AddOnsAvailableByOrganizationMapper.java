package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.AddOnsAvailableByOrganization;
import com.saathratri.developer.blog.service.dto.AddOnsAvailableByOrganizationDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AddOnsAvailableByOrganization} and its DTO {@link AddOnsAvailableByOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddOnsAvailableByOrganizationMapper extends EntityMapper<AddOnsAvailableByOrganizationDTO, AddOnsAvailableByOrganization> {
    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.entityType", source = "compositeId.entityType")
    @Mapping(target = "compositeId.entityId", source = "compositeId.entityId")
    @Mapping(target = "compositeId.addOnId", source = "compositeId.addOnId")
    AddOnsAvailableByOrganization toEntity(AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO);

    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.entityType", source = "compositeId.entityType")
    @Mapping(target = "compositeId.entityId", source = "compositeId.entityId")
    @Mapping(target = "compositeId.addOnId", source = "compositeId.addOnId")
    AddOnsAvailableByOrganizationDTO toDto(AddOnsAvailableByOrganization addOnsAvailableByOrganization);

    default Set<String> map(String value) {
        Set<String> theSet = new TreeSet<String>();
        if (value != null) {
            theSet.add(value);
        }
        return theSet;
    }

    default String map(Set<String> value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value.iterator().next();
    }
}
