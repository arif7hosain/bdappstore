package com.appstore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appstore.domain.ProductCategory;
import com.appstore.repository.ProductCategoryRepository;
import com.appstore.repository.search.ProductCategorySearchRepository;
import com.appstore.web.rest.util.HeaderUtil;
import com.appstore.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProductCategory.
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryResource.class);
        
    @Inject
    private ProductCategoryRepository productCategoryRepository;
    
    @Inject
    private ProductCategorySearchRepository productCategorySearchRepository;
    
    /**
     * POST  /productCategorys -> Create a new productCategory.
     */
    @RequestMapping(value = "/productCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody ProductCategory productCategory) throws URISyntaxException {
        log.debug("REST request to save ProductCategory : {}", productCategory);
        if (productCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productCategory", "idexists", "A new productCategory cannot already have an ID")).body(null);
        }
        ProductCategory result = productCategoryRepository.save(productCategory);
        productCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/productCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("productCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productCategorys -> Updates an existing productCategory.
     */
    @RequestMapping(value = "/productCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductCategory> updateProductCategory(@RequestBody ProductCategory productCategory) throws URISyntaxException {
        log.debug("REST request to update ProductCategory : {}", productCategory);
        if (productCategory.getId() == null) {
            return createProductCategory(productCategory);
        }
        ProductCategory result = productCategoryRepository.save(productCategory);
        productCategorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productCategory", productCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productCategorys -> get all the productCategorys.
     */
    @RequestMapping(value = "/productCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProductCategory>> getAllProductCategorys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductCategorys");
        Page<ProductCategory> page = productCategoryRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /productCategorys/:id -> get the "id" productCategory.
     */
    @RequestMapping(value = "/productCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductCategory> getProductCategory(@PathVariable Long id) {
        log.debug("REST request to get ProductCategory : {}", id);
        ProductCategory productCategory = productCategoryRepository.findOne(id);
        return Optional.ofNullable(productCategory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productCategorys/:id -> delete the "id" productCategory.
     */
    @RequestMapping(value = "/productCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id) {
        log.debug("REST request to delete ProductCategory : {}", id);
        productCategoryRepository.delete(id);
        productCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/productCategorys/:query -> search for the productCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/productCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProductCategory> searchProductCategorys(@PathVariable String query) {
        log.debug("REST request to search ProductCategorys for query {}", query);
        return StreamSupport
            .stream(productCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
