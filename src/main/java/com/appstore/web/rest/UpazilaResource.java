package com.appstore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appstore.domain.Upazila;
import com.appstore.repository.UpazilaRepository;
import com.appstore.repository.search.UpazilaSearchRepository;
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
 * REST controller for managing Upazila.
 */
@RestController
@RequestMapping("/api")
public class UpazilaResource {

    private final Logger log = LoggerFactory.getLogger(UpazilaResource.class);
        
    @Inject
    private UpazilaRepository upazilaRepository;
    
    @Inject
    private UpazilaSearchRepository upazilaSearchRepository;
    
    /**
     * POST  /upazilas -> Create a new upazila.
     */
    @RequestMapping(value = "/upazilas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Upazila> createUpazila(@RequestBody Upazila upazila) throws URISyntaxException {
        log.debug("REST request to save Upazila : {}", upazila);
        if (upazila.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("upazila", "idexists", "A new upazila cannot already have an ID")).body(null);
        }
        Upazila result = upazilaRepository.save(upazila);
        upazilaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/upazilas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("upazila", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /upazilas -> Updates an existing upazila.
     */
    @RequestMapping(value = "/upazilas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Upazila> updateUpazila(@RequestBody Upazila upazila) throws URISyntaxException {
        log.debug("REST request to update Upazila : {}", upazila);
        if (upazila.getId() == null) {
            return createUpazila(upazila);
        }
        Upazila result = upazilaRepository.save(upazila);
        upazilaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("upazila", upazila.getId().toString()))
            .body(result);
    }

    /**
     * GET  /upazilas -> get all the upazilas.
     */
    @RequestMapping(value = "/upazilas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Upazila>> getAllUpazilas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Upazilas");
        Page<Upazila> page = upazilaRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/upazilas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /upazilas/:id -> get the "id" upazila.
     */
    @RequestMapping(value = "/upazilas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Upazila> getUpazila(@PathVariable Long id) {
        log.debug("REST request to get Upazila : {}", id);
        Upazila upazila = upazilaRepository.findOne(id);
        return Optional.ofNullable(upazila)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /upazilas/:id -> delete the "id" upazila.
     */
    @RequestMapping(value = "/upazilas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUpazila(@PathVariable Long id) {
        log.debug("REST request to delete Upazila : {}", id);
        upazilaRepository.delete(id);
        upazilaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("upazila", id.toString())).build();
    }

    /**
     * SEARCH  /_search/upazilas/:query -> search for the upazila corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/upazilas/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Upazila> searchUpazilas(@PathVariable String query) {
        log.debug("REST request to search Upazilas for query {}", query);
        return StreamSupport
            .stream(upazilaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
