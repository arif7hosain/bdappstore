package com.appstore.web.rest;


import com.appstore.domain.User;
import com.appstore.security.SecurityUtils;
import com.codahale.metrics.annotation.Timed;
import com.appstore.domain.CompanyInformation;
import com.appstore.repository.CompanyInformationRepository;
import com.appstore.repository.search.CompanyInformationSearchRepository;
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
 * REST controller for managing CompanyInformation.
 */
@RestController
@RequestMapping("/api")
public class CompanyInformationResource {

    private final Logger log = LoggerFactory.getLogger(CompanyInformationResource.class);

    @Inject
    private CompanyInformationRepository companyInformationRepository;

    @Inject
    private CompanyInformationSearchRepository companyInformationSearchRepository;

    /**
     * POST  /companyInformations -> Create a new companyInformation.
     */
    @RequestMapping(value = "/companyInformations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompanyInformation> createCompanyInformation(@RequestBody CompanyInformation companyInformation) throws URISyntaxException {
        log.debug("REST request to save CompanyInformation : {}", companyInformation);
        if (companyInformation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("companyInformation", "idexists", "A new companyInformation cannot already have an ID")).body(null);
        }
        CompanyInformation result = companyInformationRepository.save(companyInformation);
        companyInformationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/companyInformations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("companyInformation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /companyInformations -> Updates an existing companyInformation.
     */
    @RequestMapping(value = "/companyInformations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompanyInformation> updateCompanyInformation(@RequestBody CompanyInformation companyInformation) throws URISyntaxException {
        log.debug("REST request to update CompanyInformation : {}", companyInformation);
        if (companyInformation.getId() == null) {
            return createCompanyInformation(companyInformation);
        }
        CompanyInformation result = companyInformationRepository.save(companyInformation);
        companyInformationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("companyInformation", companyInformation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /companyInformations -> get all the companyInformations.
     */
    @RequestMapping(value = "/companyInformations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CompanyInformation>> getAllCompanyInformations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CompanyInformations");
        Page<CompanyInformation> page = companyInformationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/companyInformations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /companyInformations/:id -> get the "id" companyInformation.
     */
    @RequestMapping(value = "/companyInformations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompanyInformation> getCompanyInformation(@PathVariable Long id) {
        log.debug("REST request to get CompanyInformation : {}", id);
        CompanyInformation companyInformation = companyInformationRepository.findOne(id);
        return Optional.ofNullable(companyInformation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /companyInformations/:id -> delete the "id" companyInformation.
     */
    @RequestMapping(value = "/companyInformations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCompanyInformation(@PathVariable Long id) {
        log.debug("REST request to delete CompanyInformation : {}", id);
        companyInformationRepository.delete(id);
        companyInformationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("companyInformation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/companyInformations/:query -> search for the companyInformation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/companyInformations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CompanyInformation> searchCompanyInformations(@PathVariable String query) {
        log.debug("REST request to search CompanyInformations for query {}", query);
        return StreamSupport
            .stream(companyInformationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/_search/companyInformations/profile/{login}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public CompanyInformation getCompanyProfileInfoByLogin(@PathVariable String login) {

        log.debug("REST request to search CompanyInformations for query {}", login);
        return companyInformationRepository.getCompampanyByLogin(login);
    }

    @RequestMapping(value = "/_search/companyInformations/info",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public CompanyInformation getCurrentCompany() {
       String login=SecurityUtils.getCurrentUserLogin();
       CompanyInformation companyInformation=companyInformationRepository.getCompampanyByLogin(login);
        if(companyInformation !=null){
            return companyInformation;
        }else
            return null;
    }
}
