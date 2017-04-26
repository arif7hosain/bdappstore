package com.appstore.web.rest;

import com.appstore.Application;
import com.appstore.domain.ComAddress;
import com.appstore.repository.ComAddressRepository;
import com.appstore.repository.search.ComAddressSearchRepository;

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

import com.appstore.domain.enumeration.AddressType;

/**
 * Test class for the ComAddressResource REST controller.
 *
 * @see ComAddressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ComAddressResourceIntTest {



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
    private ComAddressRepository comAddressRepository;

    @Inject
    private ComAddressSearchRepository comAddressSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restComAddressMockMvc;

    private ComAddress comAddress;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComAddressResource comAddressResource = new ComAddressResource();
        ReflectionTestUtils.setField(comAddressResource, "comAddressSearchRepository", comAddressSearchRepository);
        ReflectionTestUtils.setField(comAddressResource, "comAddressRepository", comAddressRepository);
        this.restComAddressMockMvc = MockMvcBuilders.standaloneSetup(comAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        comAddress = new ComAddress();
        comAddress.setAddressType(DEFAULT_ADDRESS_TYPE);
        comAddress.setOfficePhone(DEFAULT_OFFICE_PHONE);
        comAddress.setContactNumber(DEFAULT_CONTACT_NUMBER);
        comAddress.setPostalCode(DEFAULT_POSTAL_CODE);
        comAddress.setHouse(DEFAULT_HOUSE);
        comAddress.setRoadNo(DEFAULT_ROAD_NO);
        comAddress.setCity(DEFAULT_CITY);
        comAddress.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createComAddress() throws Exception {
        int databaseSizeBeforeCreate = comAddressRepository.findAll().size();

        // Create the ComAddress

        restComAddressMockMvc.perform(post("/api/comAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(comAddress)))
                .andExpect(status().isCreated());

        // Validate the ComAddress in the database
        List<ComAddress> comAddresss = comAddressRepository.findAll();
        assertThat(comAddresss).hasSize(databaseSizeBeforeCreate + 1);
        ComAddress testComAddress = comAddresss.get(comAddresss.size() - 1);
        assertThat(testComAddress.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
        assertThat(testComAddress.getOfficePhone()).isEqualTo(DEFAULT_OFFICE_PHONE);
        assertThat(testComAddress.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testComAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testComAddress.getHouse()).isEqualTo(DEFAULT_HOUSE);
        assertThat(testComAddress.getRoadNo()).isEqualTo(DEFAULT_ROAD_NO);
        assertThat(testComAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testComAddress.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllComAddresss() throws Exception {
        // Initialize the database
        comAddressRepository.saveAndFlush(comAddress);

        // Get all the comAddresss
        restComAddressMockMvc.perform(get("/api/comAddresss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(comAddress.getId().intValue())))
                .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
                .andExpect(jsonPath("$.[*].officePhone").value(hasItem(DEFAULT_OFFICE_PHONE.toString())))
                .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
                .andExpect(jsonPath("$.[*].house").value(hasItem(DEFAULT_HOUSE.toString())))
                .andExpect(jsonPath("$.[*].RoadNo").value(hasItem(DEFAULT_ROAD_NO.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].CreatedDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    public void getComAddress() throws Exception {
        // Initialize the database
        comAddressRepository.saveAndFlush(comAddress);

        // Get the comAddress
        restComAddressMockMvc.perform(get("/api/comAddresss/{id}", comAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(comAddress.getId().intValue()))
            .andExpect(jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE.toString()))
            .andExpect(jsonPath("$.officePhone").value(DEFAULT_OFFICE_PHONE.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.house").value(DEFAULT_HOUSE.toString()))
            .andExpect(jsonPath("$.RoadNo").value(DEFAULT_ROAD_NO.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.CreatedDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    public void getNonExistingComAddress() throws Exception {
        // Get the comAddress
        restComAddressMockMvc.perform(get("/api/comAddresss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComAddress() throws Exception {
        // Initialize the database
        comAddressRepository.saveAndFlush(comAddress);

		int databaseSizeBeforeUpdate = comAddressRepository.findAll().size();

        // Update the comAddress
        comAddress.setAddressType(UPDATED_ADDRESS_TYPE);
        comAddress.setOfficePhone(UPDATED_OFFICE_PHONE);
        comAddress.setContactNumber(UPDATED_CONTACT_NUMBER);
        comAddress.setPostalCode(UPDATED_POSTAL_CODE);
        comAddress.setHouse(UPDATED_HOUSE);
        comAddress.setRoadNo(UPDATED_ROAD_NO);
        comAddress.setCity(UPDATED_CITY);
        comAddress.setIsActive(UPDATED_IS_ACTIVE);

        restComAddressMockMvc.perform(put("/api/comAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(comAddress)))
                .andExpect(status().isOk());

        // Validate the ComAddress in the database
        List<ComAddress> comAddresss = comAddressRepository.findAll();
        assertThat(comAddresss).hasSize(databaseSizeBeforeUpdate);
        ComAddress testComAddress = comAddresss.get(comAddresss.size() - 1);
        assertThat(testComAddress.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testComAddress.getOfficePhone()).isEqualTo(UPDATED_OFFICE_PHONE);
        assertThat(testComAddress.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testComAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testComAddress.getHouse()).isEqualTo(UPDATED_HOUSE);
        assertThat(testComAddress.getRoadNo()).isEqualTo(UPDATED_ROAD_NO);
        assertThat(testComAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testComAddress.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteComAddress() throws Exception {
        // Initialize the database
        comAddressRepository.saveAndFlush(comAddress);

		int databaseSizeBeforeDelete = comAddressRepository.findAll().size();

        // Get the comAddress
        restComAddressMockMvc.perform(delete("/api/comAddresss/{id}", comAddress.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ComAddress> comAddresss = comAddressRepository.findAll();
        assertThat(comAddresss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
