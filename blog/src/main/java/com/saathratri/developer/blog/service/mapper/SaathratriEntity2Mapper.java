package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.SaathratriEntity2;
import com.saathratri.developer.blog.service.dto.SaathratriEntity2DTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SaathratriEntity2} and its DTO {@link SaathratriEntity2DTO}.
 */
@Mapper(componentModel = "spring")
public interface SaathratriEntity2Mapper extends EntityMapper<SaathratriEntity2DTO, SaathratriEntity2> {
    @Mapping(target = "compositeId.entityTypeId", source = "compositeId.entityTypeId")
    @Mapping(target = "compositeId.yearOfDateAdded", source = "compositeId.yearOfDateAdded")
    @Mapping(target = "compositeId.arrivalDate", source = "compositeId.arrivalDate")
    @Mapping(target = "compositeId.blogId", source = "compositeId.blogId")
    SaathratriEntity2 toEntity(SaathratriEntity2DTO saathratriEntity2DTO);

    @Mapping(target = "compositeId.entityTypeId", source = "compositeId.entityTypeId")
    @Mapping(target = "compositeId.yearOfDateAdded", source = "compositeId.yearOfDateAdded")
    @Mapping(target = "compositeId.arrivalDate", source = "compositeId.arrivalDate")
    @Mapping(target = "compositeId.blogId", source = "compositeId.blogId")
    SaathratriEntity2DTO toDto(SaathratriEntity2 saathratriEntity2);

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
