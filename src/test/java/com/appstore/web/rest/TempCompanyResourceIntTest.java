package com.appstore.web.rest;

import com.appstore.Application;
import com.appstore.domain.TempCompany;
import com.appstore.repository.TempCompanyRepository;
import com.appstore.repository.search.TempCompanySearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.appstore.domain.enumeration.CompanyType;
import com.appstore.domain.enumeration.AddressType;

/**
 * Test class for the TempCompanyResource REST controller.
 *
 * @see TempCompanyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TempCompanyResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAA";
    private static final String UPDATED_USERNAME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_PASSWORD = "AAAAA";
    private static final String UPDATED_PASSWORD = "BBBBB";
    private static final String DEFAULT_BRANCH_NAME = "AAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBB";
    private static final String DEFAULT_BRANCH_TYPE = "AAAAA";
    private static final String UPDATED_BRANCH_TYPE = "BBBBB";
    private static final String DEFAULT_COMPANY_NAME = "AAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBB";
    private static final String DEFAULT_SHORT_NAME = "AAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBB";
    private static final String DEFAULT_COMPANY_INFORMATION = "AAAAA";
    private static final String UPDATED_COMPANY_INFORMATION = "BBBBB";
    private static final String DEFAULT_BUSINESS_DESCRIPTION = "AAAAA";
    private static final String UPDATED_BUSINESS_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_FACEBOOK = "AAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBB";
    private static final String DEFAULT_GOOGLE_PLUS = "AAAAA";
    private static final String UPDATED_GOOGLE_PLUS = "BBBBB";
    private static final String DEFAULT_YOUTUBE = "AAAAA";
    private static final String UPDATED_YOUTUBE = "BBBBB";
    private static final String DEFAULT_LINKEDIN = "AAAAA";
    private static final String UPDATED_LINKEDIN = "BBBBB";
    private static final String DEFAULT_TWITTER = "AAAAA";
    private static final String UPDATED_TWITTER = "BBBBB";
    private static final String DEFAULT_WEBSITE = "AAAAA";
    private static final String UPDATED_WEBSITE = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";
    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";


    private static final CompanyType DEFAULT_COMPANY_TYPE = CompanyType.National;
    private static final CompanyType UPDATED_COMPANY_TYPE = CompanyType.International;


    private static final AddressType DEFAULT_ADDRESS_TYPE = AddressType.National;
    private static final AddressType UPDATED_ADDRESS_TYPE = AddressType.International;
    private static final String DEFAULT_OFFICE_PHONE = "AAAAA";
    private static final String UPDATED_OFFICE_PHONE = "BBBBB";
    private static final String DEFAULT_CONTACT_NUMBER = "AAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBB";
    private static final String DEFAULT_POSTAL_CODE = "AAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBB";
    private static final String DEFAULT_HOUSE = "AAAAA";
    private static final String UPDATED_HOUSE = "BBBBB";
    private static final String DEFAULT_ROAD_NO = "AAAAA";
    private static final String UPDATED_ROAD_NO = "BBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CREATE_BY = "AAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBB";
    private static final String DEFAULT_UPDATED_BY = "AAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBB";

    private static final Integer DEFAULT_ACTIVE_STATUS = 1;
    private static final Integer UPDATED_ACTIVE_STATUS = 2;

    @Inject
    private TempCompanyRepository tempCompanyRepository;

    @Inject
    private TempCompanySearchRepository tempCompanySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTempCompanyMockMvc;

    private TempCompany tempCompany;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TempCompanyResource tempCompanyResource = new TempCompanyResource();
        ReflectionTestUtils.setField(tempCompanyResource, "tempCompanySearchRepository", tempCompanySearchRepository);
        ReflectionTestUtils.setField(tempCompanyResource, "tempCompanyRepository", tempCompanyRepository);
        this.restTempCompanyMockMvc = MockMvcBuilders.standaloneSetup(tempCompanyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tempCompany = new TempCompany();
        tempCompany.setUsername(DEFAULT_USERNAME);
        tempCompany.setEmail(DEFAULT_EMAIL);
        tempCompany.setPassword(DEFAULT_PASSWORD);
        tempCompany.setBranchName(DEFAULT_BRANCH_NAME);
        tempCompany.setBranchType(DEFAULT_BRANCH_TYPE);
        tempCompany.setCompanyName(DEFAULT_COMPANY_NAME);
        tempCompany.setShortName(DEFAULT_SHORT_NAME);
        tempCompany.setCompanyInformation(DEFAULT_COMPANY_INFORMATION);
        tempCompany.setBusinessDescription(DEFAULT_BUSINESS_DESCRIPTION);
        tempCompany.setFacebook(DEFAULT_FACEBOOK);
        tempCompany.setGooglePlus(DEFAULT_GOOGLE_PLUS);
        tempCompany.setYoutube(DEFAULT_YOUTUBE);
        tempCompany.setLinkedin(DEFAULT_LINKEDIN);
        tempCompany.setTwitter(DEFAULT_TWITTER);
        tempCompany.setWebsite(DEFAULT_WEBSITE);
        tempCompany.setCity(DEFAULT_CITY);
        tempCompany.setShortDescription(DEFAULT_SHORT_DESCRIPTION);
        tempCompany.setLogo(DEFAULT_LOGO);
        tempCompany.setLogoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        tempCompany.setCompanyType(DEFAULT_COMPANY_TYPE);
        tempCompany.setAddressType(DEFAULT_ADDRESS_TYPE);
        tempCompany.setOfficePhone(DEFAULT_OFFICE_PHONE);
        tempCompany.setContactNumber(DEFAULT_CONTACT_NUMBER);
        tempCompany.setPostalCode(DEFAULT_POSTAL_CODE);
        tempCompany.setHouse(DEFAULT_HOUSE);
        tempCompany.setRoadNo(DEFAULT_ROAD_NO);
        tempCompany.setCreatedDate(DEFAULT_CREATED_DATE);
        tempCompany.setUpdatedDate(DEFAULT_UPDATED_DATE);
        tempCompany.setCreateBy(DEFAULT_CREATE_BY);
        tempCompany.setUpdatedBy(DEFAULT_UPDATED_BY);
        tempCompany.setActiveStatus(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void createTempCompany() throws Exception {
        int databaseSizeBeforeCreate = tempCompanyRepository.findAll().size();

        // Create the TempCompany

        restTempCompanyMockMvc.perform(post("/api/tempCompanys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempCompany)))
                .andExpect(status().isCreated());

        // Validate the TempCompany in the database
        List<TempCompany> tempCompanys = tempCompanyRepository.findAll();
        assertThat(tempCompanys).hasSize(databaseSizeBeforeCreate + 1);
        TempCompany testTempCompany = tempCompanys.get(tempCompanys.size() - 1);
        assertThat(testTempCompany.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testTempCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTempCompany.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testTempCompany.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testTempCompany.getBranchType()).isEqualTo(DEFAULT_BRANCH_TYPE);
        assertThat(testTempCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testTempCompany.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testTempCompany.getCompanyInformation()).isEqualTo(DEFAULT_COMPANY_INFORMATION);
        assertThat(testTempCompany.getBusinessDescription()).isEqualTo(DEFAULT_BUSINESS_DESCRIPTION);
        assertThat(testTempCompany.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testTempCompany.getGooglePlus()).isEqualTo(DEFAULT_GOOGLE_PLUS);
        assertThat(testTempCompany.getYoutube()).isEqualTo(DEFAULT_YOUTUBE);
        assertThat(testTempCompany.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testTempCompany.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testTempCompany.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testTempCompany.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testTempCompany.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testTempCompany.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testTempCompany.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testTempCompany.getCompanyType()).isEqualTo(DEFAULT_COMPANY_TYPE);
        assertThat(testTempCompany.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
        assertThat(testTempCompany.getOfficePhone()).isEqualTo(DEFAULT_OFFICE_PHONE);
        assertThat(testTempCompany.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testTempCompany.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testTempCompany.getHouse()).isEqualTo(DEFAULT_HOUSE);
        assertThat(testTempCompany.getRoadNo()).isEqualTo(DEFAULT_ROAD_NO);
        assertThat(testTempCompany.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTempCompany.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testTempCompany.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testTempCompany.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTempCompany.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void getAllTempCompanys() throws Exception {
        // Initialize the database
        tempCompanyRepository.saveAndFlush(tempCompany);

        // Get all the tempCompanys
        restTempCompanyMockMvc.perform(get("/api/tempCompanys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tempCompany.getId().intValue())))
                .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
                .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME.toString())))
                .andExpect(jsonPath("$.[*].BranchType").value(hasItem(DEFAULT_BRANCH_TYPE.toString())))
                .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
                .andExpect(jsonPath("$.[*].companyInformation").value(hasItem(DEFAULT_COMPANY_INFORMATION.toString())))
                .andExpect(jsonPath("$.[*].businessDescription").value(hasItem(DEFAULT_BUSINESS_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
                .andExpect(jsonPath("$.[*].googlePlus").value(hasItem(DEFAULT_GOOGLE_PLUS.toString())))
                .andExpect(jsonPath("$.[*].youtube").value(hasItem(DEFAULT_YOUTUBE.toString())))
                .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN.toString())))
                .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
                .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
                .andExpect(jsonPath("$.[*].companyType").value(hasItem(DEFAULT_COMPANY_TYPE.toString())))
                .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
                .andExpect(jsonPath("$.[*].officePhone").value(hasItem(DEFAULT_OFFICE_PHONE.toString())))
                .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
                .andExpect(jsonPath("$.[*].house").value(hasItem(DEFAULT_HOUSE.toString())))
                .andExpect(jsonPath("$.[*].RoadNo").value(hasItem(DEFAULT_ROAD_NO.toString())))
                .andExpect(jsonPath("$.[*].CreatedDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS)));
    }

    @Test
    @Transactional
    public void getTempCompany() throws Exception {
        // Initialize the database
        tempCompanyRepository.saveAndFlush(tempCompany);

        // Get the tempCompany
        restTempCompanyMockMvc.perform(get("/api/tempCompanys/{id}", tempCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tempCompany.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME.toString()))
            .andExpect(jsonPath("$.BranchType").value(DEFAULT_BRANCH_TYPE.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.companyInformation").value(DEFAULT_COMPANY_INFORMATION.toString()))
            .andExpect(jsonPath("$.businessDescription").value(DEFAULT_BUSINESS_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.googlePlus").value(DEFAULT_GOOGLE_PLUS.toString()))
            .andExpect(jsonPath("$.youtube").value(DEFAULT_YOUTUBE.toString()))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN.toString()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.companyType").value(DEFAULT_COMPANY_TYPE.toString()))
            .andExpect(jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE.toString()))
            .andExpect(jsonPath("$.officePhone").value(DEFAULT_OFFICE_PHONE.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.house").value(DEFAULT_HOUSE.toString()))
            .andExpect(jsonPath("$.RoadNo").value(DEFAULT_ROAD_NO.toString()))
            .andExpect(jsonPath("$.CreatedDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingTempCompany() throws Exception {
        // Get the tempCompany
        restTempCompanyMockMvc.perform(get("/api/tempCompanys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTempCompany() throws Exception {
        // Initialize the database
        tempCompanyRepository.saveAndFlush(tempCompany);

		int databaseSizeBeforeUpdate = tempCompanyRepository.findAll().size();

        // Update the tempCompany
        tempCompany.setUsername(UPDATED_USERNAME);
        tempCompany.setEmail(UPDATED_EMAIL);
        tempCompany.setPassword(UPDATED_PASSWORD);
        tempCompany.setBranchName(UPDATED_BRANCH_NAME);
        tempCompany.setBranchType(UPDATED_BRANCH_TYPE);
        tempCompany.setCompanyName(UPDATED_COMPANY_NAME);
        tempCompany.setShortName(UPDATED_SHORT_NAME);
        tempCompany.setCompanyInformation(UPDATED_COMPANY_INFORMATION);
        tempCompany.setBusinessDescription(UPDATED_BUSINESS_DESCRIPTION);
        tempCompany.setFacebook(UPDATED_FACEBOOK);
        tempCompany.setGooglePlus(UPDATED_GOOGLE_PLUS);
        tempCompany.setYoutube(UPDATED_YOUTUBE);
        tempCompany.setLinkedin(UPDATED_LINKEDIN);
        tempCompany.setTwitter(UPDATED_TWITTER);
        tempCompany.setWebsite(UPDATED_WEBSITE);
        tempCompany.setCity(UPDATED_CITY);
        tempCompany.setShortDescription(UPDATED_SHORT_DESCRIPTION);
        tempCompany.setLogo(UPDATED_LOGO);
        tempCompany.setLogoContentType(UPDATED_LOGO_CONTENT_TYPE);
        tempCompany.setCompanyType(UPDATED_COMPANY_TYPE);
        tempCompany.setAddressType(UPDATED_ADDRESS_TYPE);
        tempCompany.setOfficePhone(UPDATED_OFFICE_PHONE);
        tempCompany.setContactNumber(UPDATED_CONTACT_NUMBER);
        tempCompany.setPostalCode(UPDATED_POSTAL_CODE);
        tempCompany.setHouse(UPDATED_HOUSE);
        tempCompany.setRoadNo(UPDATED_ROAD_NO);
        tempCompany.setCreatedDate(UPDATED_CREATED_DATE);
        tempCompany.setUpdatedDate(UPDATED_UPDATED_DATE);
        tempCompany.setCreateBy(UPDATED_CREATE_BY);
        tempCompany.setUpdatedBy(UPDATED_UPDATED_BY);
        tempCompany.setActiveStatus(UPDATED_ACTIVE_STATUS);

        restTempCompanyMockMvc.perform(put("/api/tempCompanys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tempCompany)))
                .andExpect(status().isOk());

        // Validate the TempCompany in the database
        List<TempCompany> tempCompanys = tempCompanyRepository.findAll();
        assertThat(tempCompanys).hasSize(databaseSizeBeforeUpdate);
        TempCompany testTempCompany = tempCompanys.get(tempCompanys.size() - 1);
        assertThat(testTempCompany.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testTempCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTempCompany.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testTempCompany.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testTempCompany.getBranchType()).isEqualTo(UPDATED_BRANCH_TYPE);
        assertThat(testTempCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testTempCompany.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testTempCompany.getCompanyInformation()).isEqualTo(UPDATED_COMPANY_INFORMATION);
        assertThat(testTempCompany.getBusinessDescription()).isEqualTo(UPDATED_BUSINESS_DESCRIPTION);
        assertThat(testTempCompany.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testTempCompany.getGooglePlus()).isEqualTo(UPDATED_GOOGLE_PLUS);
        assertThat(testTempCompany.getYoutube()).isEqualTo(UPDATED_YOUTUBE);
        assertThat(testTempCompany.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testTempCompany.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testTempCompany.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testTempCompany.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testTempCompany.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testTempCompany.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testTempCompany.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testTempCompany.getCompanyType()).isEqualTo(UPDATED_COMPANY_TYPE);
        assertThat(testTempCompany.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testTempCompany.getOfficePhone()).isEqualTo(UPDATED_OFFICE_PHONE);
        assertThat(testTempCompany.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testTempCompany.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testTempCompany.getHouse()).isEqualTo(UPDATED_HOUSE);
        assertThat(testTempCompany.getRoadNo()).isEqualTo(UPDATED_ROAD_NO);
        assertThat(testTempCompany.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTempCompany.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testTempCompany.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testTempCompany.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTempCompany.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void deleteTempCompany() throws Exception {
        // Initialize the database
        tempCompanyRepository.saveAndFlush(tempCompany);

		int databaseSizeBeforeDelete = tempCompanyRepository.findAll().size();

        // Get the tempCompany
        restTempCompanyMockMvc.perform(delete("/api/tempCompanys/{id}", tempCompany.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TempCompany> tempCompanys = tempCompanyRepository.findAll();
        assertThat(tempCompanys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
