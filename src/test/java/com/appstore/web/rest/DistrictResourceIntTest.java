package com.appstore.web.rest;

import com.appstore.Application;
import com.appstore.domain.District;
import com.appstore.repository.DistrictRepository;
import com.appstore.repository.search.DistrictSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DistrictResource REST controller.
 *
 * @see DistrictResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DistrictResourceIntTest {

    private static final String DEFAULT_DISTRICT_NAME = "AAAAA";
    private static final String UPDATED_DISTRICT_NAME = "BBBBB";
    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

    @Inject
    private DistrictRepository districtRepository;

    @Inject
    private DistrictSearchRepository districtSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDistrictMockMvc;

    private District district;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DistrictResource districtResource = new DistrictResource();
        ReflectionTestUtils.setField(districtResource, "districtSearchRepository", districtSearchRepository);
        ReflectionTestUtils.setField(districtResource, "districtRepository", districtRepository);
        this.restDistrictMockMvc = MockMvcBuilders.standaloneSetup(districtResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        district = new District();
        district.setDistrictName(DEFAULT_DISTRICT_NAME);
        district.setComments(DEFAULT_COMMENTS);
        district.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createDistrict() throws Exception {
        int databaseSizeBeforeCreate = districtRepository.findAll().size();

        // Create the District

        restDistrictMockMvc.perform(post("/api/districts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(district)))
                .andExpect(status().isCreated());

        // Validate the District in the database
        List<District> districts = districtRepository.findAll();
        assertThat(districts).hasSize(databaseSizeBeforeCreate + 1);
        District testDistrict = districts.get(districts.size() - 1);
        assertThat(testDistrict.getDistrictName()).isEqualTo(DEFAULT_DISTRICT_NAME);
        assertThat(testDistrict.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testDistrict.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllDistricts() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districts
        restDistrictMockMvc.perform(get("/api/districts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(district.getId().intValue())))
                .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME.toString())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    public void getDistrict() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get the district
        restDistrictMockMvc.perform(get("/api/districts/{id}", district.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(district.getId().intValue()))
            .andExpect(jsonPath("$.districtName").value(DEFAULT_DISTRICT_NAME.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    public void getNonExistingDistrict() throws Exception {
        // Get the district
        restDistrictMockMvc.perform(get("/api/districts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistrict() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

		int databaseSizeBeforeUpdate = districtRepository.findAll().size();

        // Update the district
        district.setDistrictName(UPDATED_DISTRICT_NAME);
        district.setComments(UPDATED_COMMENTS);
        district.setIsActive(UPDATED_IS_ACTIVE);

        restDistrictMockMvc.perform(put("/api/districts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(district)))
                .andExpect(status().isOk());

        // Validate the District in the database
        List<District> districts = districtRepository.findAll();
        assertThat(districts).hasSize(databaseSizeBeforeUpdate);
        District testDistrict = districts.get(districts.size() - 1);
        assertThat(testDistrict.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testDistrict.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testDistrict.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteDistrict() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

		int databaseSizeBeforeDelete = districtRepository.findAll().size();

        // Get the district
        restDistrictMockMvc.perform(delete("/api/districts/{id}", district.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<District> districts = districtRepository.findAll();
        assertThat(districts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
