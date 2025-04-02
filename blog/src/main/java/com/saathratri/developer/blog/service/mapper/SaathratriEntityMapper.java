package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.SaathratriEntity;
import com.saathratri.developer.blog.service.dto.SaathratriEntityDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SaathratriEntity} and its DTO {@link SaathratriEntityDTO}.
 */
@Mapper(componentModel = "spring")
public interface SaathratriEntityMapper extends EntityMapper<SaathratriEntityDTO, SaathratriEntity> {
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
