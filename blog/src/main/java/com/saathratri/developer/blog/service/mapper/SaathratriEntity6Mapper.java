package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.SaathratriEntity6;
import com.saathratri.developer.blog.service.dto.SaathratriEntity6DTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SaathratriEntity6} and its DTO {@link SaathratriEntity6DTO}.
 */
@Mapper(componentModel = "spring")
public interface SaathratriEntity6Mapper extends EntityMapper<SaathratriEntity6DTO, SaathratriEntity6> {
    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.arrivalDate", source = "compositeId.arrivalDate")
    @Mapping(target = "compositeId.accountNumber", source = "compositeId.accountNumber")
    @Mapping(target = "compositeId.createdTimeId", source = "compositeId.createdTimeId")
    SaathratriEntity6 toEntity(SaathratriEntity6DTO saathratriEntity6DTO);

    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.arrivalDate", source = "compositeId.arrivalDate")
    @Mapping(target = "compositeId.accountNumber", source = "compositeId.accountNumber")
    @Mapping(target = "compositeId.createdTimeId", source = "compositeId.createdTimeId")
    SaathratriEntity6DTO toDto(SaathratriEntity6 saathratriEntity6);

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
