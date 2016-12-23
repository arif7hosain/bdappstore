package com.appstore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appstore.domain.ComBranch;
import com.appstore.repository.ComBranchRepository;
import com.appstore.repository.search.ComBranchSearchRepository;
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
 * REST controller for managing ComBranch.
 */
@RestController
@RequestMapping("/api")
public class ComBranchResource {

    private final Logger log = LoggerFactory.getLogger(ComBranchResource.class);
        
    @Inject
    private ComBranchRepository comBranchRepository;
    
    @Inject
    private ComBranchSearchRepository comBranchSearchRepository;
    
    /**
     * POST  /comBranchs -> Create a new comBranch.
     */
    @RequestMapping(value = "/comBranchs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ComBranch> createComBranch(@RequestBody ComBranch comBranch) throws URISyntaxException {
        log.debug("REST request to save ComBranch : {}", comBranch);
        if (comBranch.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("comBranch", "idexists", "A new comBranch cannot already have an ID")).body(null);
        }
        ComBranch result = comBranchRepository.save(comBranch);
        comBranchSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/comBranchs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("comBranch", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comBranchs -> Updates an existing comBranch.
     */
    @RequestMapping(value = "/comBranchs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ComBranch> updateComBranch(@RequestBody ComBranch comBranch) throws URISyntaxException {
        log.debug("REST request to update ComBranch : {}", comBranch);
        if (comBranch.getId() == null) {
            return createComBranch(comBranch);
        }
        ComBranch result = comBranchRepository.save(comBranch);
        comBranchSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("comBranch", comBranch.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comBranchs -> get all the comBranchs.
     */
    @RequestMapping(value = "/comBranchs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ComBranch>> getAllComBranchs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ComBranchs");
        Page<ComBranch> page = comBranchRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comBranchs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comBranchs/:id -> get the "id" comBranch.
     */
    @RequestMapping(value = "/comBranchs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ComBranch> getComBranch(@PathVariable Long id) {
        log.debug("REST request to get ComBranch : {}", id);
        ComBranch comBranch = comBranchRepository.findOne(id);
        return Optional.ofNullable(comBranch)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /comBranchs/:id -> delete the "id" comBranch.
     */
    @RequestMapping(value = "/comBranchs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteComBranch(@PathVariable Long id) {
        log.debug("REST request to delete ComBranch : {}", id);
        comBranchRepository.delete(id);
        comBranchSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("comBranch", id.toString())).build();
    }

    /**
     * SEARCH  /_search/comBranchs/:query -> search for the comBranch corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/comBranchs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ComBranch> searchComBranchs(@PathVariable String query) {
        log.debug("REST request to search ComBranchs for query {}", query);
        return StreamSupport
            .stream(comBranchSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
