package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PriceList;
import com.mycompany.myapp.repository.PriceListRepository;
import java.time.Instant;
import java.time.LocalDate;
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
 * Integration tests for the {@link PriceListResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PriceListResourceIT {
    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TILL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TILL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPriceListMockMvc;

    private PriceList priceList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceList createEntity(EntityManager em) {
        PriceList priceList = new PriceList().validFrom(DEFAULT_VALID_FROM).validTill(DEFAULT_VALID_TILL).type(DEFAULT_TYPE);
        return priceList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceList createUpdatedEntity(EntityManager em) {
        PriceList priceList = new PriceList().validFrom(UPDATED_VALID_FROM).validTill(UPDATED_VALID_TILL).type(UPDATED_TYPE);
        return priceList;
    }

    @BeforeEach
    public void initTest() {
        priceList = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriceList() throws Exception {
        int databaseSizeBeforeCreate = priceListRepository.findAll().size();
        // Create the PriceList
        restPriceListMockMvc
            .perform(post("/api/price-lists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priceList)))
            .andExpect(status().isCreated());

        // Validate the PriceList in the database
        List<PriceList> priceListList = priceListRepository.findAll();
        assertThat(priceListList).hasSize(databaseSizeBeforeCreate + 1);
        PriceList testPriceList = priceListList.get(priceListList.size() - 1);
        assertThat(testPriceList.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPriceList.getValidTill()).isEqualTo(DEFAULT_VALID_TILL);
        assertThat(testPriceList.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPriceList.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        //        assertThat(testPriceList.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        //        assertThat(testPriceList.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        //        assertThat(testPriceList.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createPriceListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceListRepository.findAll().size();

        // Create the PriceList with an existing ID
        priceList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceListMockMvc
            .perform(post("/api/price-lists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priceList)))
            .andExpect(status().isBadRequest());

        // Validate the PriceList in the database
        List<PriceList> priceListList = priceListRepository.findAll();
        assertThat(priceListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPriceLists() throws Exception {
        // Initialize the database
        priceListRepository.saveAndFlush(priceList);

        // Get all the priceListList
        restPriceListMockMvc
            .perform(get("/api/price-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceList.getId().intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTill").value(hasItem(DEFAULT_VALID_TILL.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_AT))));
    }

    @Test
    @Transactional
    public void getPriceList() throws Exception {
        // Initialize the database
        priceListRepository.saveAndFlush(priceList);

        // Get the priceList
        restPriceListMockMvc
            .perform(get("/api/price-lists/{id}", priceList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(priceList.getId().intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTill").value(DEFAULT_VALID_TILL.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_UPDATED_BY))
            .andExpect(jsonPath("$.lastUpdatedAt").value(sameInstant(DEFAULT_LAST_UPDATED_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingPriceList() throws Exception {
        // Get the priceList
        restPriceListMockMvc.perform(get("/api/price-lists/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceList() throws Exception {
        // Initialize the database
        priceListRepository.saveAndFlush(priceList);

        int databaseSizeBeforeUpdate = priceListRepository.findAll().size();

        // Update the priceList
        PriceList updatedPriceList = priceListRepository.findById(priceList.getId()).get();
        // Disconnect from session so that the updates on updatedPriceList are not directly saved in db
        em.detach(updatedPriceList);
        updatedPriceList.validFrom(UPDATED_VALID_FROM).validTill(UPDATED_VALID_TILL).type(UPDATED_TYPE);

        restPriceListMockMvc
            .perform(
                put("/api/price-lists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedPriceList))
            )
            .andExpect(status().isOk());

        // Validate the PriceList in the database
        List<PriceList> priceListList = priceListRepository.findAll();
        assertThat(priceListList).hasSize(databaseSizeBeforeUpdate);
        PriceList testPriceList = priceListList.get(priceListList.size() - 1);
        assertThat(testPriceList.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPriceList.getValidTill()).isEqualTo(UPDATED_VALID_TILL);
        assertThat(testPriceList.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPriceList.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        //        assertThat(testPriceList.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        //        assertThat(testPriceList.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        //        assertThat(testPriceList.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceList() throws Exception {
        int databaseSizeBeforeUpdate = priceListRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceListMockMvc
            .perform(put("/api/price-lists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priceList)))
            .andExpect(status().isBadRequest());

        // Validate the PriceList in the database
        List<PriceList> priceListList = priceListRepository.findAll();
        assertThat(priceListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePriceList() throws Exception {
        // Initialize the database
        priceListRepository.saveAndFlush(priceList);

        int databaseSizeBeforeDelete = priceListRepository.findAll().size();

        // Delete the priceList
        restPriceListMockMvc
            .perform(delete("/api/price-lists/{id}", priceList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PriceList> priceListList = priceListRepository.findAll();
        assertThat(priceListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
