package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.TajUser;
import com.saathratri.developer.blog.service.dto.TajUserDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TajUser} and its DTO {@link TajUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface TajUserMapper extends EntityMapper<TajUserDTO, TajUser> {
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
