package com.appstore.web.rest;

import com.appstore.domain.CompanyInformation;
import com.appstore.domain.User;
import com.appstore.repository.CompanyInformationRepository;
import com.appstore.security.SecurityUtils;
import com.appstore.service.UserService;
import com.codahale.metrics.annotation.Timed;
import com.appstore.domain.Product;
import com.appstore.repository.ProductRepository;
import com.appstore.repository.search.ProductSearchRepository;
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
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductSearchRepository productSearchRepository;

    @Inject
    private UserService userService;

    @Inject
    private CompanyInformationRepository companyInformationRepository;


    /**
     * POST  /products -> Create a new product.
     */
    @RequestMapping(value = "/products",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws URISyntaxException {
        log.debug("REST request to save Product : {}", product);
        if (product.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("product", "idexists", "A new product cannot already have an ID")).body(null);
        }

        product.setCompanyInformation(getInfo());
        product.setServiceCategory(getInfo().getServiceCategory());
        product.setIsActive(1);
        product.setView(1);
        Product result = productRepository.save(product);
        productSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("product", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /products -> Updates an existing product.
     */
    @RequestMapping(value = "/products",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws URISyntaxException {
        log.debug("REST request to update Product : {}", product);
        if (product.getId() == null) {
            return createProduct(product);
        }
        Product result = productRepository.save(product);
        productSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("product", product.getId().toString()))
            .body(result);
    }

    /**
     * GET  /products -> get all the products.
     */
    @RequestMapping(value = "/products",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Product>> getAllProducts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Products");
        Page<Product> page = productRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /products -> get all the products.
     */
//    @RequestMapping(value = "/products",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<List<Product>> getAllProductsByCategoryId(Pageable pageable)
//        throws URISyntaxException {
//        log.debug("REST request to get a page of Products");
//        List<Product> products=productRepository.findAll();
//
//        Page<Product> page = productRepository.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    /**
     * GET  /products/:id -> get the "id" product.
     */
    @RequestMapping(value = "/products/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);

        System.out.println(">>>>>>>>>>>>>>.."+id);

        Product product = productRepository.findOne(id);
        System.out.println("<<<<<<<<<<<<<<<<<");
        System.out.println(product);
        System.out.println("<<<<<<<<<<<<<<<<<<");
        return Optional.ofNullable(product)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /products/:id -> delete the "id" product.
     */
    @RequestMapping(value = "/products/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productRepository.delete(id);
        productSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("product", id.toString())).build();
    }

    /**
     * SEARCH  /_search/products/:query -> search for the product corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/products/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Product> searchProducts(@PathVariable String query) {
        log.debug("REST request to search Products for query {}", query);
        return StreamSupport
            .stream(productSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/products/search/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Product> getApps(@PathVariable String query) {
        log.debug("REST request to search Products for query {}", query);
        return StreamSupport
            .stream(productSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    public CompanyInformation getInfo(){
      String username=SecurityUtils.getCurrentUserLogin();
      Optional<User> u=userService.getUserWithAuthoritiesByLogin(username);
      User user=u.get();
      CompanyInformation companyInformation=productRepository.getCompany(user.getId());
      if(companyInformation !=null)
            return companyInformation;
        else
        return null;
    }

    /**
     * GET  /products -> get all the products.
     */
    @RequestMapping(value = "/_search/products/currentUse",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Product> getAllAppsByCurrentUser()
        throws URISyntaxException {
        List<Product> products=null;

        if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")){
            products =productRepository.getActiveProductList();
            return products;
        }
        log.debug("REST request to get a page of Products");
        System.out.println(">>>>>>>>>>..com _ID"+getInfo().getId());
        products=productRepository.getPublisherApps(getInfo().getId());
        if(!products.isEmpty())
            return products;
        else return null;

    }

    /**
     * GET  /products -> get all the products.
     */
    @RequestMapping(value = "/products/addView/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void addView(@PathVariable Long id)
        throws URISyntaxException {
        log.debug("REST request to get a page of Products");
        Product products=productRepository.findOne(id);
        Integer p=products.getView();
        products.setView(p+1);
        Product result = productRepository.save(products);

        productSearchRepository.save(result);

    }

//
//    /**
//     * GET  /products -> get all the products.
//     */
//    @RequestMapping(value = "/_search/products/category/{id}",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public Product getAppByCategory(@PathVariable Long id)
//        throws URISyntaxException {
//        log.debug("REST request to get a page of Products");
//        Product products=productRepository.
//        Integer p=products.getView();
//        products.setView(p+1);
//        Product result = productRepository.save(products);
//        productSearchRepository.save(result);
//
//    }

}
