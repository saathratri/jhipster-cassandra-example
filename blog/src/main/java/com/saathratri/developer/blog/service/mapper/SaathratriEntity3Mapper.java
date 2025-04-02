package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.SaathratriEntity3;
import com.saathratri.developer.blog.service.dto.SaathratriEntity3DTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SaathratriEntity3} and its DTO {@link SaathratriEntity3DTO}.
 */
@Mapper(componentModel = "spring")
public interface SaathratriEntity3Mapper extends EntityMapper<SaathratriEntity3DTO, SaathratriEntity3> {
    @Mapping(target = "compositeId.entityType", source = "compositeId.entityType")
    @Mapping(target = "compositeId.createdTimeId", source = "compositeId.createdTimeId")
    SaathratriEntity3 toEntity(SaathratriEntity3DTO saathratriEntity3DTO);

    @Mapping(target = "compositeId.entityType", source = "compositeId.entityType")
    @Mapping(target = "compositeId.createdTimeId", source = "compositeId.createdTimeId")
    SaathratriEntity3DTO toDto(SaathratriEntity3 saathratriEntity3);

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
