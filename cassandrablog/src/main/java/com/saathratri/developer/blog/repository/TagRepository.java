package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.Tag;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the Tag entity.
 */
@Repository
public interface TagRepository extends CassandraRepository<Tag, UUID> {}
