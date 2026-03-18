package com.saathratri.developer.blog.service.mapper;

import com.saathratri.developer.blog.domain.Post;
import com.saathratri.developer.blog.service.dto.PostDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "compositeId.createdDate", source = "compositeId.createdDate")
    @Mapping(target = "compositeId.addedDateTime", source = "compositeId.addedDateTime")
    @Mapping(target = "compositeId.postId", source = "compositeId.postId")
    Post toEntity(PostDTO postDTO);

    @Mapping(target = "compositeId.createdDate", source = "compositeId.createdDate")
    @Mapping(target = "compositeId.addedDateTime", source = "compositeId.addedDateTime")
    @Mapping(target = "compositeId.postId", source = "compositeId.postId")
    PostDTO toDto(Post post);

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
