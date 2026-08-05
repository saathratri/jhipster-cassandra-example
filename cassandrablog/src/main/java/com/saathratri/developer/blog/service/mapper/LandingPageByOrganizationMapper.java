package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.LandingPageByOrganization;
import com.saathratri.developer.blog.service.dto.LandingPageByOrganizationDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LandingPageByOrganization} and its DTO {@link LandingPageByOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LandingPageByOrganizationMapper extends EntityMapper<LandingPageByOrganizationDTO, LandingPageByOrganization> {
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
