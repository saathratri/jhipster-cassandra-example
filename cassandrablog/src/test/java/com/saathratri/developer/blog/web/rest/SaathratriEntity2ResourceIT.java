package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.SaathratriEntity2Asserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static com.saathratri.developer.blog.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.SaathratriEntity2;
import com.saathratri.developer.blog.domain.SaathratriEntity2Id;
import com.saathratri.developer.blog.repository.SaathratriEntity2Repository;
import com.saathratri.developer.blog.service.dto.SaathratriEntity2DTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntity2Mapper;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link SaathratriEntity2Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaathratriEntity2ResourceIT {

    private static final UUID DEFAULT_ENTITY_TYPE_ID = UUID.randomUUID();
    private static final UUID UPDATED_ENTITY_TYPE_ID = UUID.randomUUID();

    private static final Long DEFAULT_YEAR_OF_DATE_ADDED = 1L;
    private static final Long UPDATED_YEAR_OF_DATE_ADDED = 2L;

    private static final Long DEFAULT_ARRIVAL_DATE = 1L;
    private static final Long UPDATED_ARRIVAL_DATE = 2L;

    private static final UUID DEFAULT_BLOG_ID = UUID.randomUUID();
    private static final UUID UPDATED_BLOG_ID = UUID.randomUUID();

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ENTITY_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_ENTITY_COST = new BigDecimal(2);

    private static final Long DEFAULT_DEPARTURE_DATE = 1L;
    private static final Long UPDATED_DEPARTURE_DATE = 2L;

    private static final String ENTITY_API_URL = "/api/saathratri-entity-2-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{entityTypeId}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SaathratriEntity2Repository saathratriEntity2Repository;

    @Autowired
    private SaathratriEntity2Mapper saathratriEntity2Mapper;

    @Autowired
    private MockMvc restSaathratriEntity2MockMvc;

    private SaathratriEntity2 saathratriEntity2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity2 createEntity() {
        SaathratriEntity2 saathratriEntity2 = new SaathratriEntity2()
            .compositeId(
                new SaathratriEntity2Id()
                    .entityTypeId(DEFAULT_ENTITY_TYPE_ID)
                    .yearOfDateAdded(DEFAULT_YEAR_OF_DATE_ADDED)
                    .arrivalDate(DEFAULT_ARRIVAL_DATE)
                    .blogId(DEFAULT_BLOG_ID)
            )
            .entityName(DEFAULT_ENTITY_NAME)
            .entityDescription(DEFAULT_ENTITY_DESCRIPTION)
            .entityCost(DEFAULT_ENTITY_COST)
            .departureDate(DEFAULT_DEPARTURE_DATE);
        saathratriEntity2.setCompositeId(
            new SaathratriEntity2Id(DEFAULT_ENTITY_TYPE_ID, DEFAULT_YEAR_OF_DATE_ADDED, DEFAULT_ARRIVAL_DATE, DEFAULT_BLOG_ID)
        );
        return saathratriEntity2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity2 createUpdatedEntity() {
        SaathratriEntity2 saathratriEntity2 = new SaathratriEntity2()
            .compositeId(
                new SaathratriEntity2Id()
                    .entityTypeId(UPDATED_ENTITY_TYPE_ID)
                    .yearOfDateAdded(UPDATED_YEAR_OF_DATE_ADDED)
                    .arrivalDate(UPDATED_ARRIVAL_DATE)
                    .blogId(UPDATED_BLOG_ID)
            )
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE);
        saathratriEntity2.setCompositeId(
            new SaathratriEntity2Id(UPDATED_ENTITY_TYPE_ID, UPDATED_YEAR_OF_DATE_ADDED, UPDATED_ARRIVAL_DATE, UPDATED_BLOG_ID)
        );
        return saathratriEntity2;
    }

    @BeforeEach
    public void initTest() {
        saathratriEntity2Repository.deleteAll();
        saathratriEntity2 = createEntity();
    }

    @Test
    void createSaathratriEntity2() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SaathratriEntity2
        SaathratriEntity2DTO saathratriEntity2DTO = saathratriEntity2Mapper.toDto(saathratriEntity2);
        var returnedSaathratriEntity2DTO = om.readValue(
            restSaathratriEntity2MockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(saathratriEntity2DTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SaathratriEntity2DTO.class
        );

        // Validate the SaathratriEntity2 in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSaathratriEntity2 = saathratriEntity2Mapper.toEntity(returnedSaathratriEntity2DTO);
        assertSaathratriEntity2UpdatableFieldsEquals(returnedSaathratriEntity2, getPersistedSaathratriEntity2(returnedSaathratriEntity2));
    }

    @Test
    void createSaathratriEntity2WithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        SaathratriEntity2DTO saathratriEntity2DTO = saathratriEntity2Mapper.toDto(saathratriEntity2);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restSaathratriEntity2MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity2DTO))
            )
            .andExpect(status().isCreated());

        // Validate the SaathratriEntity2 was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSaathratriEntity2s() throws Exception {
        // Initialize the database
        saathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        saathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        saathratriEntity2Repository.save(saathratriEntity2);

        // Get all the saathratriEntity2List
        restSaathratriEntity2MockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            .andExpect(
                jsonPath("$.[*].compositeId.entityTypeId").value(hasItem(saathratriEntity2.getCompositeId().getEntityTypeId().toString()))
            )

            .andExpect(
                jsonPath("$.[*].compositeId.yearOfDateAdded").value(
                    hasItem(saathratriEntity2.getCompositeId().getYearOfDateAdded().intValue())
                )
            )

            .andExpect(
                jsonPath("$.[*].compositeId.arrivalDate").value(hasItem(saathratriEntity2.getCompositeId().getArrivalDate().intValue()))
            )

            .andExpect(jsonPath("$.[*].compositeId.blogId").value(hasItem(saathratriEntity2.getCompositeId().getBlogId().toString())))

            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))

            .andExpect(jsonPath("$.[*].entityDescription").value(hasItem(DEFAULT_ENTITY_DESCRIPTION)))

            .andExpect(jsonPath("$.[*].entityCost").value(hasItem(sameNumber(DEFAULT_ENTITY_COST))))

            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.intValue())));
    }

    @Test
    void getSaathratriEntity2() throws Exception {
        // Initialize the database
        saathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        saathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        saathratriEntity2Repository.save(saathratriEntity2);

        // Get the saathratriEntity2
        restSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(saathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            .andExpect(jsonPath("$.compositeId.entityTypeId").value(saathratriEntity2.getCompositeId().getEntityTypeId().toString()))

            .andExpect(jsonPath("$.compositeId.yearOfDateAdded").value(saathratriEntity2.getCompositeId().getYearOfDateAdded().intValue()))

            .andExpect(jsonPath("$.compositeId.arrivalDate").value(saathratriEntity2.getCompositeId().getArrivalDate().intValue()))

            .andExpect(jsonPath("$.compositeId.blogId").value(saathratriEntity2.getCompositeId().getBlogId().toString()))

            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))

            .andExpect(jsonPath("$.entityDescription").value(DEFAULT_ENTITY_DESCRIPTION))

            .andExpect(jsonPath("$.entityCost").value(sameNumber(DEFAULT_ENTITY_COST)))

            .andExpect(jsonPath("$.departureDate").value(DEFAULT_DEPARTURE_DATE.intValue()));
    }

    @Test
    void getAllSaathratriEntity2sByCompositeKeySearches() throws Exception {
        // Initialize the database
        saathratriEntity2Repository.save(saathratriEntity2);

        // Exercise every generated composite-key search endpoint (partial-partition findAllBy
        // carry @AllowFiltering, clustering/comparison/findBy are plain valid queries), plus
        // /slice. A 200 confirms the derived CQL + parameter binding executes against real
        // Cassandra; body shape is covered by the get()/getAll() tests above.
        restSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-id").param(
                    "entityTypeId",
                    String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId())
                )
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-id-pageable")
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added")
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-pageable")
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-pageable"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-pageable"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-equal"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-equal-pageable"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-pageable"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-equal"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-equal-pageable"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(saathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(saathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-pageable"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(saathratriEntity2.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-equal"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(saathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-equal-pageable"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(saathratriEntity2.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(saathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-pageable"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(saathratriEntity2.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-equal"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(saathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-equal-pageable"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(saathratriEntity2.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-latest-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date"
                )
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restSaathratriEntity2MockMvc.perform(get(ENTITY_API_URL + "/slice").param("size", "20")).andExpect(status().isOk());
    }

    @Test
    void getNonExistingSaathratriEntity2() throws Exception {
        // Get the saathratriEntity2
        restSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("entityTypeId", String.valueOf(saathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(saathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(saathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(saathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingSaathratriEntity2() throws Exception {
        // Initialize the database
        saathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        saathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        saathratriEntity2Repository.save(saathratriEntity2);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity2
        SaathratriEntity2 updatedSaathratriEntity2 = saathratriEntity2Repository.findById(saathratriEntity2.getCompositeId()).orElseThrow();
        updatedSaathratriEntity2

            .entityName(UPDATED_ENTITY_NAME)

            .entityDescription(UPDATED_ENTITY_DESCRIPTION)

            .entityCost(UPDATED_ENTITY_COST)

            .departureDate(UPDATED_DEPARTURE_DATE);
        SaathratriEntity2DTO saathratriEntity2DTO = saathratriEntity2Mapper.toDto(updatedSaathratriEntity2);

        restSaathratriEntity2MockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    saathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    saathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    saathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    saathratriEntity2DTO.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity2DTO))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSaathratriEntity2ToMatchAllProperties(updatedSaathratriEntity2);
    }

    @Test
    void putNonExistingSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity2.setCompositeId(
            new SaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );

        // Create the SaathratriEntity2
        SaathratriEntity2DTO saathratriEntity2DTO = saathratriEntity2Mapper.toDto(saathratriEntity2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntity2MockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    saathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    saathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    saathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    saathratriEntity2DTO.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity2.setCompositeId(
            new SaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );
        // Create the SaathratriEntity2
        SaathratriEntity2DTO saathratriEntity2DTO = saathratriEntity2Mapper.toDto(saathratriEntity2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity2MockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    UUID.randomUUID(),
                    longCount.incrementAndGet(),
                    longCount.incrementAndGet(),
                    UUID.randomUUID()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity2.setCompositeId(
            new SaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );

        // Create the SaathratriEntity2
        SaathratriEntity2DTO saathratriEntity2DTO = saathratriEntity2Mapper.toDto(saathratriEntity2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity2MockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(saathratriEntity2DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSaathratriEntity2WithPatch() throws Exception {
        // Initialize the database
        saathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        saathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        saathratriEntity2Repository.save(saathratriEntity2);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity2 using partial update
        SaathratriEntity2 partialUpdatedSaathratriEntity2 = new SaathratriEntity2();
        partialUpdatedSaathratriEntity2.setCompositeId(saathratriEntity2.getCompositeId());

        partialUpdatedSaathratriEntity2

            .entityName(UPDATED_ENTITY_NAME)

            .entityDescription(UPDATED_ENTITY_DESCRIPTION)

            .entityCost(UPDATED_ENTITY_COST)

            .departureDate(UPDATED_DEPARTURE_DATE);

        restSaathratriEntity2MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    partialUpdatedSaathratriEntity2.getCompositeId().getEntityTypeId(),
                    partialUpdatedSaathratriEntity2.getCompositeId().getYearOfDateAdded(),
                    partialUpdatedSaathratriEntity2.getCompositeId().getArrivalDate(),
                    partialUpdatedSaathratriEntity2.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity2))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity2 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntity2UpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSaathratriEntity2, saathratriEntity2),
            getPersistedSaathratriEntity2(saathratriEntity2)
        );
    }

    @Test
    void fullUpdateSaathratriEntity2WithPatch() throws Exception {
        // Initialize the database
        saathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        saathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        saathratriEntity2Repository.save(saathratriEntity2);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity2 using partial update
        SaathratriEntity2 partialUpdatedSaathratriEntity2 = new SaathratriEntity2();
        partialUpdatedSaathratriEntity2.setCompositeId(saathratriEntity2.getCompositeId());

        partialUpdatedSaathratriEntity2

            .entityName(UPDATED_ENTITY_NAME)

            .entityDescription(UPDATED_ENTITY_DESCRIPTION)

            .entityCost(UPDATED_ENTITY_COST)

            .departureDate(UPDATED_DEPARTURE_DATE);

        restSaathratriEntity2MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    partialUpdatedSaathratriEntity2.getCompositeId().getEntityTypeId(),
                    partialUpdatedSaathratriEntity2.getCompositeId().getYearOfDateAdded(),
                    partialUpdatedSaathratriEntity2.getCompositeId().getArrivalDate(),
                    partialUpdatedSaathratriEntity2.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity2))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity2 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntity2UpdatableFieldsEquals(
            partialUpdatedSaathratriEntity2,
            getPersistedSaathratriEntity2(partialUpdatedSaathratriEntity2)
        );
    }

    @Test
    void patchNonExistingSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity2.setCompositeId(
            new SaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );

        // Create the SaathratriEntity2
        SaathratriEntity2DTO saathratriEntity2DTO = saathratriEntity2Mapper.toDto(saathratriEntity2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntity2MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    saathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    saathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    saathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    saathratriEntity2DTO.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity2.setCompositeId(
            new SaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );

        // Create the SaathratriEntity2
        SaathratriEntity2DTO saathratriEntity2DTO = saathratriEntity2Mapper.toDto(saathratriEntity2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity2MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    UUID.randomUUID(),
                    longCount.incrementAndGet(),
                    longCount.incrementAndGet(),
                    UUID.randomUUID()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity2.setCompositeId(
            new SaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );

        // Create the SaathratriEntity2
        SaathratriEntity2DTO saathratriEntity2DTO = saathratriEntity2Mapper.toDto(saathratriEntity2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity2MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity2DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSaathratriEntity2() throws Exception {
        // Initialize the database
        saathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        saathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        saathratriEntity2Repository.save(saathratriEntity2);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the saathratriEntity2
        restSaathratriEntity2MockMvc
            .perform(
                delete(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    saathratriEntity2.getCompositeId().getEntityTypeId(),
                    saathratriEntity2.getCompositeId().getYearOfDateAdded(),
                    saathratriEntity2.getCompositeId().getArrivalDate(),
                    saathratriEntity2.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return saathratriEntity2Repository.count();
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

    protected SaathratriEntity2 getPersistedSaathratriEntity2(SaathratriEntity2 saathratriEntity2) {
        return saathratriEntity2Repository.findById(saathratriEntity2.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedSaathratriEntity2ToMatchAllProperties(SaathratriEntity2 expectedSaathratriEntity2) {
        assertSaathratriEntity2AllPropertiesEquals(expectedSaathratriEntity2, getPersistedSaathratriEntity2(expectedSaathratriEntity2));
    }

    protected void assertPersistedSaathratriEntity2ToMatchUpdatableProperties(SaathratriEntity2 expectedSaathratriEntity2) {
        assertSaathratriEntity2AllUpdatablePropertiesEquals(
            expectedSaathratriEntity2,
            getPersistedSaathratriEntity2(expectedSaathratriEntity2)
        );
    }
}
