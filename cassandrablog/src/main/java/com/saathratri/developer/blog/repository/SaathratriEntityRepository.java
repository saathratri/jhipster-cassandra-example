package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.SaathratriEntity;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the SaathratriEntity entity.
 */
@Repository
public interface SaathratriEntityRepository extends CassandraRepository<SaathratriEntity, UUID> {}
