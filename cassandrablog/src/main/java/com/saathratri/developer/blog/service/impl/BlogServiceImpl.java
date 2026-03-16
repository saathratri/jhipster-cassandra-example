package com.saathratri.developer.blog.service.impl;

import com.saathratri.developer.blog.domain.Blog;
import com.saathratri.developer.blog.domain.BlogId;
import com.saathratri.developer.blog.repository.BlogRepository;
import com.saathratri.developer.blog.service.BlogService;
import com.saathratri.developer.blog.service.dto.BlogDTO;
import com.saathratri.developer.blog.service.mapper.BlogMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.saathratri.developer.blog.domain.Blog}.
 */
@Service
public class BlogServiceImpl implements BlogService {

    private static final Logger LOG = LoggerFactory.getLogger(BlogServiceImpl.class);

    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;

    public BlogServiceImpl(BlogRepository blogRepository, BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
    }

    @Override
    public BlogDTO save(BlogDTO blogDTO) {
        LOG.debug("Request to save Blog : {}", blogDTO);
        Blog blog = blogMapper.toEntity(blogDTO);
        blog = blogRepository.save(blog);
        LOG.debug("Saved blog : {}", blog);
        return blogMapper.toDto(blog);
    }

    @Override
    public BlogDTO update(BlogDTO blogDTO) {
        LOG.debug("Request to update Blog : {}", blogDTO);
        Blog blog = blogMapper.toEntity(blogDTO);
        blog = blogRepository.save(blog);
        LOG.debug("Saved blog : {}", blog);
        return blogMapper.toDto(blog);
    }

    @Override
    public Optional<BlogDTO> partialUpdate(BlogDTO blogDTO) {
        LOG.debug("Request to partially update Blog : {}", blogDTO);

        return blogRepository
            .findById(new BlogId(blogDTO.getCompositeId().getCategory(), blogDTO.getCompositeId().getBlogId()))
            .map(existingBlog -> {
                blogMapper.partialUpdate(existingBlog, blogDTO);

                return existingBlog;
            })
            .map(blogRepository::save)
            .map(blogMapper::toDto);
    }

    @Override
    public List<BlogDTO> findAll() {
        LOG.debug("Request to get all Blogs");
        return blogRepository.findAll().stream().map(blogMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<BlogDTO> findOne(BlogId compositeId) {
        LOG.debug("Request to get Blog : {}", compositeId);
        return blogRepository.findById(compositeId).map(blogMapper::toDto);
    }

    @Override
    public void delete(BlogId compositeId) {
        LOG.debug("Request to delete Blog : {}", compositeId);
        blogRepository.deleteById(compositeId);
    }

    @Override
    public List<BlogDTO> findAllByCompositeIdCategory(final String category) {
        LOG.debug("Request to findAllByCompositeIdCategory(final String category) service in BlogServiceImpl.");
        return blogRepository
            .findAllByCompositeIdCategory(category)
            .stream()
            .map(blogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<BlogDTO> findAllByCompositeIdCategoryPageable(final String category, Pageable pageable) {
        LOG.debug("Request to findAllByCompositeIdCategoryPageable service in BlogServiceImpl with pagination.");
        return blogRepository.findAllByCompositeIdCategory(category, pageable).map(blogMapper::toDto);
    }

    @Override
    public Optional<BlogDTO> findByCompositeIdCategoryAndCompositeIdBlogId(final String category, final UUID blogId) {
        LOG.debug(
            "Request to findByCompositeIdCategoryAndCompositeIdBlogId(final String category, final UUID blogId) service in BlogServiceImpl."
        );
        return blogRepository.findByCompositeIdCategoryAndCompositeIdBlogId(category, blogId).map(blogMapper::toDto);
    }

    @Override
    public List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(final String category, final UUID blogId) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(final String category, final UUID blogId) service in BlogServiceImpl."
        );
        return blogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(category, blogId)
            .stream()
            .map(blogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable service in BlogServiceImpl with pagination."
        );
        return blogRepository.findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(category, blogId, pageable).map(blogMapper::toDto);
    }

    @Override
    public List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(final String category, final UUID blogId) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(final String category, final UUID blogId) service in BlogServiceImpl."
        );
        return blogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(category, blogId)
            .stream()
            .map(blogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable service in BlogServiceImpl with pagination."
        );
        return blogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(category, blogId, pageable)
            .map(blogMapper::toDto);
    }

    @Override
    public List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(final String category, final UUID blogId) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(final String category, final UUID blogId) service in BlogServiceImpl."
        );
        return blogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(category, blogId)
            .stream()
            .map(blogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable service in BlogServiceImpl with pagination."
        );
        return blogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(category, blogId, pageable)
            .map(blogMapper::toDto);
    }

    @Override
    public List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(final String category, final UUID blogId) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(final String category, final UUID blogId) service in BlogServiceImpl."
        );
        return blogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(category, blogId)
            .stream()
            .map(blogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Slice<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    ) {
        LOG.debug(
            "Request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable service in BlogServiceImpl with pagination."
        );
        return blogRepository
            .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(category, blogId, pageable)
            .map(blogMapper::toDto);
    }

    @Override
    public BlogDTO findLatestByCompositeIdCategory(final String category) {
        LOG.debug("Request to findLatestByCompositeIdCategory(final String category) service in BlogServiceImpl.");
        return blogRepository.findLatestByCompositeIdCategory(category).map(blogMapper::toDto).orElse(null); // Return null if no record found
    }
}
