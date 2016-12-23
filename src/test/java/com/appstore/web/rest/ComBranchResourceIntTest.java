package com.appstore.web.rest;

import com.appstore.Application;
import com.appstore.domain.ComBranch;
import com.appstore.repository.ComBranchRepository;
import com.appstore.repository.search.ComBranchSearchRepository;

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

import com.appstore.domain.enumeration.BranchType;

/**
 * Test class for the ComBranchResource REST controller.
 *
 * @see ComBranchResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ComBranchResourceIntTest {

    private static final String DEFAULT_BRANCH_NAME = "AAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBB";
    private static final String DEFAULT_BRANCH_DESCRIPTION = "AAAAA";
    private static final String UPDATED_BRANCH_DESCRIPTION = "BBBBB";


    private static final BranchType DEFAULT_BRANCH_TYPE = BranchType.Headquarter;
    private static final BranchType UPDATED_BRANCH_TYPE = BranchType.Regional;
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
    private ComBranchRepository comBranchRepository;

    @Inject
    private ComBranchSearchRepository comBranchSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restComBranchMockMvc;

    private ComBranch comBranch;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComBranchResource comBranchResource = new ComBranchResource();
        ReflectionTestUtils.setField(comBranchResource, "comBranchSearchRepository", comBranchSearchRepository);
        ReflectionTestUtils.setField(comBranchResource, "comBranchRepository", comBranchRepository);
        this.restComBranchMockMvc = MockMvcBuilders.standaloneSetup(comBranchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        comBranch = new ComBranch();
        comBranch.setBranchName(DEFAULT_BRANCH_NAME);
        comBranch.setBranchDescription(DEFAULT_BRANCH_DESCRIPTION);
        comBranch.setBranchType(DEFAULT_BRANCH_TYPE);
        comBranch.setFacebook(DEFAULT_FACEBOOK);
        comBranch.setGooglePlus(DEFAULT_GOOGLE_PLUS);
        comBranch.setYoutube(DEFAULT_YOUTUBE);
        comBranch.setLinkedin(DEFAULT_LINKEDIN);
        comBranch.setTwitter(DEFAULT_TWITTER);
        comBranch.setWebsite(DEFAULT_WEBSITE);
        comBranch.setCity(DEFAULT_CITY);
        comBranch.setCreatedDate(DEFAULT_CREATED_DATE);
        comBranch.setUpdatedDate(DEFAULT_UPDATED_DATE);
        comBranch.setCreateBy(DEFAULT_CREATE_BY);
        comBranch.setUpdatedBy(DEFAULT_UPDATED_BY);
        comBranch.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createComBranch() throws Exception {
        int databaseSizeBeforeCreate = comBranchRepository.findAll().size();

        // Create the ComBranch

        restComBranchMockMvc.perform(post("/api/comBranchs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(comBranch)))
                .andExpect(status().isCreated());

        // Validate the ComBranch in the database
        List<ComBranch> comBranchs = comBranchRepository.findAll();
        assertThat(comBranchs).hasSize(databaseSizeBeforeCreate + 1);
        ComBranch testComBranch = comBranchs.get(comBranchs.size() - 1);
        assertThat(testComBranch.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testComBranch.getBranchDescription()).isEqualTo(DEFAULT_BRANCH_DESCRIPTION);
        assertThat(testComBranch.getBranchType()).isEqualTo(DEFAULT_BRANCH_TYPE);
        assertThat(testComBranch.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testComBranch.getGooglePlus()).isEqualTo(DEFAULT_GOOGLE_PLUS);
        assertThat(testComBranch.getYoutube()).isEqualTo(DEFAULT_YOUTUBE);
        assertThat(testComBranch.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testComBranch.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testComBranch.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testComBranch.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testComBranch.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testComBranch.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testComBranch.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testComBranch.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testComBranch.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllComBranchs() throws Exception {
        // Initialize the database
        comBranchRepository.saveAndFlush(comBranch);

        // Get all the comBranchs
        restComBranchMockMvc.perform(get("/api/comBranchs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(comBranch.getId().intValue())))
                .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME.toString())))
                .andExpect(jsonPath("$.[*].branchDescription").value(hasItem(DEFAULT_BRANCH_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].branchType").value(hasItem(DEFAULT_BRANCH_TYPE.toString())))
                .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
                .andExpect(jsonPath("$.[*].googlePlus").value(hasItem(DEFAULT_GOOGLE_PLUS.toString())))
                .andExpect(jsonPath("$.[*].youtube").value(hasItem(DEFAULT_YOUTUBE.toString())))
                .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN.toString())))
                .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
                .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    public void getComBranch() throws Exception {
        // Initialize the database
        comBranchRepository.saveAndFlush(comBranch);

        // Get the comBranch
        restComBranchMockMvc.perform(get("/api/comBranchs/{id}", comBranch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(comBranch.getId().intValue()))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME.toString()))
            .andExpect(jsonPath("$.branchDescription").value(DEFAULT_BRANCH_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.branchType").value(DEFAULT_BRANCH_TYPE.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.googlePlus").value(DEFAULT_GOOGLE_PLUS.toString()))
            .andExpect(jsonPath("$.youtube").value(DEFAULT_YOUTUBE.toString()))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN.toString()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    public void getNonExistingComBranch() throws Exception {
        // Get the comBranch
        restComBranchMockMvc.perform(get("/api/comBranchs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComBranch() throws Exception {
        // Initialize the database
        comBranchRepository.saveAndFlush(comBranch);

		int databaseSizeBeforeUpdate = comBranchRepository.findAll().size();

        // Update the comBranch
        comBranch.setBranchName(UPDATED_BRANCH_NAME);
        comBranch.setBranchDescription(UPDATED_BRANCH_DESCRIPTION);
        comBranch.setBranchType(UPDATED_BRANCH_TYPE);
        comBranch.setFacebook(UPDATED_FACEBOOK);
        comBranch.setGooglePlus(UPDATED_GOOGLE_PLUS);
        comBranch.setYoutube(UPDATED_YOUTUBE);
        comBranch.setLinkedin(UPDATED_LINKEDIN);
        comBranch.setTwitter(UPDATED_TWITTER);
        comBranch.setWebsite(UPDATED_WEBSITE);
        comBranch.setCity(UPDATED_CITY);
        comBranch.setCreatedDate(UPDATED_CREATED_DATE);
        comBranch.setUpdatedDate(UPDATED_UPDATED_DATE);
        comBranch.setCreateBy(UPDATED_CREATE_BY);
        comBranch.setUpdatedBy(UPDATED_UPDATED_BY);
        comBranch.setIsActive(UPDATED_IS_ACTIVE);

        restComBranchMockMvc.perform(put("/api/comBranchs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(comBranch)))
                .andExpect(status().isOk());

        // Validate the ComBranch in the database
        List<ComBranch> comBranchs = comBranchRepository.findAll();
        assertThat(comBranchs).hasSize(databaseSizeBeforeUpdate);
        ComBranch testComBranch = comBranchs.get(comBranchs.size() - 1);
        assertThat(testComBranch.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testComBranch.getBranchDescription()).isEqualTo(UPDATED_BRANCH_DESCRIPTION);
        assertThat(testComBranch.getBranchType()).isEqualTo(UPDATED_BRANCH_TYPE);
        assertThat(testComBranch.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testComBranch.getGooglePlus()).isEqualTo(UPDATED_GOOGLE_PLUS);
        assertThat(testComBranch.getYoutube()).isEqualTo(UPDATED_YOUTUBE);
        assertThat(testComBranch.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testComBranch.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testComBranch.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testComBranch.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testComBranch.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testComBranch.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testComBranch.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testComBranch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testComBranch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteComBranch() throws Exception {
        // Initialize the database
        comBranchRepository.saveAndFlush(comBranch);

		int databaseSizeBeforeDelete = comBranchRepository.findAll().size();

        // Get the comBranch
        restComBranchMockMvc.perform(delete("/api/comBranchs/{id}", comBranch.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ComBranch> comBranchs = comBranchRepository.findAll();
        assertThat(comBranchs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
