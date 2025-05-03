package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.PostAsserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.Post;
import com.saathratri.developer.blog.domain.PostId;
import com.saathratri.developer.blog.repository.PostRepository;
import com.saathratri.developer.blog.service.dto.PostDTO;
import com.saathratri.developer.blog.service.mapper.PostMapper;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link PostResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PostResourceIT {

    private static final Long DEFAULT_CREATED_DATE = 1L;
    private static final Long UPDATED_CREATED_DATE = 2L;

    private static final Long DEFAULT_ADDED_DATE_TIME = 1L;
    private static final Long UPDATED_ADDED_DATE_TIME = 2L;

    private static final UUID DEFAULT_POST_ID = UUID.randomUUID();
    private static final UUID UPDATED_POST_ID = UUID.randomUUID();

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Long DEFAULT_PUBLISHED_DATE_TIME = 1L;
    private static final Long UPDATED_PUBLISHED_DATE_TIME = 2L;

    private static final Long DEFAULT_SENT_DATE = 1L;
    private static final Long UPDATED_SENT_DATE = 2L;

    private static final String ENTITY_API_URL = "/api/posts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{createdDate}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private MockMvc restPostMockMvc;

    private Post post;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createEntity() {
        Post post = new Post()
            .compositeId(new PostId().createdDate(DEFAULT_CREATED_DATE).addedDateTime(DEFAULT_ADDED_DATE_TIME).postId(DEFAULT_POST_ID))
            .title("title1")
            .content("content1")
            .publishedDateTime(1L)
            .sentDate(1L);
        post.setCompositeId(new PostId(DEFAULT_CREATED_DATE, DEFAULT_ADDED_DATE_TIME, DEFAULT_POST_ID));
        return post;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createUpdatedEntity() {
        Post post = new Post()
            .compositeId(new PostId().createdDate(UPDATED_CREATED_DATE).addedDateTime(UPDATED_ADDED_DATE_TIME).postId(UPDATED_POST_ID))
            .title("title1")
            .content("content1")
            .publishedDateTime(1L)
            .sentDate(1L);
        post.setCompositeId(new PostId(UPDATED_CREATED_DATE, UPDATED_ADDED_DATE_TIME, UPDATED_POST_ID));
        return post;
    }

    @BeforeEach
    public void initTest() {
        postRepository.deleteAll();
        post = createEntity();
    }

    @Test
    void createPost() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);
        var returnedPostDTO = om.readValue(
            restPostMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PostDTO.class
        );

        // Validate the Post in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPost = postMapper.toEntity(returnedPostDTO);
        assertPostUpdatableFieldsEquals(returnedPost, getPersistedPost(returnedPost));
    }

    @Test
    void createPostWithExistingId() throws Exception {
        // Create the Post with an existing ID
        post.setCompositeId(new PostId(DEFAULT_CREATED_DATE, DEFAULT_ADDED_DATE_TIME, DEFAULT_POST_ID));
        PostDTO postDTO = postMapper.toDto(post);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        post.setTitle(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkContentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        post.setContent(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllPosts() throws Exception {
        // Initialize the database
        post.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        post.getCompositeId().setPostId(UUID.randomUUID());
        postRepository.save(post);

        // Get all the postList
        restPostMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].compositeId.createdDate").value(hasItem(post.getCompositeId().getCreatedDate().intValue())))
            .andExpect(jsonPath("$.[*].compositeId.addedDateTime").value(hasItem(post.getCompositeId().getAddedDateTime().intValue())))
            .andExpect(jsonPath("$.[*].compositeId.postId").value(hasItem(post.getCompositeId().getPostId().toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].publishedDateTime").value(hasItem(DEFAULT_PUBLISHED_DATE_TIME.intValue())))
            .andExpect(jsonPath("$.[*].sentDate").value(hasItem(DEFAULT_SENT_DATE.intValue())));
    }

    @Test
    void getPost() throws Exception {
        // Initialize the database
        post.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        post.getCompositeId().setPostId(UUID.randomUUID());
        postRepository.save(post);

        // Get the post
        restPostMockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    post.getCompositeId().getCreatedDate() +
                    "/" +
                    post.getCompositeId().getAddedDateTime() +
                    "/" +
                    post.getCompositeId().getPostId()
                )
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].compositeId.createdDate").value(hasItem(post.getCompositeId().getCreatedDate().intValue())))
            .andExpect(jsonPath("$.[*].compositeId.addedDateTime").value(hasItem(post.getCompositeId().getAddedDateTime().intValue())))
            .andExpect(jsonPath("$.[*].compositeId.postId").value(hasItem(post.getCompositeId().getPostId().toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].publishedDateTime").value(hasItem(DEFAULT_PUBLISHED_DATE_TIME.intValue())))
            .andExpect(jsonPath("$.[*].sentDate").value(hasItem(DEFAULT_SENT_DATE.intValue())));
    }

    @Test
    void getNonExistingPost() throws Exception {
        // Get the post
        restPostMockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    post.getCompositeId().getCreatedDate() +
                    "/" +
                    post.getCompositeId().getAddedDateTime() +
                    "/" +
                    post.getCompositeId().getPostId()
                )
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingPost() throws Exception {
        // Initialize the database
        post.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        post.getCompositeId().setPostId(UUID.randomUUID());
        postRepository.save(post);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the post
        Post updatedPost = postRepository.findById(post.getCompositeId()).orElseThrow();
        updatedPost
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .publishedDateTime(UPDATED_PUBLISHED_DATE_TIME)
            .sentDate(UPDATED_SENT_DATE);
        PostDTO postDTO = postMapper.toDto(updatedPost);

        restPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postDTO).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO))
            )
            .andExpect(status().isOk());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPostToMatchAllProperties(updatedPost);
    }

    @Test
    void putNonExistingPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setCompositeId(new PostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                put(
                    ENTITY_API_URL_ID,
                    post.getCompositeId().getCreatedDate() +
                    "/" +
                    post.getCompositeId().getAddedDateTime() +
                    "/" +
                    post.getCompositeId().getPostId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setCompositeId(new PostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));
        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setCompositeId(new PostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePostWithPatch() throws Exception {
        // Initialize the database
        post.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        post.getCompositeId().setPostId(UUID.randomUUID());
        postRepository.save(post);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the post using partial update
        Post partialUpdatedPost = new Post();
        partialUpdatedPost.setCompositeId(post.getCompositeId());

        partialUpdatedPost
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .publishedDateTime(UPDATED_PUBLISHED_DATE_TIME)
            .sentDate(UPDATED_SENT_DATE);

        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPost.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPost))
            )
            .andExpect(status().isOk());

        // Validate the Post in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPostUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPost, post), getPersistedPost(post));
    }

    @Test
    void fullUpdatePostWithPatch() throws Exception {
        // Initialize the database
        post.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        post.getCompositeId().setPostId(UUID.randomUUID());
        postRepository.save(post);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the post using partial update
        Post partialUpdatedPost = new Post();
        partialUpdatedPost.setCompositeId(post.getCompositeId());

        partialUpdatedPost
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .publishedDateTime(UPDATED_PUBLISHED_DATE_TIME)
            .sentDate(UPDATED_SENT_DATE);

        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPost.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPost))
            )
            .andExpect(status().isOk());

        // Validate the Post in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPostUpdatableFieldsEquals(partialUpdatedPost, getPersistedPost(partialUpdatedPost));
    }

    @Test
    void patchNonExistingPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setCompositeId(new PostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postDTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setCompositeId(new PostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postDTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        post.setCompositeId(new PostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(postDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Post in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePost() throws Exception {
        // Initialize the database
        post.setCompositeId(new PostId());
        post.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        post.getCompositeId().setPostId(UUID.randomUUID());
        postRepository.save(post);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the post
        restPostMockMvc
            .perform(delete(ENTITY_API_URL_ID, post.getCompositeId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return postRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Post getPersistedPost(Post post) {
        return postRepository.findById(post.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedPostToMatchAllProperties(Post expectedPost) {
        assertPostAllPropertiesEquals(expectedPost, getPersistedPost(expectedPost));
    }

    protected void assertPersistedPostToMatchUpdatableProperties(Post expectedPost) {
        assertPostAllUpdatablePropertiesEquals(expectedPost, getPersistedPost(expectedPost));
    }
}
