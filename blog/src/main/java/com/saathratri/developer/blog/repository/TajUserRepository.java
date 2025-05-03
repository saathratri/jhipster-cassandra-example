package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.TajUser;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the TajUser entity.
 */
@Repository
public interface TajUserRepository extends CassandraRepository<TajUser, UUID> {}
