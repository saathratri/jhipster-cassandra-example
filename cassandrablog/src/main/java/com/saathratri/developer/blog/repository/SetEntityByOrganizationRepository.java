package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.SetEntityByOrganization;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the SetEntityByOrganization entity.
 */
@Repository
public interface SetEntityByOrganizationRepository extends CassandraRepository<SetEntityByOrganization, UUID> {}
