package com.appstore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appstore.domain.SoftwareCategory;
import com.appstore.repository.SoftwareCategoryRepository;
import com.appstore.repository.search.SoftwareCategorySearchRepository;
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
 * REST controller for managing SoftwareCategory.
 */
@RestController
@RequestMapping("/api")
public class SoftwareCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SoftwareCategoryResource.class);
        
    @Inject
    private SoftwareCategoryRepository softwareCategoryRepository;
    
    @Inject
    private SoftwareCategorySearchRepository softwareCategorySearchRepository;
    
    /**
     * POST  /softwareCategorys -> Create a new softwareCategory.
     */
    @RequestMapping(value = "/softwareCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SoftwareCategory> createSoftwareCategory(@RequestBody SoftwareCategory softwareCategory) throws URISyntaxException {
        log.debug("REST request to save SoftwareCategory : {}", softwareCategory);
        if (softwareCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("softwareCategory", "idexists", "A new softwareCategory cannot already have an ID")).body(null);
        }
        SoftwareCategory result = softwareCategoryRepository.save(softwareCategory);
        softwareCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/softwareCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("softwareCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /softwareCategorys -> Updates an existing softwareCategory.
     */
    @RequestMapping(value = "/softwareCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SoftwareCategory> updateSoftwareCategory(@RequestBody SoftwareCategory softwareCategory) throws URISyntaxException {
        log.debug("REST request to update SoftwareCategory : {}", softwareCategory);
        if (softwareCategory.getId() == null) {
            return createSoftwareCategory(softwareCategory);
        }
        SoftwareCategory result = softwareCategoryRepository.save(softwareCategory);
        softwareCategorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("softwareCategory", softwareCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /softwareCategorys -> get all the softwareCategorys.
     */
    @RequestMapping(value = "/softwareCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SoftwareCategory>> getAllSoftwareCategorys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SoftwareCategorys");
        Page<SoftwareCategory> page = softwareCategoryRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/softwareCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /softwareCategorys/:id -> get the "id" softwareCategory.
     */
    @RequestMapping(value = "/softwareCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SoftwareCategory> getSoftwareCategory(@PathVariable Long id) {
        log.debug("REST request to get SoftwareCategory : {}", id);
        SoftwareCategory softwareCategory = softwareCategoryRepository.findOne(id);
        return Optional.ofNullable(softwareCategory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /softwareCategorys/:id -> delete the "id" softwareCategory.
     */
    @RequestMapping(value = "/softwareCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSoftwareCategory(@PathVariable Long id) {
        log.debug("REST request to delete SoftwareCategory : {}", id);
        softwareCategoryRepository.delete(id);
        softwareCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("softwareCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/softwareCategorys/:query -> search for the softwareCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/softwareCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SoftwareCategory> searchSoftwareCategorys(@PathVariable String query) {
        log.debug("REST request to search SoftwareCategorys for query {}", query);
        return StreamSupport
            .stream(softwareCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
