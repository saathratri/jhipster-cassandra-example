package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.SetEntityByOrganization;
import com.saathratri.developer.blog.service.dto.SetEntityByOrganizationDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SetEntityByOrganization} and its DTO {@link SetEntityByOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface SetEntityByOrganizationMapper extends EntityMapper<SetEntityByOrganizationDTO, SetEntityByOrganization> {
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
