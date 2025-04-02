package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.Post;
import com.saathratri.developer.blog.domain.PostId;
import com.saathratri.developer.blog.repository.PostRepository;
import com.saathratri.developer.blog.service.PostService;
import com.saathratri.developer.blog.service.dto.PostDTO;
import com.saathratri.developer.blog.service.mapper.PostMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.Post}.
 */
@Service
public class PostServiceImpl implements PostService {

    private static final Logger LOG = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public PostDTO save(PostDTO postDTO) {
        LOG.debug("Request to save Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        LOG.debug("Saved post : {}", post);
        return postMapper.toDto(post);
    }

    @Override
    public PostDTO update(PostDTO postDTO) {
        LOG.debug("Request to update Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        LOG.debug("Saved post : {}", post);
        return postMapper.toDto(post);
    }

    @Override
    public Optional<PostDTO> partialUpdate(PostDTO postDTO) {
        LOG.debug("Request to partially update Post : {}", postDTO);

        return postRepository
            .findById(
                new PostId(
                    postDTO.getCompositeId().getCreatedDate(),
                    postDTO.getCompositeId().getAddedDateTime(),
                    postDTO.getCompositeId().getPostId()
                )
            )
            .map(existingPost -> {
                postMapper.partialUpdate(existingPost, postDTO);

                return existingPost;
            })
            .map(postRepository::save)
            .map(postMapper::toDto);
    }

    @Override
    public List<PostDTO> findAll() {
        LOG.debug("Request to get all Posts");
        return postRepository.findAll().stream().map(postMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<PostDTO> findOne(PostId compositeId) {
        LOG.debug("Request to get Post : {}", compositeId);
        return postRepository.findById(compositeId).map(postMapper::toDto);
    }

    @Override
    public void delete(PostId compositeId) {
        LOG.debug("Request to delete Post : {}", compositeId);
        postRepository.deleteById(compositeId);
    }

    @Override
    public List<PostDTO> findAllByCompositeIdCreatedDate(final Long createdDate) {
        LOG.debug("Request to findAllByCompositeIdCreatedDate(final Long createdDate) service in PostServiceImpl.");
        return postRepository
            .findAllByCompositeIdCreatedDate(createdDate)
            .stream()
            .map(postMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(final Long createdDate, final Long addedDateTime) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(final Long createdDate, final Long addedDateTime) service in PostServiceImpl."
        );
        return postRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(createdDate, addedDateTime)
            .stream()
            .map(postMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(
        final Long createdDate,
        final Long addedDateTime
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(final Long createdDate, final Long addedDateTime) service in PostServiceImpl."
        );
        return postRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(createdDate, addedDateTime)
            .stream()
            .map(postMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(
        final Long createdDate,
        final Long addedDateTime
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(final Long createdDate, final Long addedDateTime) service in PostServiceImpl."
        );
        return postRepository
            .findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(createdDate, addedDateTime)
            .stream()
            .map(postMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<PostDTO> findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
        final Long createdDate,
        final Long addedDateTime,
        final UUID postId
    ) {
        LOG.debug(
            "Request to findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(final Long createdDate, final Long addedDateTime, final UUID postId) service in PostServiceImpl."
        );
        return postRepository
            .findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(createdDate, addedDateTime, postId)
            .map(postMapper::toDto);
    }
}
