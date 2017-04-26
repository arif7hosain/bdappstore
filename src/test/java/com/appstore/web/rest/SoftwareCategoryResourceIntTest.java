package com.appstore.web.rest;

import com.appstore.Application;
import com.appstore.domain.SoftwareCategory;
import com.appstore.repository.SoftwareCategoryRepository;
import com.appstore.repository.search.SoftwareCategorySearchRepository;

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
 * Test class for the SoftwareCategoryResource REST controller.
 *
 * @see SoftwareCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SoftwareCategoryResourceIntTest {

    private static final String DEFAULT_CATEGORY_NAME = "AAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
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
    private SoftwareCategoryRepository softwareCategoryRepository;

    @Inject
    private SoftwareCategorySearchRepository softwareCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSoftwareCategoryMockMvc;

    private SoftwareCategory softwareCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SoftwareCategoryResource softwareCategoryResource = new SoftwareCategoryResource();
        ReflectionTestUtils.setField(softwareCategoryResource, "softwareCategorySearchRepository", softwareCategorySearchRepository);
        ReflectionTestUtils.setField(softwareCategoryResource, "softwareCategoryRepository", softwareCategoryRepository);
        this.restSoftwareCategoryMockMvc = MockMvcBuilders.standaloneSetup(softwareCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        softwareCategory = new SoftwareCategory();
        softwareCategory.setCategoryName(DEFAULT_CATEGORY_NAME);
        softwareCategory.setDescription(DEFAULT_DESCRIPTION);
        softwareCategory.setShortDescription(DEFAULT_SHORT_DESCRIPTION);
        softwareCategory.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createSoftwareCategory() throws Exception {
        int databaseSizeBeforeCreate = softwareCategoryRepository.findAll().size();

        // Create the SoftwareCategory

        restSoftwareCategoryMockMvc.perform(post("/api/softwareCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(softwareCategory)))
                .andExpect(status().isCreated());

        // Validate the SoftwareCategory in the database
        List<SoftwareCategory> softwareCategorys = softwareCategoryRepository.findAll();
        assertThat(softwareCategorys).hasSize(databaseSizeBeforeCreate + 1);
        SoftwareCategory testSoftwareCategory = softwareCategorys.get(softwareCategorys.size() - 1);
        assertThat(testSoftwareCategory.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testSoftwareCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSoftwareCategory.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testSoftwareCategory.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllSoftwareCategorys() throws Exception {
        // Initialize the database
        softwareCategoryRepository.saveAndFlush(softwareCategory);

        // Get all the softwareCategorys
        restSoftwareCategoryMockMvc.perform(get("/api/softwareCategorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(softwareCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].CreatedDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    public void getSoftwareCategory() throws Exception {
        // Initialize the database
        softwareCategoryRepository.saveAndFlush(softwareCategory);

        // Get the softwareCategory
        restSoftwareCategoryMockMvc.perform(get("/api/softwareCategorys/{id}", softwareCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(softwareCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.CreatedDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    public void getNonExistingSoftwareCategory() throws Exception {
        // Get the softwareCategory
        restSoftwareCategoryMockMvc.perform(get("/api/softwareCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoftwareCategory() throws Exception {
        // Initialize the database
        softwareCategoryRepository.saveAndFlush(softwareCategory);

		int databaseSizeBeforeUpdate = softwareCategoryRepository.findAll().size();

        // Update the softwareCategory
        softwareCategory.setCategoryName(UPDATED_CATEGORY_NAME);
        softwareCategory.setDescription(UPDATED_DESCRIPTION);
        softwareCategory.setShortDescription(UPDATED_SHORT_DESCRIPTION);
        softwareCategory.setIsActive(UPDATED_IS_ACTIVE);

        restSoftwareCategoryMockMvc.perform(put("/api/softwareCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(softwareCategory)))
                .andExpect(status().isOk());

        // Validate the SoftwareCategory in the database
        List<SoftwareCategory> softwareCategorys = softwareCategoryRepository.findAll();
        assertThat(softwareCategorys).hasSize(databaseSizeBeforeUpdate);
        SoftwareCategory testSoftwareCategory = softwareCategorys.get(softwareCategorys.size() - 1);
        assertThat(testSoftwareCategory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testSoftwareCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSoftwareCategory.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testSoftwareCategory.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteSoftwareCategory() throws Exception {
        // Initialize the database
        softwareCategoryRepository.saveAndFlush(softwareCategory);

		int databaseSizeBeforeDelete = softwareCategoryRepository.findAll().size();

        // Get the softwareCategory
        restSoftwareCategoryMockMvc.perform(delete("/api/softwareCategorys/{id}", softwareCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SoftwareCategory> softwareCategorys = softwareCategoryRepository.findAll();
        assertThat(softwareCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
