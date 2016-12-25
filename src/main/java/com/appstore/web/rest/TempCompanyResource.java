package com.appstore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appstore.domain.TempCompany;
import com.appstore.repository.TempCompanyRepository;
import com.appstore.repository.search.TempCompanySearchRepository;
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
 * REST controller for managing TempCompany.
 */
@RestController
@RequestMapping("/api")
public class TempCompanyResource {

    private final Logger log = LoggerFactory.getLogger(TempCompanyResource.class);
        
    @Inject
    private TempCompanyRepository tempCompanyRepository;
    
    @Inject
    private TempCompanySearchRepository tempCompanySearchRepository;
    
    /**
     * POST  /tempCompanys -> Create a new tempCompany.
     */
    @RequestMapping(value = "/tempCompanys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TempCompany> createTempCompany(@RequestBody TempCompany tempCompany) throws URISyntaxException {
        log.debug("REST request to save TempCompany : {}", tempCompany);
        if (tempCompany.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tempCompany", "idexists", "A new tempCompany cannot already have an ID")).body(null);
        }
        TempCompany result = tempCompanyRepository.save(tempCompany);
        tempCompanySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tempCompanys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tempCompany", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tempCompanys -> Updates an existing tempCompany.
     */
    @RequestMapping(value = "/tempCompanys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TempCompany> updateTempCompany(@RequestBody TempCompany tempCompany) throws URISyntaxException {
        log.debug("REST request to update TempCompany : {}", tempCompany);
        if (tempCompany.getId() == null) {
            return createTempCompany(tempCompany);
        }
        TempCompany result = tempCompanyRepository.save(tempCompany);
        tempCompanySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tempCompany", tempCompany.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tempCompanys -> get all the tempCompanys.
     */
    @RequestMapping(value = "/tempCompanys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TempCompany>> getAllTempCompanys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TempCompanys");
        Page<TempCompany> page = tempCompanyRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tempCompanys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tempCompanys/:id -> get the "id" tempCompany.
     */
    @RequestMapping(value = "/tempCompanys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TempCompany> getTempCompany(@PathVariable Long id) {
        log.debug("REST request to get TempCompany : {}", id);
        TempCompany tempCompany = tempCompanyRepository.findOne(id);
        return Optional.ofNullable(tempCompany)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tempCompanys/:id -> delete the "id" tempCompany.
     */
    @RequestMapping(value = "/tempCompanys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTempCompany(@PathVariable Long id) {
        log.debug("REST request to delete TempCompany : {}", id);
        tempCompanyRepository.delete(id);
        tempCompanySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tempCompany", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tempCompanys/:query -> search for the tempCompany corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/tempCompanys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempCompany> searchTempCompanys(@PathVariable String query) {
        log.debug("REST request to search TempCompanys for query {}", query);
        return StreamSupport
            .stream(tempCompanySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
