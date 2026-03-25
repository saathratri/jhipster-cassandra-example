package com.saathratri.developer.blog.service.mapper;

import com.datastax.oss.driver.api.core.data.CqlVector;
import com.saathratri.developer.blog.domain.Tag;
import com.saathratri.developer.blog.service.dto.TagDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {
    @Mapping(target = "nameEmbedding", ignore = true)
    @Mapping(target = "descriptionEmbedding", ignore = true)
    Tag toEntity(TagDTO tagDTO);

    @Mapping(target = "nameEmbedding", ignore = true)
    @Mapping(target = "descriptionEmbedding", ignore = true)
    void partialUpdate(@MappingTarget Tag entity, TagDTO dto);

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

    default List<Float> mapCqlVectorToList(CqlVector<Float> vector) {
        if (vector == null) {
            return null;
        }
        List<Float> list = new ArrayList<>();
        for (Float f : vector) {
            list.add(f);
        }
        return list;
    }
}
