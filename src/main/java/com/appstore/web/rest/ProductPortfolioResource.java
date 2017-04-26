package com.appstore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.appstore.domain.ProductPortfolio;
import com.appstore.repository.ProductPortfolioRepository;
import com.appstore.repository.search.ProductPortfolioSearchRepository;
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
 * REST controller for managing ProductPortfolio.
 */
@RestController
@RequestMapping("/api")
public class ProductPortfolioResource {

    private final Logger log = LoggerFactory.getLogger(ProductPortfolioResource.class);

    @Inject
    private ProductPortfolioRepository productPortfolioRepository;

    @Inject
    private ProductPortfolioSearchRepository productPortfolioSearchRepository;

    /**
     * POST  /productPortfolios -> Create a new productPortfolio.
     */
    @RequestMapping(value = "/productPortfolios",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductPortfolio> createProductPortfolio(@RequestBody ProductPortfolio productPortfolio) throws URISyntaxException {
        log.debug("REST request to save ProductPortfolio : {}", productPortfolio);
        if (productPortfolio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productPortfolio", "idexists", "A new productPortfolio cannot already have an ID")).body(null);
        }
        ProductPortfolio result = productPortfolioRepository.save(productPortfolio);
        productPortfolioSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/productPortfolios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("productPortfolio", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productPortfolios -> Updates an existing productPortfolio.
     */
    @RequestMapping(value = "/productPortfolios",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductPortfolio> updateProductPortfolio(@RequestBody ProductPortfolio productPortfolio) throws URISyntaxException {
        log.debug("REST request to update ProductPortfolio : {}", productPortfolio);
        if (productPortfolio.getId() == null) {
            return createProductPortfolio(productPortfolio);
        }
        ProductPortfolio result = productPortfolioRepository.save(productPortfolio);
        productPortfolioSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productPortfolio", productPortfolio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productPortfolios -> get all the productPortfolios.
     */
    @RequestMapping(value = "/productPortfolios",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProductPortfolio>> getAllProductPortfolios(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductPortfolios");
        Page<ProductPortfolio> page = productPortfolioRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productPortfolios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/productPortfolios/getPortfolioByProduct/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ProductPortfolio getAllProductPortfoliosByProduct(@PathVariable Long id)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductPortfolios");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>."+id);
        ProductPortfolio portfolios=productPortfolioRepository.getAllPortfoliosByProduct(id);
        if(portfolios == null){
            return null;
        }
        return portfolios;
    }

    /**
     * GET  /productPortfolios/:id -> get the "id" productPortfolio.
     */
    @RequestMapping(value = "/productPortfolios/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductPortfolio> getProductPortfolio(@PathVariable Long id) {
        log.debug("REST request to get ProductPortfolio : {}", id);
        ProductPortfolio productPortfolio = productPortfolioRepository.findOne(id);
        return Optional.ofNullable(productPortfolio)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productPortfolios/:id -> delete the "id" productPortfolio.
     */
    @RequestMapping(value = "/productPortfolios/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProductPortfolio(@PathVariable Long id) {
        log.debug("REST request to delete ProductPortfolio : {}", id);
        productPortfolioRepository.delete(id);
        productPortfolioSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productPortfolio", id.toString())).build();
    }

    /**
     * SEARCH  /_search/productPortfolios/:query -> search for the productPortfolio corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/productPortfolios/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProductPortfolio> searchProductPortfolios(@PathVariable String query) {
        log.debug("REST request to search ProductPortfolios for query {}", query);
        return StreamSupport
            .stream(productPortfolioSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
