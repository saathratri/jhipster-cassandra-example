package com.saathratri.developer.store.web.rest;

import com.saathratri.developer.store.repository.ProductRepository;
import com.saathratri.developer.store.service.ProductService;
import com.saathratri.developer.store.service.dto.ProductDTO;
import com.saathratri.developer.store.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.saathratri.developer.store.domain.Product}.
 */
@RestController
@RequestMapping("/api/products")
public class ProductResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProductResource.class);

    private static final String ENTITY_NAME = "cassandrastoreProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductService productService;

    private final ProductRepository productRepository;

    public ProductResource(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    /**
     * {@code POST  /products} : Create a new product.
     *
     * @param productDTO the productDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDTO, or with status {@code 400 (Bad Request)} if the product has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        LOG.debug("REST request to save Product : {}", productDTO);

        // Single-value Primary Key Code
        if (productDTO.getId() == null) {
            throw new BadRequestAlertException("A new product must have an ID", ENTITY_NAME, "idinvalid");
        }

        productDTO = productService.save(productDTO);
        // Single-value Primary Key Code
        return ResponseEntity.created(new URI("/api/products/" + productDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, productDTO.getId().toString()))
            .body(productDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PUT  /products/:id} : Updates an existing product.
     *
     * // Single-value Primary Key Code
     * @param id the id of the productDTO to save.
     * @param productDTO the productDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDTO,
     * or with status {@code 400 (Bad Request)} if the productDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
        // Single-value Primary Key Code
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ProductDTO productDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to update Product : {}, {}", id, productDTO);
        // Single-value Primary Key Code
        if (productDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(id, productDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!productRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        productDTO = productService.update(productDTO);
        // Single-value Primary Key Code
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDTO.getId().toString()))
            .body(productDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PATCH  /products/:id} : Partial updates given fields of an existing product, field will ignore if it is null
     *
     * // Single-value Primary Key Code
     * @param id the id of the productDTO to save.
     * @param productDTO the productDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDTO,
     * or with status {@code 400 (Bad Request)} if the productDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductDTO> partialUpdateProduct(
        // Single-value Primary Key Code
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ProductDTO productDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to partial update Product partially : {}, {}", id, productDTO);
        // Single-value Primary Key Code
        if (productDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(id, productDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!productRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductDTO> result = productService.partialUpdate(productDTO);

        // Single-value Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /products} : get all the products.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("")
    public List<ProductDTO> getAllProducts() {
        LOG.debug("REST request to get all Products");
        return productService.findAll();
    }

    /**
     * {@code GET  /products/slice} : get products with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<ProductDTO>> getAllProductsSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of Products, pagingState: {}, size: {}", pagingState, size);

        // Build CassandraPageRequest from pagingState parameter
        org.springframework.data.domain.Pageable cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                java.nio.ByteBuffer pagingStateBuffer;
                try {
                    pagingStateBuffer = java.nio.ByteBuffer.wrap(java.util.Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    pagingStateBuffer = java.nio.ByteBuffer.wrap(java.util.Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(org.springframework.data.domain.PageRequest.of(0, size), pagingStateBuffer);
            } catch (Exception e) {
                LOG.warn("Invalid paging state, starting from beginning", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<ProductDTO> slice = productService.findAllSlice(cassandraPageRequest);
        List<ProductDTO> result = slice.getContent();

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        boolean hasNext = slice.hasNext();
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            java.nio.ByteBuffer nextPagingState = null;
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            if (currentCassandraPageRequest.getPagingState() != null) {
                org.springframework.data.domain.Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            }
            if (nextPagingState != null) {
                byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
                nextPagingState.duplicate().get(pagingStateBytes);
                headers.add("X-Paging-State", java.util.Base64.getUrlEncoder().encodeToString(pagingStateBytes));
            }
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));
        headers.add("Access-Control-Expose-Headers", "X-Has-Next-Page, X-Paging-State, X-Total-Count");

        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * // Single-value Primary Key Code
     * {@code GET  /:id} : get the "id" product.
     *
     * // Single-value Primary Key Code
     * @param id the id of the productDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDTO, or with status {@code 404 (Not Found)}.
     */
    // Single-value Primary Key Code
    @GetMapping("/{id}")
    // Single-value Primary Key Code
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") UUID id) {
        // Single-value Primary Key Code
        LOG.debug("REST request to get Product : {}", id);

        Optional<ProductDTO> productDTO = productService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code DELETE  /:id} : delete the "id" product.
     *
     * // Single-value Primary Key Code
     * @param id the id of the productDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Single-value Primary Key Code
    @DeleteMapping("/{id}")
    // Single-value Primary Key Code
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") UUID id) {
        // Single-value Primary Key Code
        LOG.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
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
