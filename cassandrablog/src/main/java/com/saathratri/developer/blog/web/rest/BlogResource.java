package com.saathratri.developer.blog.web.rest;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.saathratri.developer.blog.domain.BlogId;
import com.saathratri.developer.blog.repository.BlogRepository;
import com.saathratri.developer.blog.service.BlogService;
import com.saathratri.developer.blog.service.dto.BlogDTO;
import com.saathratri.developer.blog.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.saathratri.developer.blog.domain.Blog}.
 */
@RestController
@RequestMapping("/api/blogs")
public class BlogResource {

    private static final Logger LOG = LoggerFactory.getLogger(BlogResource.class);

    private static final String ENTITY_NAME = "cassandrablogBlog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogService blogService;

    private final BlogRepository blogRepository;

    public BlogResource(BlogService blogService, BlogRepository blogRepository) {
        this.blogService = blogService;
        this.blogRepository = blogRepository;
    }

    /**
     * {@code POST  /blogs} : Create a new blog.
     *
     * @param blogDTO the blogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blogDTO, or with status {@code 400 (Bad Request)} if the blog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody BlogDTO blogDTO) throws URISyntaxException {
        LOG.debug("REST request to save Blog : {}", blogDTO);

        // Generate a TimeUUID for the Primary Key composite fields.

        blogDTO.getCompositeId().setBlogId(Uuids.timeBased());

        // Composite Primary Key Code
        if (blogDTO.getCompositeId().getCategory() == null || blogDTO.getCompositeId().getBlogId() == null) {
            throw new BadRequestAlertException("A new blog cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        blogDTO = blogService.save(blogDTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/blogs/" +
                    getUrlEncodedParameterValue(blogDTO.getCompositeId().getCategory()) +
                    "/" +
                    blogDTO.getCompositeId().getBlogId()
            )
        )
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, blogDTO.getCompositeId().toString()))
            .body(blogDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /blogs/:category/:blogId} : Updates an existing blog.
     *
     * // Composite Primary Key Code
     * @param category the Category of the blog to update.
     * @param blogId the Blog Id of the blog to update.
     * @param blogDTO the blogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogDTO,
     * or with status {@code 400 (Bad Request)} if the blogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{category}/{blogId}")
    public ResponseEntity<BlogDTO> updateBlog(
        // Composite Primary Key Code
        @PathVariable(value = "category", required = true) final String category,
        @PathVariable(value = "blogId", required = true) final UUID blogId,
        @Valid @RequestBody BlogDTO blogDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug("REST request to update Blog with parameters category: {}, blogId: {}, blogDTO: {}", category, blogId, blogDTO);
        // Composite Primary Key Code
        if (blogDTO.getCompositeId().getCategory() == null || blogDTO.getCompositeId().getBlogId() == null) {
            throw new BadRequestAlertException("Invalid category", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(category, blogDTO.getCompositeId().getCategory()) ||
            !Objects.equals(blogId, blogDTO.getCompositeId().getBlogId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (!blogRepository.existsById(new BlogId(blogDTO.getCompositeId().getCategory(), blogDTO.getCompositeId().getBlogId()))) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        blogDTO = blogService.update(blogDTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogDTO.getCompositeId().toString()))
            .body(blogDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /blogs/:category/:blogId} : Partial updates given fields of an existing blog, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param category the Category of the blog to partially update.
     * @param blogId the Blog Id of the blog to partially update.
     * @param blogDTO the blogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogDTO,
     * or with status {@code 400 (Bad Request)} if the blogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the blogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the blogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(value = "/{category}/{blogId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BlogDTO> partialUpdateBlog(
        // Composite Primary Key Code
        @PathVariable(value = "category", required = true) final String category,
        @PathVariable(value = "blogId", required = true) final UUID blogId,
        @NotNull @RequestBody BlogDTO blogDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update Blog with the parameters category: {}, blogId: {}, blogDTO: {}",
            category,
            blogId,
            blogDTO
        );
        // Composite Primary Key Code
        if (blogDTO.getCompositeId().getCategory() == null || blogDTO.getCompositeId().getBlogId() == null) {
            throw new BadRequestAlertException("Invalid category", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(category, blogDTO.getCompositeId().getCategory()) ||
            !Objects.equals(blogId, blogDTO.getCompositeId().getBlogId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (!blogRepository.existsById(new BlogId(blogDTO.getCompositeId().getCategory(), blogDTO.getCompositeId().getBlogId()))) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BlogDTO> result = blogService.partialUpdate(blogDTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogDTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /blogs} : get all the blogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogs in body.
     */
    @GetMapping("")
    public List<BlogDTO> getAllBlogs() {
        LOG.debug("REST request to get all Blogs");
        return blogService.findAll();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET  /:category/:blogId} : get the "category" blog.
     *
     * // Composite Primary Key Code
     * @param category the Category of the blog to retrieve.
     * @param blogId the Blog Id of the blog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blogDTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<BlogDTO> getBlog(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug("REST request to get Blog with parameters category: {}, blogId: {}", category, blogId);
        // Composite Primary Key Code
        BlogId compositeId = new BlogId();
        compositeId.setCategory(category);
        compositeId.setBlogId(blogId);

        Optional<BlogDTO> blogDTO = blogService.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(blogDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:category/:blogId} : delete the "compositeId" blog.
     *
     * // Composite Primary Key Code
     * @param category the Category of the blog to delete.
     * @param blogId the Blog Id of the blog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{category}/{blogId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteBlog(
        @PathVariable(value = "category", required = true) final String category,
        @PathVariable(value = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug("REST request to delete Blog with parameters category: {}, blogId: {}", category, blogId);
        // Composite Primary Key Code
        BlogId compositeId = new BlogId();
        compositeId.setCategory(category);
        compositeId.setBlogId(blogId);
        blogService.delete(compositeId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, category))
            .build();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-category/:category}
     *
     *
     * @param category the Category of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Blog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-category")
    public List<BlogDTO> findAllByCompositeIdCategory(@RequestParam(name = "category", required = true) final String category) {
        // Composite Primary Key Code
        LOG.debug("REST request to findAllByCompositeIdCategory method for Blogs with parameteres category: {}", category);
        return blogService.findAllByCompositeIdCategory(category);
    }

    /**
     * {@code GET /find-all-by-composite-id-category-pageable/:category} : get paginated entities by composite key.
     *
     * @param category the Category of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-category-pageable")
    public ResponseEntity<List<BlogDTO>> findAllByCompositeIdCategoryPageable(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated Blogs with parameters category: {}, pagingState: {}, size: {}",
            category,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for Blogs", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<BlogDTO> slice = blogService.findAllByCompositeIdCategoryPageable(category, cassandraPageRequest);

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for Blogs", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-by-composite-id-category-and-composite-id-blog-id/:category/:blogId}
     *
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Blog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-composite-id-category-and-composite-id-blog-id")
    public Optional<BlogDTO> findByCompositeIdCategoryAndCompositeIdBlogId(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdCategoryAndCompositeIdBlogId method for Blogs with parameteres category: {}, blogId: {}",
            category,
            blogId
        );
        return blogService.findByCompositeIdCategoryAndCompositeIdBlogId(category, blogId);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-less-than/:category/:blogId}
     *
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Blog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-less-than")
    public List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan method for Blogs with parameteres category: {}, blogId: {}",
            category,
            blogId
        );
        return blogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(category, blogId);
    }

    /**
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-less-than-pageable/:category/:blogId} : get paginated entities by composite key.
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-pageable")
    public ResponseEntity<List<BlogDTO>> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated Blogs with parameters category: {}, blogId: {}, pagingState: {}, size: {}",
            category,
            blogId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for Blogs", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<BlogDTO> slice = blogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable(
            category,
            blogId,
            cassandraPageRequest
        );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for Blogs", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal/:category/:blogId}
     *
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Blog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal")
    public List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual method for Blogs with parameteres category: {}, blogId: {}",
            category,
            blogId
        );
        return blogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(category, blogId);
    }

    /**
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal-pageable/:category/:blogId} : get paginated entities by composite key.
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal-pageable")
    public ResponseEntity<List<BlogDTO>> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated Blogs with parameters category: {}, blogId: {}, pagingState: {}, size: {}",
            category,
            blogId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for Blogs", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<BlogDTO> slice = blogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable(
            category,
            blogId,
            cassandraPageRequest
        );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for Blogs", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-greater-than/:category/:blogId}
     *
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Blog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than")
    public List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan method for Blogs with parameteres category: {}, blogId: {}",
            category,
            blogId
        );
        return blogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(category, blogId);
    }

    /**
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-pageable/:category/:blogId} : get paginated entities by composite key.
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-pageable")
    public ResponseEntity<List<BlogDTO>> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated Blogs with parameters category: {}, blogId: {}, pagingState: {}, size: {}",
            category,
            blogId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for Blogs", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<BlogDTO> slice = blogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable(
            category,
            blogId,
            cassandraPageRequest
        );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for Blogs", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal/:category/:blogId}
     *
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Blog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal")
    public List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual method for Blogs with parameteres category: {}, blogId: {}",
            category,
            blogId
        );
        return blogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(category, blogId);
    }

    /**
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal-pageable/:category/:blogId} : get paginated entities by composite key.
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal-pageable")
    public ResponseEntity<List<BlogDTO>> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated Blogs with parameters category: {}, blogId: {}, pagingState: {}, size: {}",
            category,
            blogId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for Blogs", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<BlogDTO> slice = blogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable(
            category,
            blogId,
            cassandraPageRequest
        );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for Blogs", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-latest-by-composite-id-category/:category}
     *
     *
     * @param category the Category of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the Blog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-latest-by-composite-id-category")
    public BlogDTO findLatestByCompositeIdCategory(@RequestParam(name = "category", required = true) final String category) {
        // Composite Primary Key Code
        LOG.debug("REST request to findLatestByCompositeIdCategory method for Blogs with parameteres category: {}", category);
        return blogService.findLatestByCompositeIdCategory(category);
    }

    private String getUrlEncodedParameterValue(String parameterValue) {
        String encodedValue = null;
        try {
            encodedValue = URLEncoder.encode(parameterValue, StandardCharsets.UTF_8);
            LOG.info("Encoded String '{}' is '{}'.", parameterValue, encodedValue);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return encodedValue;
    }
}
