package com.appstore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appstore.domain.ComAddress;
import com.appstore.repository.ComAddressRepository;
import com.appstore.repository.search.ComAddressSearchRepository;
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
 * REST controller for managing ComAddress.
 */
@RestController
@RequestMapping("/api")
public class ComAddressResource {

    private final Logger log = LoggerFactory.getLogger(ComAddressResource.class);
        
    @Inject
    private ComAddressRepository comAddressRepository;
    
    @Inject
    private ComAddressSearchRepository comAddressSearchRepository;
    
    /**
     * POST  /comAddresss -> Create a new comAddress.
     */
    @RequestMapping(value = "/comAddresss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ComAddress> createComAddress(@RequestBody ComAddress comAddress) throws URISyntaxException {
        log.debug("REST request to save ComAddress : {}", comAddress);
        if (comAddress.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("comAddress", "idexists", "A new comAddress cannot already have an ID")).body(null);
        }
        ComAddress result = comAddressRepository.save(comAddress);
        comAddressSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/comAddresss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("comAddress", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comAddresss -> Updates an existing comAddress.
     */
    @RequestMapping(value = "/comAddresss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ComAddress> updateComAddress(@RequestBody ComAddress comAddress) throws URISyntaxException {
        log.debug("REST request to update ComAddress : {}", comAddress);
        if (comAddress.getId() == null) {
            return createComAddress(comAddress);
        }
        ComAddress result = comAddressRepository.save(comAddress);
        comAddressSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("comAddress", comAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comAddresss -> get all the comAddresss.
     */
    @RequestMapping(value = "/comAddresss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ComAddress>> getAllComAddresss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ComAddresss");
        Page<ComAddress> page = comAddressRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comAddresss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comAddresss/:id -> get the "id" comAddress.
     */
    @RequestMapping(value = "/comAddresss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ComAddress> getComAddress(@PathVariable Long id) {
        log.debug("REST request to get ComAddress : {}", id);
        ComAddress comAddress = comAddressRepository.findOne(id);
        return Optional.ofNullable(comAddress)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /comAddresss/:id -> delete the "id" comAddress.
     */
    @RequestMapping(value = "/comAddresss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteComAddress(@PathVariable Long id) {
        log.debug("REST request to delete ComAddress : {}", id);
        comAddressRepository.delete(id);
        comAddressSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("comAddress", id.toString())).build();
    }

    /**
     * SEARCH  /_search/comAddresss/:query -> search for the comAddress corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/comAddresss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ComAddress> searchComAddresss(@PathVariable String query) {
        log.debug("REST request to search ComAddresss for query {}", query);
        return StreamSupport
            .stream(comAddressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
