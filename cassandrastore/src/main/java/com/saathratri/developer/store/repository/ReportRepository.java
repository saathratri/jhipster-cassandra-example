package com.saathratri.developer.store.repository;

import com.saathratri.developer.store.domain.Report;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the Report entity.
 */
@Repository
public interface ReportRepository extends CassandraRepository<Report, UUID> {}
