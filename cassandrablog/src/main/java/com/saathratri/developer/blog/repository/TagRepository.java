package com.saathratri.developer.blog.repository;

import com.datastax.oss.driver.api.core.data.CqlVector;
import com.saathratri.developer.blog.domain.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the Tag entity.
 */
@Repository
public interface TagRepository extends CassandraRepository<Tag, UUID> {
    @Query("SELECT * FROM tag ORDER BY name_embedding ANN OF ?0 LIMIT ?1")
    List<Tag> findSimilarByNameEmbedding(CqlVector<Float> queryVector, int limit);

    @Query("SELECT * FROM tag ORDER BY description_embedding ANN OF ?0 LIMIT ?1")
    List<Tag> findSimilarByDescriptionEmbedding(CqlVector<Float> queryVector, int limit);
}
