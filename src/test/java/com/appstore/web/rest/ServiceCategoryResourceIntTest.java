package com.appstore.web.rest;

import com.appstore.Application;
import com.appstore.domain.ServiceCategory;
import com.appstore.repository.ServiceCategoryRepository;
import com.appstore.repository.search.ServiceCategorySearchRepository;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ServiceCategoryResource REST controller.
 *
 * @see ServiceCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ServiceCategoryResourceIntTest {

    private static final String DEFAULT_SERVICE_NAME = "AAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBB";
    private static final String DEFAULT_SERVICE_DESCRIPTION = "AAAAA";
    private static final String UPDATED_SERVICE_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CREATE_BY = "AAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBB";
    private static final String DEFAULT_UPDATED_BY = "AAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBB";

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

    @Inject
    private ServiceCategoryRepository serviceCategoryRepository;

    @Inject
    private ServiceCategorySearchRepository serviceCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restServiceCategoryMockMvc;

    private ServiceCategory serviceCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceCategoryResource serviceCategoryResource = new ServiceCategoryResource();
        ReflectionTestUtils.setField(serviceCategoryResource, "serviceCategorySearchRepository", serviceCategorySearchRepository);
        ReflectionTestUtils.setField(serviceCategoryResource, "serviceCategoryRepository", serviceCategoryRepository);
        this.restServiceCategoryMockMvc = MockMvcBuilders.standaloneSetup(serviceCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        serviceCategory = new ServiceCategory();
        serviceCategory.setServiceName(DEFAULT_SERVICE_NAME);
        serviceCategory.setServiceDescription(DEFAULT_SERVICE_DESCRIPTION);
        serviceCategory.setShortDescription(DEFAULT_SHORT_DESCRIPTION);
        serviceCategory.setCreatedDate(DEFAULT_CREATED_DATE);
        serviceCategory.setUpdatedDate(DEFAULT_UPDATED_DATE);
        serviceCategory.setCreateBy(DEFAULT_CREATE_BY);
        serviceCategory.setUpdatedBy(DEFAULT_UPDATED_BY);
        serviceCategory.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createServiceCategory() throws Exception {
        int databaseSizeBeforeCreate = serviceCategoryRepository.findAll().size();

        // Create the ServiceCategory

        restServiceCategoryMockMvc.perform(post("/api/serviceCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(serviceCategory)))
                .andExpect(status().isCreated());

        // Validate the ServiceCategory in the database
        List<ServiceCategory> serviceCategorys = serviceCategoryRepository.findAll();
        assertThat(serviceCategorys).hasSize(databaseSizeBeforeCreate + 1);
        ServiceCategory testServiceCategory = serviceCategorys.get(serviceCategorys.size() - 1);
        assertThat(testServiceCategory.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testServiceCategory.getServiceDescription()).isEqualTo(DEFAULT_SERVICE_DESCRIPTION);
        assertThat(testServiceCategory.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testServiceCategory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testServiceCategory.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testServiceCategory.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testServiceCategory.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testServiceCategory.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllServiceCategorys() throws Exception {
        // Initialize the database
        serviceCategoryRepository.saveAndFlush(serviceCategory);

        // Get all the serviceCategorys
        restServiceCategoryMockMvc.perform(get("/api/serviceCategorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(serviceCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
                .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].CreatedDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    public void getServiceCategory() throws Exception {
        // Initialize the database
        serviceCategoryRepository.saveAndFlush(serviceCategory);

        // Get the serviceCategory
        restServiceCategoryMockMvc.perform(get("/api/serviceCategorys/{id}", serviceCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(serviceCategory.getId().intValue()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.serviceDescription").value(DEFAULT_SERVICE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.CreatedDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    public void getNonExistingServiceCategory() throws Exception {
        // Get the serviceCategory
        restServiceCategoryMockMvc.perform(get("/api/serviceCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceCategory() throws Exception {
        // Initialize the database
        serviceCategoryRepository.saveAndFlush(serviceCategory);

		int databaseSizeBeforeUpdate = serviceCategoryRepository.findAll().size();

        // Update the serviceCategory
        serviceCategory.setServiceName(UPDATED_SERVICE_NAME);
        serviceCategory.setServiceDescription(UPDATED_SERVICE_DESCRIPTION);
        serviceCategory.setShortDescription(UPDATED_SHORT_DESCRIPTION);
        serviceCategory.setCreatedDate(UPDATED_CREATED_DATE);
        serviceCategory.setUpdatedDate(UPDATED_UPDATED_DATE);
        serviceCategory.setCreateBy(UPDATED_CREATE_BY);
        serviceCategory.setUpdatedBy(UPDATED_UPDATED_BY);
        serviceCategory.setIsActive(UPDATED_IS_ACTIVE);

        restServiceCategoryMockMvc.perform(put("/api/serviceCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(serviceCategory)))
                .andExpect(status().isOk());

        // Validate the ServiceCategory in the database
        List<ServiceCategory> serviceCategorys = serviceCategoryRepository.findAll();
        assertThat(serviceCategorys).hasSize(databaseSizeBeforeUpdate);
        ServiceCategory testServiceCategory = serviceCategorys.get(serviceCategorys.size() - 1);
        assertThat(testServiceCategory.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testServiceCategory.getServiceDescription()).isEqualTo(UPDATED_SERVICE_DESCRIPTION);
        assertThat(testServiceCategory.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testServiceCategory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testServiceCategory.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testServiceCategory.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testServiceCategory.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testServiceCategory.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteServiceCategory() throws Exception {
        // Initialize the database
        serviceCategoryRepository.saveAndFlush(serviceCategory);

		int databaseSizeBeforeDelete = serviceCategoryRepository.findAll().size();

        // Get the serviceCategory
        restServiceCategoryMockMvc.perform(delete("/api/serviceCategorys/{id}", serviceCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceCategory> serviceCategorys = serviceCategoryRepository.findAll();
        assertThat(serviceCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
