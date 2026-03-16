package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.AddOnsSelectedByOrganization;
import com.saathratri.developer.blog.service.dto.AddOnsSelectedByOrganizationDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AddOnsSelectedByOrganization} and its DTO {@link AddOnsSelectedByOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddOnsSelectedByOrganizationMapper extends EntityMapper<AddOnsSelectedByOrganizationDTO, AddOnsSelectedByOrganization> {
    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.arrivalDate", source = "compositeId.arrivalDate")
    @Mapping(target = "compositeId.accountNumber", source = "compositeId.accountNumber")
    @Mapping(target = "compositeId.createdTimeId", source = "compositeId.createdTimeId")
    AddOnsSelectedByOrganization toEntity(AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO);

    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.arrivalDate", source = "compositeId.arrivalDate")
    @Mapping(target = "compositeId.accountNumber", source = "compositeId.accountNumber")
    @Mapping(target = "compositeId.createdTimeId", source = "compositeId.createdTimeId")
    AddOnsSelectedByOrganizationDTO toDto(AddOnsSelectedByOrganization addOnsSelectedByOrganization);

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
