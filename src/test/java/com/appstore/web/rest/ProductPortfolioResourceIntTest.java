package com.appstore.web.rest;

import com.appstore.Application;
import com.appstore.domain.ProductPortfolio;
import com.appstore.repository.ProductPortfolioRepository;
import com.appstore.repository.search.ProductPortfolioSearchRepository;

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


/**
 * Test class for the ProductPortfolioResource REST controller.
 *
 * @see ProductPortfolioResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductPortfolioResourceIntTest {


    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

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
    private ProductPortfolioRepository productPortfolioRepository;

    @Inject
    private ProductPortfolioSearchRepository productPortfolioSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProductPortfolioMockMvc;

    private ProductPortfolio productPortfolio;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductPortfolioResource productPortfolioResource = new ProductPortfolioResource();
        ReflectionTestUtils.setField(productPortfolioResource, "productPortfolioSearchRepository", productPortfolioSearchRepository);
        ReflectionTestUtils.setField(productPortfolioResource, "productPortfolioRepository", productPortfolioRepository);
        this.restProductPortfolioMockMvc = MockMvcBuilders.standaloneSetup(productPortfolioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        productPortfolio = new ProductPortfolio();
        productPortfolio.setImage(DEFAULT_IMAGE);
        productPortfolio.setImageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        productPortfolio.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createProductPortfolio() throws Exception {
        int databaseSizeBeforeCreate = productPortfolioRepository.findAll().size();

        // Create the ProductPortfolio

        restProductPortfolioMockMvc.perform(post("/api/productPortfolios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productPortfolio)))
                .andExpect(status().isCreated());

        // Validate the ProductPortfolio in the database
        List<ProductPortfolio> productPortfolios = productPortfolioRepository.findAll();
        assertThat(productPortfolios).hasSize(databaseSizeBeforeCreate + 1);
        ProductPortfolio testProductPortfolio = productPortfolios.get(productPortfolios.size() - 1);
        assertThat(testProductPortfolio.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProductPortfolio.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testProductPortfolio.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllProductPortfolios() throws Exception {
        // Initialize the database
        productPortfolioRepository.saveAndFlush(productPortfolio);

        // Get all the productPortfolios
        restProductPortfolioMockMvc.perform(get("/api/productPortfolios?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(productPortfolio.getId().intValue())))
                .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
                .andExpect(jsonPath("$.[*].CreatedDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    public void getProductPortfolio() throws Exception {
        // Initialize the database
        productPortfolioRepository.saveAndFlush(productPortfolio);

        // Get the productPortfolio
        restProductPortfolioMockMvc.perform(get("/api/productPortfolios/{id}", productPortfolio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(productPortfolio.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.CreatedDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    public void getNonExistingProductPortfolio() throws Exception {
        // Get the productPortfolio
        restProductPortfolioMockMvc.perform(get("/api/productPortfolios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductPortfolio() throws Exception {
        // Initialize the database
        productPortfolioRepository.saveAndFlush(productPortfolio);

		int databaseSizeBeforeUpdate = productPortfolioRepository.findAll().size();

        // Update the productPortfolio
        productPortfolio.setImage(UPDATED_IMAGE);
        productPortfolio.setImageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        productPortfolio.setIsActive(UPDATED_IS_ACTIVE);

        restProductPortfolioMockMvc.perform(put("/api/productPortfolios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productPortfolio)))
                .andExpect(status().isOk());

        // Validate the ProductPortfolio in the database
        List<ProductPortfolio> productPortfolios = productPortfolioRepository.findAll();
        assertThat(productPortfolios).hasSize(databaseSizeBeforeUpdate);
        ProductPortfolio testProductPortfolio = productPortfolios.get(productPortfolios.size() - 1);
        assertThat(testProductPortfolio.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProductPortfolio.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testProductPortfolio.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteProductPortfolio() throws Exception {
        // Initialize the database
        productPortfolioRepository.saveAndFlush(productPortfolio);

		int databaseSizeBeforeDelete = productPortfolioRepository.findAll().size();

        // Get the productPortfolio
        restProductPortfolioMockMvc.perform(delete("/api/productPortfolios/{id}", productPortfolio.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductPortfolio> productPortfolios = productPortfolioRepository.findAll();
        assertThat(productPortfolios).hasSize(databaseSizeBeforeDelete - 1);
    }
}
