package com.appstore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appstore.domain.ServiceCategory;
import com.appstore.repository.ServiceCategoryRepository;
import com.appstore.repository.search.ServiceCategorySearchRepository;
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
 * REST controller for managing ServiceCategory.
 */
@RestController
@RequestMapping("/api")
public class ServiceCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ServiceCategoryResource.class);
        
    @Inject
    private ServiceCategoryRepository serviceCategoryRepository;
    
    @Inject
    private ServiceCategorySearchRepository serviceCategorySearchRepository;
    
    /**
     * POST  /serviceCategorys -> Create a new serviceCategory.
     */
    @RequestMapping(value = "/serviceCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ServiceCategory> createServiceCategory(@RequestBody ServiceCategory serviceCategory) throws URISyntaxException {
        log.debug("REST request to save ServiceCategory : {}", serviceCategory);
        if (serviceCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("serviceCategory", "idexists", "A new serviceCategory cannot already have an ID")).body(null);
        }
        ServiceCategory result = serviceCategoryRepository.save(serviceCategory);
        serviceCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/serviceCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("serviceCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /serviceCategorys -> Updates an existing serviceCategory.
     */
    @RequestMapping(value = "/serviceCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ServiceCategory> updateServiceCategory(@RequestBody ServiceCategory serviceCategory) throws URISyntaxException {
        log.debug("REST request to update ServiceCategory : {}", serviceCategory);
        if (serviceCategory.getId() == null) {
            return createServiceCategory(serviceCategory);
        }
        ServiceCategory result = serviceCategoryRepository.save(serviceCategory);
        serviceCategorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("serviceCategory", serviceCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /serviceCategorys -> get all the serviceCategorys.
     */
    @RequestMapping(value = "/serviceCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ServiceCategory>> getAllServiceCategorys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ServiceCategorys");
        Page<ServiceCategory> page = serviceCategoryRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/serviceCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /serviceCategorys/:id -> get the "id" serviceCategory.
     */
    @RequestMapping(value = "/serviceCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ServiceCategory> getServiceCategory(@PathVariable Long id) {
        log.debug("REST request to get ServiceCategory : {}", id);
        ServiceCategory serviceCategory = serviceCategoryRepository.findOne(id);
        return Optional.ofNullable(serviceCategory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /serviceCategorys/:id -> delete the "id" serviceCategory.
     */
    @RequestMapping(value = "/serviceCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable Long id) {
        log.debug("REST request to delete ServiceCategory : {}", id);
        serviceCategoryRepository.delete(id);
        serviceCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("serviceCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/serviceCategorys/:query -> search for the serviceCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/serviceCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ServiceCategory> searchServiceCategorys(@PathVariable String query) {
        log.debug("REST request to search ServiceCategorys for query {}", query);
        return StreamSupport
            .stream(serviceCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
