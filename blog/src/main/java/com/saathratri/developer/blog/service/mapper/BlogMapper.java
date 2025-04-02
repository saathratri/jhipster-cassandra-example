package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.Blog;
import com.saathratri.developer.blog.service.dto.BlogDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Blog} and its DTO {@link BlogDTO}.
 */
@Mapper(componentModel = "spring")
public interface BlogMapper extends EntityMapper<BlogDTO, Blog> {
    @Mapping(target = "compositeId.category", source = "compositeId.category")
    @Mapping(target = "compositeId.blogId", source = "compositeId.blogId")
    Blog toEntity(BlogDTO blogDTO);

    @Mapping(target = "compositeId.category", source = "compositeId.category")
    @Mapping(target = "compositeId.blogId", source = "compositeId.blogId")
    BlogDTO toDto(Blog blog);

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
