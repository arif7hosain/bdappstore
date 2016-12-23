package com.appstore.web.rest;

import com.appstore.Application;
import com.appstore.domain.Upazila;
import com.appstore.repository.UpazilaRepository;
import com.appstore.repository.search.UpazilaSearchRepository;

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
 * Test class for the UpazilaResource REST controller.
 *
 * @see UpazilaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UpazilaResourceIntTest {

    private static final String DEFAULT_UPAZILA_NAME = "AAAAA";
    private static final String UPDATED_UPAZILA_NAME = "BBBBB";
    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

    @Inject
    private UpazilaRepository upazilaRepository;

    @Inject
    private UpazilaSearchRepository upazilaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUpazilaMockMvc;

    private Upazila upazila;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UpazilaResource upazilaResource = new UpazilaResource();
        ReflectionTestUtils.setField(upazilaResource, "upazilaSearchRepository", upazilaSearchRepository);
        ReflectionTestUtils.setField(upazilaResource, "upazilaRepository", upazilaRepository);
        this.restUpazilaMockMvc = MockMvcBuilders.standaloneSetup(upazilaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        upazila = new Upazila();
        upazila.setUpazilaName(DEFAULT_UPAZILA_NAME);
        upazila.setComments(DEFAULT_COMMENTS);
        upazila.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createUpazila() throws Exception {
        int databaseSizeBeforeCreate = upazilaRepository.findAll().size();

        // Create the Upazila

        restUpazilaMockMvc.perform(post("/api/upazilas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(upazila)))
                .andExpect(status().isCreated());

        // Validate the Upazila in the database
        List<Upazila> upazilas = upazilaRepository.findAll();
        assertThat(upazilas).hasSize(databaseSizeBeforeCreate + 1);
        Upazila testUpazila = upazilas.get(upazilas.size() - 1);
        assertThat(testUpazila.getUpazilaName()).isEqualTo(DEFAULT_UPAZILA_NAME);
        assertThat(testUpazila.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testUpazila.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllUpazilas() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);

        // Get all the upazilas
        restUpazilaMockMvc.perform(get("/api/upazilas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(upazila.getId().intValue())))
                .andExpect(jsonPath("$.[*].upazilaName").value(hasItem(DEFAULT_UPAZILA_NAME.toString())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    public void getUpazila() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);

        // Get the upazila
        restUpazilaMockMvc.perform(get("/api/upazilas/{id}", upazila.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(upazila.getId().intValue()))
            .andExpect(jsonPath("$.upazilaName").value(DEFAULT_UPAZILA_NAME.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    public void getNonExistingUpazila() throws Exception {
        // Get the upazila
        restUpazilaMockMvc.perform(get("/api/upazilas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpazila() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);

		int databaseSizeBeforeUpdate = upazilaRepository.findAll().size();

        // Update the upazila
        upazila.setUpazilaName(UPDATED_UPAZILA_NAME);
        upazila.setComments(UPDATED_COMMENTS);
        upazila.setIsActive(UPDATED_IS_ACTIVE);

        restUpazilaMockMvc.perform(put("/api/upazilas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(upazila)))
                .andExpect(status().isOk());

        // Validate the Upazila in the database
        List<Upazila> upazilas = upazilaRepository.findAll();
        assertThat(upazilas).hasSize(databaseSizeBeforeUpdate);
        Upazila testUpazila = upazilas.get(upazilas.size() - 1);
        assertThat(testUpazila.getUpazilaName()).isEqualTo(UPDATED_UPAZILA_NAME);
        assertThat(testUpazila.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testUpazila.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteUpazila() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);

		int databaseSizeBeforeDelete = upazilaRepository.findAll().size();

        // Get the upazila
        restUpazilaMockMvc.perform(delete("/api/upazilas/{id}", upazila.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Upazila> upazilas = upazilaRepository.findAll();
        assertThat(upazilas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
