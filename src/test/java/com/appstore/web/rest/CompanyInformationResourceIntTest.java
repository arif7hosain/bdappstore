package com.appstore.web.rest;

import com.appstore.Application;
import com.appstore.domain.CompanyInformation;
import com.appstore.repository.CompanyInformationRepository;
import com.appstore.repository.search.CompanyInformationSearchRepository;

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

/**
 * Test class for the CompanyInformationResource REST controller.
 *
 * @see CompanyInformationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CompanyInformationResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBB";
    private static final String DEFAULT_SHORT_NAME = "AAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBB";
    private static final String DEFAULT_COMPANY_INFORMATION = "AAAAA";
    private static final String UPDATED_COMPANY_INFORMATION = "BBBBB";
    private static final String DEFAULT_BUSINESS_DESCRIPTION = "AAAAA";
    private static final String UPDATED_BUSINESS_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_WEBSITE = "AAAAA";
    private static final String UPDATED_WEBSITE = "BBBBB";


    private static final CompanyType DEFAULT_COMPANY_TYPE = CompanyType.National;
    private static final CompanyType UPDATED_COMPANY_TYPE = CompanyType.International;

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
    private CompanyInformationRepository companyInformationRepository;

    @Inject
    private CompanyInformationSearchRepository companyInformationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCompanyInformationMockMvc;

    private CompanyInformation companyInformation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyInformationResource companyInformationResource = new CompanyInformationResource();
        ReflectionTestUtils.setField(companyInformationResource, "companyInformationSearchRepository", companyInformationSearchRepository);
        ReflectionTestUtils.setField(companyInformationResource, "companyInformationRepository", companyInformationRepository);
        this.restCompanyInformationMockMvc = MockMvcBuilders.standaloneSetup(companyInformationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        companyInformation = new CompanyInformation();
        companyInformation.setCompanyName(DEFAULT_COMPANY_NAME);
        companyInformation.setShortName(DEFAULT_SHORT_NAME);
        companyInformation.setCompanyInformation(DEFAULT_COMPANY_INFORMATION);
        companyInformation.setBusinessDescription(DEFAULT_BUSINESS_DESCRIPTION);
        companyInformation.setShortDescription(DEFAULT_SHORT_DESCRIPTION);
        companyInformation.setLogo(DEFAULT_LOGO);
        companyInformation.setLogoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        companyInformation.setWebsite(DEFAULT_WEBSITE);
        companyInformation.setCompanyType(DEFAULT_COMPANY_TYPE);
        companyInformation.setCreatedDate(DEFAULT_CREATED_DATE);
        companyInformation.setUpdatedDate(DEFAULT_UPDATED_DATE);
        companyInformation.setCreateBy(DEFAULT_CREATE_BY);
        companyInformation.setUpdatedBy(DEFAULT_UPDATED_BY);
        companyInformation.setActiveStatus(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void createCompanyInformation() throws Exception {
        int databaseSizeBeforeCreate = companyInformationRepository.findAll().size();

        // Create the CompanyInformation

        restCompanyInformationMockMvc.perform(post("/api/companyInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(companyInformation)))
                .andExpect(status().isCreated());

        // Validate the CompanyInformation in the database
        List<CompanyInformation> companyInformations = companyInformationRepository.findAll();
        assertThat(companyInformations).hasSize(databaseSizeBeforeCreate + 1);
        CompanyInformation testCompanyInformation = companyInformations.get(companyInformations.size() - 1);
        assertThat(testCompanyInformation.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompanyInformation.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testCompanyInformation.getCompanyInformation()).isEqualTo(DEFAULT_COMPANY_INFORMATION);
        assertThat(testCompanyInformation.getBusinessDescription()).isEqualTo(DEFAULT_BUSINESS_DESCRIPTION);
        assertThat(testCompanyInformation.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testCompanyInformation.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testCompanyInformation.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testCompanyInformation.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testCompanyInformation.getCompanyType()).isEqualTo(DEFAULT_COMPANY_TYPE);
        assertThat(testCompanyInformation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyInformation.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testCompanyInformation.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testCompanyInformation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCompanyInformation.getActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void getAllCompanyInformations() throws Exception {
        // Initialize the database
        companyInformationRepository.saveAndFlush(companyInformation);

        // Get all the companyInformations
        restCompanyInformationMockMvc.perform(get("/api/companyInformations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(companyInformation.getId().intValue())))
                .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
                .andExpect(jsonPath("$.[*].companyInformation").value(hasItem(DEFAULT_COMPANY_INFORMATION.toString())))
                .andExpect(jsonPath("$.[*].businessDescription").value(hasItem(DEFAULT_BUSINESS_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
                .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
                .andExpect(jsonPath("$.[*].companyType").value(hasItem(DEFAULT_COMPANY_TYPE.toString())))
                .andExpect(jsonPath("$.[*].CreatedDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS)));
    }

    @Test
    @Transactional
    public void getCompanyInformation() throws Exception {
        // Initialize the database
        companyInformationRepository.saveAndFlush(companyInformation);

        // Get the companyInformation
        restCompanyInformationMockMvc.perform(get("/api/companyInformations/{id}", companyInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(companyInformation.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.companyInformation").value(DEFAULT_COMPANY_INFORMATION.toString()))
            .andExpect(jsonPath("$.businessDescription").value(DEFAULT_BUSINESS_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.companyType").value(DEFAULT_COMPANY_TYPE.toString()))
            .andExpect(jsonPath("$.CreatedDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyInformation() throws Exception {
        // Get the companyInformation
        restCompanyInformationMockMvc.perform(get("/api/companyInformations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyInformation() throws Exception {
        // Initialize the database
        companyInformationRepository.saveAndFlush(companyInformation);

		int databaseSizeBeforeUpdate = companyInformationRepository.findAll().size();

        // Update the companyInformation
        companyInformation.setCompanyName(UPDATED_COMPANY_NAME);
        companyInformation.setShortName(UPDATED_SHORT_NAME);
        companyInformation.setCompanyInformation(UPDATED_COMPANY_INFORMATION);
        companyInformation.setBusinessDescription(UPDATED_BUSINESS_DESCRIPTION);
        companyInformation.setShortDescription(UPDATED_SHORT_DESCRIPTION);
        companyInformation.setLogo(UPDATED_LOGO);
        companyInformation.setLogoContentType(UPDATED_LOGO_CONTENT_TYPE);
        companyInformation.setWebsite(UPDATED_WEBSITE);
        companyInformation.setCompanyType(UPDATED_COMPANY_TYPE);
        companyInformation.setCreatedDate(UPDATED_CREATED_DATE);
        companyInformation.setUpdatedDate(UPDATED_UPDATED_DATE);
        companyInformation.setCreateBy(UPDATED_CREATE_BY);
        companyInformation.setUpdatedBy(UPDATED_UPDATED_BY);
        companyInformation.setActiveStatus(UPDATED_ACTIVE_STATUS);

        restCompanyInformationMockMvc.perform(put("/api/companyInformations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(companyInformation)))
                .andExpect(status().isOk());

        // Validate the CompanyInformation in the database
        List<CompanyInformation> companyInformations = companyInformationRepository.findAll();
        assertThat(companyInformations).hasSize(databaseSizeBeforeUpdate);
        CompanyInformation testCompanyInformation = companyInformations.get(companyInformations.size() - 1);
        assertThat(testCompanyInformation.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompanyInformation.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testCompanyInformation.getCompanyInformation()).isEqualTo(UPDATED_COMPANY_INFORMATION);
        assertThat(testCompanyInformation.getBusinessDescription()).isEqualTo(UPDATED_BUSINESS_DESCRIPTION);
        assertThat(testCompanyInformation.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testCompanyInformation.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCompanyInformation.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testCompanyInformation.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testCompanyInformation.getCompanyType()).isEqualTo(UPDATED_COMPANY_TYPE);
        assertThat(testCompanyInformation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyInformation.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCompanyInformation.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testCompanyInformation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCompanyInformation.getActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void deleteCompanyInformation() throws Exception {
        // Initialize the database
        companyInformationRepository.saveAndFlush(companyInformation);

		int databaseSizeBeforeDelete = companyInformationRepository.findAll().size();

        // Get the companyInformation
        restCompanyInformationMockMvc.perform(delete("/api/companyInformations/{id}", companyInformation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyInformation> companyInformations = companyInformationRepository.findAll();
        assertThat(companyInformations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
