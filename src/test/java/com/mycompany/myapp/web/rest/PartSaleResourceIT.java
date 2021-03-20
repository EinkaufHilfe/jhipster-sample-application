package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PartSale;
import com.mycompany.myapp.repository.PartSaleRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartSaleResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartSaleResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PartSaleRepository partSaleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartSaleMockMvc;

    private PartSale partSale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartSale createEntity(EntityManager em) {
        PartSale partSale = new PartSale().name(DEFAULT_NAME).price(DEFAULT_PRICE);
        return partSale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartSale createUpdatedEntity(EntityManager em) {
        PartSale partSale = new PartSale().name(UPDATED_NAME).price(UPDATED_PRICE);
        return partSale;
    }

    @BeforeEach
    public void initTest() {
        partSale = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartSale() throws Exception {
        int databaseSizeBeforeCreate = partSaleRepository.findAll().size();
        // Create the PartSale
        restPartSaleMockMvc
            .perform(post("/api/part-sales").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partSale)))
            .andExpect(status().isCreated());

        // Validate the PartSale in the database
        List<PartSale> partSaleList = partSaleRepository.findAll();
        assertThat(partSaleList).hasSize(databaseSizeBeforeCreate + 1);
        PartSale testPartSale = partSaleList.get(partSaleList.size() - 1);
        assertThat(testPartSale.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPartSale.getPrice()).isEqualTo(DEFAULT_PRICE);
        //        assertThat(testPartSale.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        //        assertThat(testPartSale.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        //        assertThat(testPartSale.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        //        assertThat(testPartSale.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createPartSaleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partSaleRepository.findAll().size();

        // Create the PartSale with an existing ID
        partSale.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartSaleMockMvc
            .perform(post("/api/part-sales").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partSale)))
            .andExpect(status().isBadRequest());

        // Validate the PartSale in the database
        List<PartSale> partSaleList = partSaleRepository.findAll();
        assertThat(partSaleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPartSales() throws Exception {
        // Initialize the database
        partSaleRepository.saveAndFlush(partSale);

        // Get all the partSaleList
        restPartSaleMockMvc
            .perform(get("/api/part-sales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partSale.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_AT))));
    }

    @Test
    @Transactional
    public void getPartSale() throws Exception {
        // Initialize the database
        partSaleRepository.saveAndFlush(partSale);

        // Get the partSale
        restPartSaleMockMvc
            .perform(get("/api/part-sales/{id}", partSale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partSale.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_UPDATED_BY))
            .andExpect(jsonPath("$.lastUpdatedAt").value(sameInstant(DEFAULT_LAST_UPDATED_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingPartSale() throws Exception {
        // Get the partSale
        restPartSaleMockMvc.perform(get("/api/part-sales/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartSale() throws Exception {
        // Initialize the database
        partSaleRepository.saveAndFlush(partSale);

        int databaseSizeBeforeUpdate = partSaleRepository.findAll().size();

        // Update the partSale
        PartSale updatedPartSale = partSaleRepository.findById(partSale.getId()).get();
        // Disconnect from session so that the updates on updatedPartSale are not directly saved in db
        em.detach(updatedPartSale);
        updatedPartSale.name(UPDATED_NAME).price(UPDATED_PRICE);

        restPartSaleMockMvc
            .perform(
                put("/api/part-sales").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedPartSale))
            )
            .andExpect(status().isOk());

        // Validate the PartSale in the database
        List<PartSale> partSaleList = partSaleRepository.findAll();
        assertThat(partSaleList).hasSize(databaseSizeBeforeUpdate);
        PartSale testPartSale = partSaleList.get(partSaleList.size() - 1);
        assertThat(testPartSale.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPartSale.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPartSale.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        //        assertThat(testPartSale.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        //        assertThat(testPartSale.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        //        assertThat(testPartSale.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingPartSale() throws Exception {
        int databaseSizeBeforeUpdate = partSaleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartSaleMockMvc
            .perform(put("/api/part-sales").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partSale)))
            .andExpect(status().isBadRequest());

        // Validate the PartSale in the database
        List<PartSale> partSaleList = partSaleRepository.findAll();
        assertThat(partSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartSale() throws Exception {
        // Initialize the database
        partSaleRepository.saveAndFlush(partSale);

        int databaseSizeBeforeDelete = partSaleRepository.findAll().size();

        // Delete the partSale
        restPartSaleMockMvc
            .perform(delete("/api/part-sales/{id}", partSale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartSale> partSaleList = partSaleRepository.findAll();
        assertThat(partSaleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
