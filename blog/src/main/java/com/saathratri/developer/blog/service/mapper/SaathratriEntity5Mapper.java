package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.SaathratriEntity5;
import com.saathratri.developer.blog.service.dto.SaathratriEntity5DTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SaathratriEntity5} and its DTO {@link SaathratriEntity5DTO}.
 */
@Mapper(componentModel = "spring")
public interface SaathratriEntity5Mapper extends EntityMapper<SaathratriEntity5DTO, SaathratriEntity5> {
    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.entityType", source = "compositeId.entityType")
    @Mapping(target = "compositeId.entityId", source = "compositeId.entityId")
    @Mapping(target = "compositeId.addOnId", source = "compositeId.addOnId")
    SaathratriEntity5 toEntity(SaathratriEntity5DTO saathratriEntity5DTO);

    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.entityType", source = "compositeId.entityType")
    @Mapping(target = "compositeId.entityId", source = "compositeId.entityId")
    @Mapping(target = "compositeId.addOnId", source = "compositeId.addOnId")
    SaathratriEntity5DTO toDto(SaathratriEntity5 saathratriEntity5);

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
