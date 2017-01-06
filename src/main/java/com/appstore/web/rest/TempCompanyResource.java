package com.appstore.web.rest;

import com.appstore.domain.*;
import com.appstore.domain.enumeration.BranchType;
import com.appstore.repository.*;
import com.appstore.repository.search.ComAddressSearchRepository;
import com.appstore.repository.search.ComBranchSearchRepository;
import com.appstore.repository.search.CompanyInformationSearchRepository;
import com.appstore.web.rest.dto.UserDTO;
import com.codahale.metrics.annotation.Timed;
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

    @Inject
    private CompanyInformationRepository companyInformationRepository;

    @Inject
    private CompanyInformationSearchRepository companyInformationSearchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ComBranchRepository comBranchRepository;

    @Inject
    private ComBranchSearchRepository comBranchSearchRepository;

    @Inject
    private ComAddressRepository comAddressRepository;

    @Inject
    private ComAddressSearchRepository comAddressSearchRepository;



    User user =new User();


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

    @RequestMapping(value = "/_search/tempCompanys/approveCompany/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public TempCompany approveCompany(@PathVariable Long id) {
      log.debug("REST request to search TempCompanys for query {}", id);
        TempCompany temp=tempCompanyRepository.findOne(id);
        System.out.print("<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>");
        System.out.print(temp);
//        User currentUser=userRepository.getUserByEmail(temp.getEmail());
        Optional<User> currentUser=userRepository.findOneByEmail(temp.getEmail());
        User u=currentUser.get();
        System.out.print("user.............>start");
        System.out.print(u);
        System.out.print("user.............>end");
        temp.setActiveStatus(1);

        TempCompany tepmResult = tempCompanyRepository.save(temp);
        tempCompanySearchRepository.save(tepmResult);

       //company create
        CompanyInformation com=new CompanyInformation();
        com.setActiveStatus(1);
        com.setCompanyName(temp.getCompanyName());
        com.setShortName(temp.getShortName());
        com.setShortDescription(temp.getShortDescription());
        com.setBusinessDescription(temp.getBusinessDescription());
        com.setCompanyInformation(temp.getCompanyInformation());
//        com.getCompanyType(temp.getCompanyType());
        com.setLogo(temp.getLogo());
        com.setLogoContentType(temp.getLogoContentType());
        com.setWebsite(temp.getWebsite());
        com.setUser(u);
        com.setCountry(temp.getCountry());
        com.setServiceCategory(temp.getServiceCategory());
        CompanyInformation ci =companyInformationRepository.save(com);
        companyInformationSearchRepository.save(ci);
        //create branch

        System.out.print("c.........."+ci);
        ComBranch branch=new ComBranch();
        branch.setBranchName(temp.getBranchName());
        branch.setCountry(temp.getCountry());
        branch.setCity(temp.getCity());
        branch.setCompanyInformation(ci);
        System.out.print("---------------->" + BranchType.Headquarter + "<----------------");
//        if(temp.getBranchType().equalsIgnoreCase("Headquarter")){
//            branch.setBranchType(BranchType.Headquarter);
//        }else{
//            branch.setBranchType(BranchType.Regional);
//       }
        ComBranch comBranch=comBranchRepository.save(branch);
        comBranchSearchRepository.save(comBranch);


        //address inforamation
        ComAddress address=new ComAddress();
        address.setCity(temp.getCity());
        address.setOfficePhone(temp.getOfficePhone());
        address.setAddressType(temp.getAddressType());
        address.setContactNumber(temp.getContactNumber());
        address.setHouse(temp.getHouse());
        address.setComBranch(comBranch);
        ComAddress comAddress=comAddressRepository.save(address);
        comAddressSearchRepository.save(comAddress);


        return tepmResult;
    }

}
