package com.saathratri.developer.blog.service.impl;

import com.datastax.oss.driver.api.core.data.CqlVector;
import com.saathratri.developer.blog.domain.Tag;
import com.saathratri.developer.blog.repository.TagRepository;
import com.saathratri.developer.blog.service.TagService;
import com.saathratri.developer.blog.service.dto.TagDTO;
import com.saathratri.developer.blog.service.embedding.EmbeddingService;
import com.saathratri.developer.blog.service.mapper.TagMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.Tag}.
 */
@Service
public class TagServiceImpl implements TagService {

    private static final Logger LOG = LoggerFactory.getLogger(TagServiceImpl.class);

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    private final EmbeddingService embeddingService;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper, EmbeddingService embeddingService) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.embeddingService = embeddingService;
    }

    @Override
    public TagDTO save(TagDTO tagDTO) {
        LOG.debug("Request to save Tag : {}", tagDTO);
        Tag tag = tagMapper.toEntity(tagDTO);
        // Generate vector embeddings for AI search
        if (embeddingService.isAvailable()) {
            {
                String sourceValue = tag.getName();
                if (sourceValue != null && !sourceValue.isBlank()) {
                    float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                    if (embeddingArray != null) {
                        java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                        for (float f : embeddingArray) {
                            floatList.add(f);
                        }
                        tag.setNameEmbedding(CqlVector.newInstance(floatList));
                    }
                }
            }
            {
                String sourceValue = tag.getDescription();
                if (sourceValue != null && !sourceValue.isBlank()) {
                    float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                    if (embeddingArray != null) {
                        java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                        for (float f : embeddingArray) {
                            floatList.add(f);
                        }
                        tag.setDescriptionEmbedding(CqlVector.newInstance(floatList));
                    }
                }
            }
        }
        tag = tagRepository.save(tag);
        LOG.debug("Saved tag : {}", tag);
        return tagMapper.toDto(tag);
    }

    @Override
    public TagDTO update(TagDTO tagDTO) {
        LOG.debug("Request to update Tag : {}", tagDTO);
        Tag tag = tagMapper.toEntity(tagDTO);
        // Generate vector embeddings for AI search
        if (embeddingService.isAvailable()) {
            {
                String sourceValue = tag.getName();
                if (sourceValue != null && !sourceValue.isBlank()) {
                    float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                    if (embeddingArray != null) {
                        java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                        for (float f : embeddingArray) {
                            floatList.add(f);
                        }
                        tag.setNameEmbedding(CqlVector.newInstance(floatList));
                    }
                }
            }
            {
                String sourceValue = tag.getDescription();
                if (sourceValue != null && !sourceValue.isBlank()) {
                    float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                    if (embeddingArray != null) {
                        java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                        for (float f : embeddingArray) {
                            floatList.add(f);
                        }
                        tag.setDescriptionEmbedding(CqlVector.newInstance(floatList));
                    }
                }
            }
        }
        tag = tagRepository.save(tag);
        LOG.debug("Saved tag : {}", tag);
        return tagMapper.toDto(tag);
    }

    @Override
    public Optional<TagDTO> partialUpdate(TagDTO tagDTO) {
        LOG.debug("Request to partially update Tag : {}", tagDTO);

        return tagRepository
            .findById(tagDTO.getId())
            .map(existingTag -> {
                tagMapper.partialUpdate(existingTag, tagDTO);

                // Regenerate vector embeddings for AI search
                if (embeddingService.isAvailable()) {
                    {
                        String sourceValue = existingTag.getName();
                        if (sourceValue != null && !sourceValue.isBlank()) {
                            float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                            if (embeddingArray != null) {
                                java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                                for (float f : embeddingArray) {
                                    floatList.add(f);
                                }
                                existingTag.setNameEmbedding(CqlVector.newInstance(floatList));
                            }
                        }
                    }
                    {
                        String sourceValue = existingTag.getDescription();
                        if (sourceValue != null && !sourceValue.isBlank()) {
                            float[] embeddingArray = embeddingService.generateEmbedding(sourceValue);
                            if (embeddingArray != null) {
                                java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
                                for (float f : embeddingArray) {
                                    floatList.add(f);
                                }
                                existingTag.setDescriptionEmbedding(CqlVector.newInstance(floatList));
                            }
                        }
                    }
                }
                return existingTag;
            })
            .map(tagRepository::save)
            .map(tagMapper::toDto);
    }

    @Override
    public List<TagDTO> findAll() {
        LOG.debug("Request to get all Tags");
        return tagRepository.findAll().stream().map(tagMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<TagDTO> findOne(UUID id) {
        LOG.debug("Request to get Tag : {}", id);
        return tagRepository.findById(id).map(tagMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete Tag : {}", id);
        tagRepository.deleteById(id);
    }

    // ==================== AI Text Search ====================

    @Override
    public List<TagDTO> aiSearch(String query, int limit, List<String> fields) {
        LOG.debug("Request to AI search Tags for query: {}, limit: {}, fields: {}", query, limit, fields);

        if (query == null || query.isBlank()) {
            return List.of();
        }

        if (!embeddingService.isAvailable()) {
            LOG.warn("Embedding service not available for AI search, returning empty results");
            return List.of();
        }

        float[] embeddingArray = embeddingService.generateEmbedding(query);
        if (embeddingArray == null) {
            LOG.warn("Failed to generate embedding for AI search query, returning empty results");
            return List.of();
        }

        // Convert float[] to CqlVector<Float>
        java.util.List<Float> floatList = new java.util.ArrayList<>(embeddingArray.length);
        for (float f : embeddingArray) {
            floatList.add(f);
        }
        CqlVector<Float> queryVector = CqlVector.newInstance(floatList);

        boolean searchAll = (fields == null || fields.isEmpty());
        java.util.Map<String, TagDTO> resultMap = new java.util.LinkedHashMap<>();

        // Search by nameEmbedding
        if (searchAll || fields.contains("nameEmbedding")) {
            tagRepository
                .findSimilarByNameEmbedding(queryVector, limit)
                .stream()
                .map(tagMapper::toDto)
                .forEach(item -> resultMap.putIfAbsent(String.valueOf(item.getId()), item));
        }

        // Search by descriptionEmbedding
        if (searchAll || fields.contains("descriptionEmbedding")) {
            tagRepository
                .findSimilarByDescriptionEmbedding(queryVector, limit)
                .stream()
                .map(tagMapper::toDto)
                .forEach(item -> resultMap.putIfAbsent(String.valueOf(item.getId()), item));
        }

        return new java.util.ArrayList<>(resultMap.values());
    }

    // ==================== Vector Similarity Search Methods ====================

    @Override
    public List<TagDTO> findSimilarByNameEmbedding(CqlVector<Float> embedding, int limit) {
        LOG.debug("Request to find Tags similar by nameEmbedding, limit: {}", limit);
        return tagRepository
            .findSimilarByNameEmbedding(embedding, limit)
            .stream()
            .map(tagMapper::toDto)
            .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<TagDTO> findSimilarByDescriptionEmbedding(CqlVector<Float> embedding, int limit) {
        LOG.debug("Request to find Tags similar by descriptionEmbedding, limit: {}", limit);
        return tagRepository
            .findSimilarByDescriptionEmbedding(embedding, limit)
            .stream()
            .map(tagMapper::toDto)
            .collect(java.util.stream.Collectors.toList());
    }
}
