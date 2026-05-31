package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.BlogAsserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.Blog;
import com.saathratri.developer.blog.domain.BlogId;
import com.saathratri.developer.blog.repository.BlogRepository;
import com.saathratri.developer.blog.service.dto.BlogDTO;
import com.saathratri.developer.blog.service.mapper.BlogMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link BlogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BlogResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final UUID DEFAULT_BLOG_ID = UUID.randomUUID();
    private static final UUID UPDATED_BLOG_ID = UUID.randomUUID();

    private static final String DEFAULT_HANDLE = "AAAAAAAAAA";
    private static final String UPDATED_HANDLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/blogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{category}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private MockMvc restBlogMockMvc;

    private Blog blog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Blog createEntity() {
        Blog blog = new Blog()
            .compositeId(new BlogId().category(DEFAULT_CATEGORY).blogId(DEFAULT_BLOG_ID))
            .handle(DEFAULT_HANDLE)
            .content(DEFAULT_CONTENT);
        blog.setCompositeId(new BlogId(DEFAULT_CATEGORY, DEFAULT_BLOG_ID));
        return blog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Blog createUpdatedEntity() {
        Blog blog = new Blog()
            .compositeId(new BlogId().category(UPDATED_CATEGORY).blogId(UPDATED_BLOG_ID))
            .handle(UPDATED_HANDLE)
            .content(UPDATED_CONTENT);
        blog.setCompositeId(new BlogId(UPDATED_CATEGORY, UPDATED_BLOG_ID));
        return blog;
    }

    @BeforeEach
    public void initTest() {
        blogRepository.deleteAll();
        blog = createEntity();
    }

    @Test
    void createBlog() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Blog
        BlogDTO blogDTO = blogMapper.toDto(blog);
        var returnedBlogDTO = om.readValue(
            restBlogMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blogDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BlogDTO.class
        );

        // Validate the Blog in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBlog = blogMapper.toEntity(returnedBlogDTO);
        assertBlogUpdatableFieldsEquals(returnedBlog, getPersistedBlog(returnedBlog));
    }

    @Test
    void createBlogWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        BlogDTO blogDTO = blogMapper.toDto(blog);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restBlogMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blogDTO)))
            .andExpect(status().isCreated());

        // Validate the Blog was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkHandleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        blog.setHandle(null);

        // Create the Blog, which fails.
        BlogDTO blogDTO = blogMapper.toDto(blog);

        restBlogMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkContentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        blog.setContent(null);

        // Create the Blog, which fails.
        BlogDTO blogDTO = blogMapper.toDto(blog);

        restBlogMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllBlogs() throws Exception {
        // Initialize the database
        blog.getCompositeId().setCategory(UUID.randomUUID().toString());
        blog.getCompositeId().setBlogId(UUID.randomUUID());
        blogRepository.save(blog);

        // Get all the blogList
        restBlogMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].compositeId.category").value(hasItem(blog.getCompositeId().getCategory().toString())))
            .andExpect(jsonPath("$.[*].compositeId.blogId").value(hasItem(blog.getCompositeId().getBlogId().toString())))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    void getBlog() throws Exception {
        // Initialize the database
        blog.getCompositeId().setCategory(UUID.randomUUID().toString());
        blog.getCompositeId().setBlogId(UUID.randomUUID());
        blogRepository.save(blog);

        // Get the blog
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(blog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.compositeId.category").value(blog.getCompositeId().getCategory().toString()))
            .andExpect(jsonPath("$.compositeId.blogId").value(blog.getCompositeId().getBlogId().toString()))
            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    void getAllBlogsByCompositeKeySearches() throws Exception {
        // Initialize the database
        blogRepository.save(blog);

        // Exercise every generated composite-key search endpoint (partial-partition findAllBy
        // carry @AllowFiltering, clustering/comparison/findBy are plain valid queries), plus
        // /slice. A 200 confirms the derived CQL + parameter binding executes against real
        // Cassandra; body shape is covered by the get()/getAll() tests above.
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category").param(
                    "category",
                    String.valueOf(blog.getCompositeId().getCategory())
                )
            )
            .andExpect(status().isOk());
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-pageable")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-by-composite-id-category-and-composite-id-blog-id")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(blog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-less-than")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(blog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-pageable")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(blog.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(blog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal-pageable")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(blog.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(blog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-pageable")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(blog.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(blog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal-pageable")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(blog.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-latest-by-composite-id-category").param(
                    "category",
                    String.valueOf(blog.getCompositeId().getCategory())
                )
            )
            .andExpect(status().isOk());
        restBlogMockMvc.perform(get(ENTITY_API_URL + "/slice").param("size", "20")).andExpect(status().isOk());
    }

    @Test
    void getNonExistingBlog() throws Exception {
        // Get the blog
        restBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("category", String.valueOf(blog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(blog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingBlog() throws Exception {
        // Initialize the database
        blog.getCompositeId().setCategory(UUID.randomUUID().toString());
        blog.getCompositeId().setBlogId(UUID.randomUUID());
        blogRepository.save(blog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the blog
        Blog updatedBlog = blogRepository.findById(blog.getCompositeId()).orElseThrow();
        updatedBlog.handle(UPDATED_HANDLE).content(UPDATED_CONTENT);
        BlogDTO blogDTO = blogMapper.toDto(updatedBlog);

        restBlogMockMvc
            .perform(
                put(ENTITY_API_URL + "/{category}/{blogId}", blogDTO.getCompositeId().getCategory(), blogDTO.getCompositeId().getBlogId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(blogDTO))
            )
            .andExpect(status().isOk());

        // Validate the Blog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBlogToMatchAllProperties(updatedBlog);
    }

    @Test
    void putNonExistingBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blog.setCompositeId(new BlogId(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the Blog
        BlogDTO blogDTO = blogMapper.toDto(blog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogMockMvc
            .perform(
                put(ENTITY_API_URL + "/{category}/{blogId}", blogDTO.getCompositeId().getCategory(), blogDTO.getCompositeId().getBlogId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(blogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Blog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blog.setCompositeId(new BlogId(UUID.randomUUID().toString(), UUID.randomUUID()));
        // Create the Blog
        BlogDTO blogDTO = blogMapper.toDto(blog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogMockMvc
            .perform(
                put(ENTITY_API_URL + "/{category}/{blogId}", UUID.randomUUID().toString(), UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(blogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Blog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blog.setCompositeId(new BlogId(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the Blog
        BlogDTO blogDTO = blogMapper.toDto(blog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Blog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateBlogWithPatch() throws Exception {
        // Initialize the database
        blog.getCompositeId().setCategory(UUID.randomUUID().toString());
        blog.getCompositeId().setBlogId(UUID.randomUUID());
        blogRepository.save(blog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the blog using partial update
        Blog partialUpdatedBlog = new Blog();
        partialUpdatedBlog.setCompositeId(blog.getCompositeId());

        partialUpdatedBlog.handle(UPDATED_HANDLE).content(UPDATED_CONTENT);

        restBlogMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{category}/{blogId}",
                    partialUpdatedBlog.getCompositeId().getCategory(),
                    partialUpdatedBlog.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBlog))
            )
            .andExpect(status().isOk());

        // Validate the Blog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBlogUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBlog, blog), getPersistedBlog(blog));
    }

    @Test
    void fullUpdateBlogWithPatch() throws Exception {
        // Initialize the database
        blog.getCompositeId().setCategory(UUID.randomUUID().toString());
        blog.getCompositeId().setBlogId(UUID.randomUUID());
        blogRepository.save(blog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the blog using partial update
        Blog partialUpdatedBlog = new Blog();
        partialUpdatedBlog.setCompositeId(blog.getCompositeId());

        partialUpdatedBlog.handle(UPDATED_HANDLE).content(UPDATED_CONTENT);

        restBlogMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{category}/{blogId}",
                    partialUpdatedBlog.getCompositeId().getCategory(),
                    partialUpdatedBlog.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBlog))
            )
            .andExpect(status().isOk());

        // Validate the Blog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBlogUpdatableFieldsEquals(partialUpdatedBlog, getPersistedBlog(partialUpdatedBlog));
    }

    @Test
    void patchNonExistingBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blog.setCompositeId(new BlogId(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the Blog
        BlogDTO blogDTO = blogMapper.toDto(blog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogMockMvc
            .perform(
                patch(ENTITY_API_URL + "/{category}/{blogId}", blogDTO.getCompositeId().getCategory(), blogDTO.getCompositeId().getBlogId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(blogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Blog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blog.setCompositeId(new BlogId(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the Blog
        BlogDTO blogDTO = blogMapper.toDto(blog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogMockMvc
            .perform(
                patch(ENTITY_API_URL + "/{category}/{blogId}", UUID.randomUUID().toString(), UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(blogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Blog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        blog.setCompositeId(new BlogId(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the Blog
        BlogDTO blogDTO = blogMapper.toDto(blog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(blogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Blog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteBlog() throws Exception {
        // Initialize the database
        blog.getCompositeId().setCategory(UUID.randomUUID().toString());
        blog.getCompositeId().setBlogId(UUID.randomUUID());
        blogRepository.save(blog);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the blog
        restBlogMockMvc
            .perform(
                delete(ENTITY_API_URL + "/{category}/{blogId}", blog.getCompositeId().getCategory(), blog.getCompositeId().getBlogId())
                    .with(csrf())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return blogRepository.count();
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

    protected Blog getPersistedBlog(Blog blog) {
        return blogRepository.findById(blog.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedBlogToMatchAllProperties(Blog expectedBlog) {
        assertBlogAllPropertiesEquals(expectedBlog, getPersistedBlog(expectedBlog));
    }

    protected void assertPersistedBlogToMatchUpdatableProperties(Blog expectedBlog) {
        assertBlogAllUpdatablePropertiesEquals(expectedBlog, getPersistedBlog(expectedBlog));
    }
}
