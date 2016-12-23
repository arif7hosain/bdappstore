package com.appstore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appstore.domain.Division;
import com.appstore.repository.DivisionRepository;
import com.appstore.repository.search.DivisionSearchRepository;
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
 * REST controller for managing Division.
 */
@RestController
@RequestMapping("/api")
public class DivisionResource {

    private final Logger log = LoggerFactory.getLogger(DivisionResource.class);
        
    @Inject
    private DivisionRepository divisionRepository;
    
    @Inject
    private DivisionSearchRepository divisionSearchRepository;
    
    /**
     * POST  /divisions -> Create a new division.
     */
    @RequestMapping(value = "/divisions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Division> createDivision(@RequestBody Division division) throws URISyntaxException {
        log.debug("REST request to save Division : {}", division);
        if (division.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("division", "idexists", "A new division cannot already have an ID")).body(null);
        }
        Division result = divisionRepository.save(division);
        divisionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/divisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("division", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /divisions -> Updates an existing division.
     */
    @RequestMapping(value = "/divisions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Division> updateDivision(@RequestBody Division division) throws URISyntaxException {
        log.debug("REST request to update Division : {}", division);
        if (division.getId() == null) {
            return createDivision(division);
        }
        Division result = divisionRepository.save(division);
        divisionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("division", division.getId().toString()))
            .body(result);
    }

    /**
     * GET  /divisions -> get all the divisions.
     */
    @RequestMapping(value = "/divisions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Division>> getAllDivisions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Divisions");
        Page<Division> page = divisionRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/divisions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /divisions/:id -> get the "id" division.
     */
    @RequestMapping(value = "/divisions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Division> getDivision(@PathVariable Long id) {
        log.debug("REST request to get Division : {}", id);
        Division division = divisionRepository.findOne(id);
        return Optional.ofNullable(division)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /divisions/:id -> delete the "id" division.
     */
    @RequestMapping(value = "/divisions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDivision(@PathVariable Long id) {
        log.debug("REST request to delete Division : {}", id);
        divisionRepository.delete(id);
        divisionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("division", id.toString())).build();
    }

    /**
     * SEARCH  /_search/divisions/:query -> search for the division corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/divisions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Division> searchDivisions(@PathVariable String query) {
        log.debug("REST request to search Divisions for query {}", query);
        return StreamSupport
            .stream(divisionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
