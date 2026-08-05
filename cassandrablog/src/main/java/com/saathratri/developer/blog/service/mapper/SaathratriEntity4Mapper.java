package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.SaathratriEntity4;
import com.saathratri.developer.blog.service.dto.SaathratriEntity4DTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SaathratriEntity4} and its DTO {@link SaathratriEntity4DTO}.
 */
@Mapper(componentModel = "spring")
public interface SaathratriEntity4Mapper extends EntityMapper<SaathratriEntity4DTO, SaathratriEntity4> {
    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.attributeKey", source = "compositeId.attributeKey")
    SaathratriEntity4 toEntity(SaathratriEntity4DTO saathratriEntity4DTO);

    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.attributeKey", source = "compositeId.attributeKey")
    SaathratriEntity4DTO toDto(SaathratriEntity4 saathratriEntity4);

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
