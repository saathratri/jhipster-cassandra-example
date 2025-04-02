package com.saathratri.developer.blog.web.rest;

import com.saathratri.developer.blog.domain.PostId;
import com.saathratri.developer.blog.repository.PostRepository;
import com.saathratri.developer.blog.service.PostService;
import com.saathratri.developer.blog.service.dto.PostDTO;
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
 * REST controller for managing {@link com.saathratri.developer.blog.domain.Post}.
 */
@RestController
@RequestMapping("/api/posts")
public class PostResource {

    private static final Logger LOG = LoggerFactory.getLogger(PostResource.class);

    private static final String ENTITY_NAME = "blogPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostService postService;

    private final PostRepository postRepository;

    public PostResource(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    /**
     * {@code POST  /posts} : Create a new post.
     *
     * @param postDTO the postDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postDTO, or with status {@code 400 (Bad Request)} if the post has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) throws URISyntaxException {
        LOG.debug("REST request to save Post : {}", postDTO);
        // Composite Primary Key Code
        if (
            postDTO.getCompositeId().getCreatedDate() == null ||
            postDTO.getCompositeId().getAddedDateTime() == null ||
            postDTO.getCompositeId().getPostId() == null
        ) {
            throw new BadRequestAlertException("A new post cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        postDTO = postService.save(postDTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/posts/" +
                postDTO.getCompositeId().getCreatedDate() +
                "/" +
                postDTO.getCompositeId().getAddedDateTime() +
                "/" +
                postDTO.getCompositeId().getPostId()
            )
        )
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, postDTO.getCompositeId().toString()))
            .body(postDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /posts/:createdDate/:addedDateTime/:postId} : Updates an existing post.
     *
     * // Composite Primary Key Code
     * @param createdDate the Created Date of the post to update.
     * @param addedDateTime the Added Date Time of the post to update.
     * @param postId the Post Id of the post to update.
     * @param postDTO the postDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postDTO,
     * or with status {@code 400 (Bad Request)} if the postDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{createdDate}/{addedDateTime}/{postId}")
    public ResponseEntity<PostDTO> updatePost(
        // Composite Primary Key Code
        @PathVariable(value = "createdDate", required = true) final Long createdDate,
        @PathVariable(value = "addedDateTime", required = true) final Long addedDateTime,
        @PathVariable(value = "postId", required = true) final UUID postId,
        @Valid @RequestBody PostDTO postDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update Post with parameters createdDate: {}, addedDateTime: {}, postId: {}, postDTO: {}",
            createdDate,
            addedDateTime,
            postId,
            postDTO
        );
        // Composite Primary Key Code
        if (
            postDTO.getCompositeId().getCreatedDate() == null ||
            postDTO.getCompositeId().getAddedDateTime() == null ||
            postDTO.getCompositeId().getPostId() == null
        ) {
            throw new BadRequestAlertException("Invalid createdDate", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(createdDate, postDTO.getCompositeId().getCreatedDate()) ||
            !Objects.equals(addedDateTime, postDTO.getCompositeId().getAddedDateTime()) ||
            !Objects.equals(postId, postDTO.getCompositeId().getPostId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !postRepository.existsById(
                new PostId(
                    postDTO.getCompositeId().getCreatedDate(),
                    postDTO.getCompositeId().getAddedDateTime(),
                    postDTO.getCompositeId().getPostId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        postDTO = postService.update(postDTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postDTO.getCompositeId().toString()))
            .body(postDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /posts/:createdDate/:addedDateTime/:postId} : Partial updates given fields of an existing post, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param createdDate the Created Date of the post to partially update.
     * @param addedDateTime the Added Date Time of the post to partially update.
     * @param postId the Post Id of the post to partially update.
     * @param postDTO the postDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postDTO,
     * or with status {@code 400 (Bad Request)} if the postDTO is not valid,
     * or with status {@code 404 (Not Found)} if the postDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the postDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(value = "/{createdDate}/{addedDateTime}/{postId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PostDTO> partialUpdatePost(
        // Composite Primary Key Code
        @PathVariable(value = "createdDate", required = true) final Long createdDate,
        @PathVariable(value = "addedDateTime", required = true) final Long addedDateTime,
        @PathVariable(value = "postId", required = true) final UUID postId,
        @NotNull @RequestBody PostDTO postDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update Post with the parameters createdDate: {}, addedDateTime: {}, postId: {}, postDTO: {}",
            createdDate,
            addedDateTime,
            postId,
            postDTO
        );
        // Composite Primary Key Code
        if (
            postDTO.getCompositeId().getCreatedDate() == null ||
            postDTO.getCompositeId().getAddedDateTime() == null ||
            postDTO.getCompositeId().getPostId() == null
        ) {
            throw new BadRequestAlertException("Invalid createdDate", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(createdDate, postDTO.getCompositeId().getCreatedDate()) ||
            !Objects.equals(addedDateTime, postDTO.getCompositeId().getAddedDateTime()) ||
            !Objects.equals(postId, postDTO.getCompositeId().getPostId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !postRepository.existsById(
                new PostId(
                    postDTO.getCompositeId().getCreatedDate(),
                    postDTO.getCompositeId().getAddedDateTime(),
                    postDTO.getCompositeId().getPostId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PostDTO> result = postService.partialUpdate(postDTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postDTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /posts} : get all the posts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of posts in body.
     */
    @GetMapping("")
    public List<PostDTO> getAllPosts() {
        LOG.debug("REST request to get all Posts");
        return postService.findAll();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET  /:createdDate/:addedDateTime/:postId} : get the "createdDate" post.
     *
     * // Composite Primary Key Code
     * @param createdDate the Created Date of the post to retrieve.
     * @param addedDateTime the Added Date Time of the post to retrieve.
     * @param postId the Post Id of the post to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postDTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<PostDTO> getPost(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime,
        @RequestParam(name = "postId", required = true) final UUID postId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get Post with parameters createdDate: {}, addedDateTime: {}, postId: {}",
            createdDate,
            addedDateTime,
            postId
        );
        // Composite Primary Key Code
        PostId compositeId = new PostId();
        compositeId.setCreatedDate(createdDate);
        compositeId.setAddedDateTime(addedDateTime);
        compositeId.setPostId(postId);

        Optional<PostDTO> postDTO = postService.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(postDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:createdDate/:addedDateTime/:postId} : delete the "compositeId" post.
     *
     * // Composite Primary Key Code
     * @param createdDate the Created Date of the post to delete.
     * @param addedDateTime the Added Date Time of the post to delete.
     * @param postId the Post Id of the post to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{createdDate}/{addedDateTime}/{postId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deletePost(
        @PathVariable(value = "createdDate", required = true) final Long createdDate,
        @PathVariable(value = "addedDateTime", required = true) final Long addedDateTime,
        @PathVariable(value = "postId", required = true) final UUID postId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete Post with parameters createdDate: {}, addedDateTime: {}, postId: {}",
            createdDate,
            addedDateTime,
            postId
        );
        // Composite Primary Key Code
        PostId compositeId = new PostId();
        compositeId.setCreatedDate(createdDate);
        compositeId.setAddedDateTime(addedDateTime);
        compositeId.setPostId(postId);
        postService.delete(compositeId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, createdDate.toString()))
            .build();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-created-date/:createdDate}
     *
     *
     * @param createdDate the Created Date of the post to retrieve. *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the post, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-created-date")
    public List<PostDTO> findAllByCompositeIdCreatedDate(@RequestParam(name = "createdDate", required = true) final Long createdDate) {
        // Composite Primary Key Code
        LOG.debug("REST request to findAllByCompositeIdCreatedDate method for Posts with parameteres createdDate: {}", createdDate);
        return postService.findAllByCompositeIdCreatedDate(createdDate);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time/:createdDate/:addedDateTime}
     *
     *
     * @param createdDate the Created Date of the post to retrieve.
     * @param addedDateTime the Added Date Time of the post to retrieve. *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the post, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time")
    public List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime method for Posts with parameteres createdDate: {}, addedDateTime: {}",
            createdDate,
            addedDateTime
        );
        return postService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(createdDate, addedDateTime);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than/:createdDate/:addedDateTime}
     *
     *
     * @param createdDate the Created Date of the post to retrieve.
     * @param addedDateTime the Added Date Time of the post to retrieve. *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the post, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than")
    public List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan method for Posts with parameteres createdDate: {}, addedDateTime: {}",
            createdDate,
            addedDateTime
        );
        return postService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(createdDate, addedDateTime);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than/:createdDate/:addedDateTime}
     *
     *
     * @param createdDate the Created Date of the post to retrieve.
     * @param addedDateTime the Added Date Time of the post to retrieve. *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the post, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than")
    public List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan method for Posts with parameteres createdDate: {}, addedDateTime: {}",
            createdDate,
            addedDateTime
        );
        return postService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(createdDate, addedDateTime);
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
