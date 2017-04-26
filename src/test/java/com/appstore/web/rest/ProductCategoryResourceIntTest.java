package com.appstore.web.rest;

import com.appstore.Application;
import com.appstore.domain.ProductCategory;
import com.appstore.repository.ProductCategoryRepository;
import com.appstore.repository.search.ProductCategorySearchRepository;

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
 * Test class for the ProductCategoryResource REST controller.
 *
 * @see ProductCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductCategoryResourceIntTest {

    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

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
    private ProductCategoryRepository productCategoryRepository;

    @Inject
    private ProductCategorySearchRepository productCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProductCategoryMockMvc;

    private ProductCategory productCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductCategoryResource productCategoryResource = new ProductCategoryResource();
        ReflectionTestUtils.setField(productCategoryResource, "productCategorySearchRepository", productCategorySearchRepository);
        ReflectionTestUtils.setField(productCategoryResource, "productCategoryRepository", productCategoryRepository);
        this.restProductCategoryMockMvc = MockMvcBuilders.standaloneSetup(productCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        productCategory = new ProductCategory();
        productCategory.setComments(DEFAULT_COMMENTS);
        productCategory.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createProductCategory() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();

        // Create the ProductCategory

        restProductCategoryMockMvc.perform(post("/api/productCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productCategory)))
                .andExpect(status().isCreated());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategorys = productCategoryRepository.findAll();
        assertThat(productCategorys).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategory testProductCategory = productCategorys.get(productCategorys.size() - 1);
        assertThat(testProductCategory.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testProductCategory.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllProductCategorys() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategorys
        restProductCategoryMockMvc.perform(get("/api/productCategorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].CreatedDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    public void getProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/productCategorys/{id}", productCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(productCategory.getId().intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.CreatedDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    public void getNonExistingProductCategory() throws Exception {
        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/productCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

		int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory
        productCategory.setComments(UPDATED_COMMENTS);
        productCategory.setIsActive(UPDATED_IS_ACTIVE);

        restProductCategoryMockMvc.perform(put("/api/productCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productCategory)))
                .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategorys = productCategoryRepository.findAll();
        assertThat(productCategorys).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategorys.get(productCategorys.size() - 1);
        assertThat(testProductCategory.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testProductCategory.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

		int databaseSizeBeforeDelete = productCategoryRepository.findAll().size();

        // Get the productCategory
        restProductCategoryMockMvc.perform(delete("/api/productCategorys/{id}", productCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductCategory> productCategorys = productCategoryRepository.findAll();
        assertThat(productCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
