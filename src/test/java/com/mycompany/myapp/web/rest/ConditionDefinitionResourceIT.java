package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.ConditionDefinition;
import com.mycompany.myapp.repository.ConditionDefinitionRepository;
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
 * Integration tests for the {@link ConditionDefinitionResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConditionDefinitionResourceIT {
    private static final String DEFAULT_DEFINITION = "AAAAAAAAAA";
    private static final String UPDATED_DEFINITION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ConditionDefinitionRepository conditionDefinitionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConditionDefinitionMockMvc;

    private ConditionDefinition conditionDefinition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConditionDefinition createEntity(EntityManager em) {
        ConditionDefinition conditionDefinition = new ConditionDefinition().definition(DEFAULT_DEFINITION).description(DEFAULT_DESCRIPTION);
        return conditionDefinition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConditionDefinition createUpdatedEntity(EntityManager em) {
        ConditionDefinition conditionDefinition = new ConditionDefinition().definition(UPDATED_DEFINITION).description(UPDATED_DESCRIPTION);
        return conditionDefinition;
    }

    @BeforeEach
    public void initTest() {
        conditionDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createConditionDefinition() throws Exception {
        int databaseSizeBeforeCreate = conditionDefinitionRepository.findAll().size();
        // Create the ConditionDefinition
        restConditionDefinitionMockMvc
            .perform(
                post("/api/condition-definitions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDefinition))
            )
            .andExpect(status().isCreated());

        // Validate the ConditionDefinition in the database
        List<ConditionDefinition> conditionDefinitionList = conditionDefinitionRepository.findAll();
        assertThat(conditionDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        ConditionDefinition testConditionDefinition = conditionDefinitionList.get(conditionDefinitionList.size() - 1);
        assertThat(testConditionDefinition.getDefinition()).isEqualTo(DEFAULT_DEFINITION);
        assertThat(testConditionDefinition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConditionDefinition.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        //        assertThat(testConditionDefinition.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        //        assertThat(testConditionDefinition.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        //        assertThat(testConditionDefinition.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createConditionDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conditionDefinitionRepository.findAll().size();

        // Create the ConditionDefinition with an existing ID
        conditionDefinition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConditionDefinitionMockMvc
            .perform(
                post("/api/condition-definitions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDefinition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConditionDefinition in the database
        List<ConditionDefinition> conditionDefinitionList = conditionDefinitionRepository.findAll();
        assertThat(conditionDefinitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConditionDefinitions() throws Exception {
        // Initialize the database
        conditionDefinitionRepository.saveAndFlush(conditionDefinition);

        // Get all the conditionDefinitionList
        restConditionDefinitionMockMvc
            .perform(get("/api/condition-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conditionDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].definition").value(hasItem(DEFAULT_DEFINITION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_AT))));
    }

    @Test
    @Transactional
    public void getConditionDefinition() throws Exception {
        // Initialize the database
        conditionDefinitionRepository.saveAndFlush(conditionDefinition);

        // Get the conditionDefinition
        restConditionDefinitionMockMvc
            .perform(get("/api/condition-definitions/{id}", conditionDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conditionDefinition.getId().intValue()))
            .andExpect(jsonPath("$.definition").value(DEFAULT_DEFINITION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_UPDATED_BY))
            .andExpect(jsonPath("$.lastUpdatedAt").value(sameInstant(DEFAULT_LAST_UPDATED_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingConditionDefinition() throws Exception {
        // Get the conditionDefinition
        restConditionDefinitionMockMvc.perform(get("/api/condition-definitions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConditionDefinition() throws Exception {
        // Initialize the database
        conditionDefinitionRepository.saveAndFlush(conditionDefinition);

        int databaseSizeBeforeUpdate = conditionDefinitionRepository.findAll().size();

        // Update the conditionDefinition
        ConditionDefinition updatedConditionDefinition = conditionDefinitionRepository.findById(conditionDefinition.getId()).get();
        // Disconnect from session so that the updates on updatedConditionDefinition are not directly saved in db
        em.detach(updatedConditionDefinition);
        updatedConditionDefinition.definition(UPDATED_DEFINITION).description(UPDATED_DESCRIPTION);

        restConditionDefinitionMockMvc
            .perform(
                put("/api/condition-definitions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConditionDefinition))
            )
            .andExpect(status().isOk());

        // Validate the ConditionDefinition in the database
        List<ConditionDefinition> conditionDefinitionList = conditionDefinitionRepository.findAll();
        assertThat(conditionDefinitionList).hasSize(databaseSizeBeforeUpdate);
        ConditionDefinition testConditionDefinition = conditionDefinitionList.get(conditionDefinitionList.size() - 1);
        assertThat(testConditionDefinition.getDefinition()).isEqualTo(UPDATED_DEFINITION);
        assertThat(testConditionDefinition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConditionDefinition.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        //        assertThat(testConditionDefinition.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        //        assertThat(testConditionDefinition.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        //        assertThat(testConditionDefinition.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingConditionDefinition() throws Exception {
        int databaseSizeBeforeUpdate = conditionDefinitionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionDefinitionMockMvc
            .perform(
                put("/api/condition-definitions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDefinition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConditionDefinition in the database
        List<ConditionDefinition> conditionDefinitionList = conditionDefinitionRepository.findAll();
        assertThat(conditionDefinitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConditionDefinition() throws Exception {
        // Initialize the database
        conditionDefinitionRepository.saveAndFlush(conditionDefinition);

        int databaseSizeBeforeDelete = conditionDefinitionRepository.findAll().size();

        // Delete the conditionDefinition
        restConditionDefinitionMockMvc
            .perform(delete("/api/condition-definitions/{id}", conditionDefinition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConditionDefinition> conditionDefinitionList = conditionDefinitionRepository.findAll();
        assertThat(conditionDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
