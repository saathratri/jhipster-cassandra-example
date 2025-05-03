package com.saathratri.developer.store.repository;

import com.saathratri.developer.store.domain.Product;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the Product entity.
 */
@Repository
public interface ProductRepository extends CassandraRepository<Product, UUID> {}
