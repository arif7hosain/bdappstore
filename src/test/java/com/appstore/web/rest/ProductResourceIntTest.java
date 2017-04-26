package com.appstore.web.rest;

import com.appstore.Application;
import com.appstore.domain.Product;
import com.appstore.repository.ProductRepository;
import com.appstore.repository.search.ProductSearchRepository;

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

import com.appstore.domain.enumeration.ProductType;
import com.appstore.domain.enumeration.CurrencyType;
import com.appstore.domain.enumeration.DurationType;

/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductResourceIntTest {

    private static final String DEFAULT_PRODUCT_TITLE = "AAAAA";
    private static final String UPDATED_PRODUCT_TITLE = "BBBBB";
    private static final String DEFAULT_PRODUCT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_PRODUCT_DESCRIPTION = "BBBBB";


    private static final ProductType DEFAULT_PRODUCT_TYPE = ProductType.Web;
    private static final ProductType UPDATED_PRODUCT_TYPE = ProductType.Mobile;


    private static final CurrencyType DEFAULT_CURRENCY = CurrencyType.USD;
    private static final CurrencyType UPDATED_CURRENCY = CurrencyType.BDT;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;


    private static final DurationType DEFAULT_DURATION_TYPE = DurationType.Monthly;
    private static final DurationType UPDATED_DURATION_TYPE = DurationType.Yearly;

    private static final Integer DEFAULT_IS_FURTHER_DEVELOPMENT = 1;
    private static final Integer UPDATED_IS_FURTHER_DEVELOPMENT = 2;
    private static final String DEFAULT_LIVE_URL = "AAAAA";
    private static final String UPDATED_LIVE_URL = "BBBBB";
    private static final String DEFAULT_ADDITIONAL_LINK = "AAAAA";
    private static final String UPDATED_ADDITIONAL_LINK = "BBBBB";

    private static final Integer DEFAULT_IS_AVAILABLE = 1;
    private static final Integer UPDATED_IS_AVAILABLE = 2;

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
    private ProductRepository productRepository;

    @Inject
    private ProductSearchRepository productSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProductMockMvc;

    private Product product;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductResource productResource = new ProductResource();
        ReflectionTestUtils.setField(productResource, "productSearchRepository", productSearchRepository);
        ReflectionTestUtils.setField(productResource, "productRepository", productRepository);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        product = new Product();
        product.setProductTitle(DEFAULT_PRODUCT_TITLE);
        product.setProductDescription(DEFAULT_PRODUCT_DESCRIPTION);
        product.setProductType(DEFAULT_PRODUCT_TYPE);
        product.setCurrency(DEFAULT_CURRENCY);
        product.setPrice(DEFAULT_PRICE);
        product.setDurationType(DEFAULT_DURATION_TYPE);
        product.setIsFurtherDevelopment(DEFAULT_IS_FURTHER_DEVELOPMENT);
        product.setLiveUrl(DEFAULT_LIVE_URL);
        product.setAdditionalLink(DEFAULT_ADDITIONAL_LINK);
        product.setIsAvailable(DEFAULT_IS_AVAILABLE);
        product.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product

        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(product)))
                .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = products.get(products.size() - 1);
        assertThat(testProduct.getProductTitle()).isEqualTo(DEFAULT_PRODUCT_TITLE);
        assertThat(testProduct.getProductDescription()).isEqualTo(DEFAULT_PRODUCT_DESCRIPTION);
        assertThat(testProduct.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testProduct.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getDurationType()).isEqualTo(DEFAULT_DURATION_TYPE);
        assertThat(testProduct.getIsFurtherDevelopment()).isEqualTo(DEFAULT_IS_FURTHER_DEVELOPMENT);
        assertThat(testProduct.getLiveUrl()).isEqualTo(DEFAULT_LIVE_URL);
        assertThat(testProduct.getAdditionalLink()).isEqualTo(DEFAULT_ADDITIONAL_LINK);
        assertThat(testProduct.getIsAvailable()).isEqualTo(DEFAULT_IS_AVAILABLE);
        assertThat(testProduct.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the products
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
                .andExpect(jsonPath("$.[*].productTitle").value(hasItem(DEFAULT_PRODUCT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].productDescription").value(hasItem(DEFAULT_PRODUCT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].Currency").value(hasItem(DEFAULT_CURRENCY.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].durationType").value(hasItem(DEFAULT_DURATION_TYPE.toString())))
                .andExpect(jsonPath("$.[*].isFurtherDevelopment").value(hasItem(DEFAULT_IS_FURTHER_DEVELOPMENT)))
                .andExpect(jsonPath("$.[*].liveUrl").value(hasItem(DEFAULT_LIVE_URL.toString())))
                .andExpect(jsonPath("$.[*].additionalLink").value(hasItem(DEFAULT_ADDITIONAL_LINK.toString())))
                .andExpect(jsonPath("$.[*].isAvailable").value(hasItem(DEFAULT_IS_AVAILABLE)))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.productTitle").value(DEFAULT_PRODUCT_TITLE.toString()))
            .andExpect(jsonPath("$.productDescription").value(DEFAULT_PRODUCT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()))
            .andExpect(jsonPath("$.Currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.durationType").value(DEFAULT_DURATION_TYPE.toString()))
            .andExpect(jsonPath("$.isFurtherDevelopment").value(DEFAULT_IS_FURTHER_DEVELOPMENT))
            .andExpect(jsonPath("$.liveUrl").value(DEFAULT_LIVE_URL.toString()))
            .andExpect(jsonPath("$.additionalLink").value(DEFAULT_ADDITIONAL_LINK.toString()))
            .andExpect(jsonPath("$.isAvailable").value(DEFAULT_IS_AVAILABLE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

		int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        product.setProductTitle(UPDATED_PRODUCT_TITLE);
        product.setProductDescription(UPDATED_PRODUCT_DESCRIPTION);
        product.setProductType(UPDATED_PRODUCT_TYPE);
        product.setCurrency(UPDATED_CURRENCY);
        product.setPrice(UPDATED_PRICE);
        product.setDurationType(UPDATED_DURATION_TYPE);
        product.setIsFurtherDevelopment(UPDATED_IS_FURTHER_DEVELOPMENT);
        product.setLiveUrl(UPDATED_LIVE_URL);
        product.setAdditionalLink(UPDATED_ADDITIONAL_LINK);
        product.setIsAvailable(UPDATED_IS_AVAILABLE);
        product.setIsActive(UPDATED_IS_ACTIVE);

        restProductMockMvc.perform(put("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(product)))
                .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = products.get(products.size() - 1);
        assertThat(testProduct.getProductTitle()).isEqualTo(UPDATED_PRODUCT_TITLE);
        assertThat(testProduct.getProductDescription()).isEqualTo(UPDATED_PRODUCT_DESCRIPTION);
        assertThat(testProduct.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testProduct.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getDurationType()).isEqualTo(UPDATED_DURATION_TYPE);
        assertThat(testProduct.getIsFurtherDevelopment()).isEqualTo(UPDATED_IS_FURTHER_DEVELOPMENT);
        assertThat(testProduct.getLiveUrl()).isEqualTo(UPDATED_LIVE_URL);
        assertThat(testProduct.getAdditionalLink()).isEqualTo(UPDATED_ADDITIONAL_LINK);
        assertThat(testProduct.getIsAvailable()).isEqualTo(UPDATED_IS_AVAILABLE);
        assertThat(testProduct.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

		int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeDelete - 1);
    }
}
