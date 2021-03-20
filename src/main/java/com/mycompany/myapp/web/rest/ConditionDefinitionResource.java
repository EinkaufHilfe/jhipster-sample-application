package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ConditionDefinition;
import com.mycompany.myapp.repository.ConditionDefinitionRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ConditionDefinition}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConditionDefinitionResource {
    private final Logger log = LoggerFactory.getLogger(ConditionDefinitionResource.class);

    private static final String ENTITY_NAME = "conditionDefinition";
    private final ConditionDefinitionRepository conditionDefinitionRepository;
    private HeaderUtil headerUtil;

    public ConditionDefinitionResource(ConditionDefinitionRepository conditionDefinitionRepository, HeaderUtil headerUtil) {
        this.conditionDefinitionRepository = conditionDefinitionRepository;
        this.headerUtil = headerUtil;
    }

    /**
     * {@code POST  /condition-definitions} : Create a new conditionDefinition.
     *
     * @param conditionDefinition the conditionDefinition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conditionDefinition, or with status {@code 400 (Bad Request)} if the conditionDefinition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/condition-definitions")
    public ResponseEntity<ConditionDefinition> createConditionDefinition(@RequestBody ConditionDefinition conditionDefinition)
        throws URISyntaxException {
        if (conditionDefinition.getId() != null) {
            throw new BadRequestAlertException("A new conditionDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConditionDefinition result = conditionDefinitionRepository.save(conditionDefinition);
        return ResponseEntity
            .created(new URI("/api/condition-definitions/" + result.getId()))
            .headers(headerUtil.createEntityCreationAlert(true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /condition-definitions} : Updates an existing conditionDefinition.
     *
     * @param conditionDefinition the conditionDefinition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conditionDefinition,
     * or with status {@code 400 (Bad Request)} if the conditionDefinition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conditionDefinition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/condition-definitions")
    public ResponseEntity<ConditionDefinition> updateConditionDefinition(@RequestBody ConditionDefinition conditionDefinition)
        throws URISyntaxException {
        if (conditionDefinition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConditionDefinition result = conditionDefinitionRepository.save(conditionDefinition);
        return ResponseEntity
            .ok()
            .headers(headerUtil.createEntityUpdateAlert(true, ENTITY_NAME, conditionDefinition.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /condition-definitions} : get all the conditionDefinitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conditionDefinitions in body.
     */
    @GetMapping("/condition-definitions")
    public List<ConditionDefinition> getAllConditionDefinitions() {
        return conditionDefinitionRepository.findAll();
    }

    /**
     * {@code GET  /condition-definitions/:id} : get the "id" conditionDefinition.
     *
     * @param id the id of the conditionDefinition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conditionDefinition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/condition-definitions/{id}")
    public ResponseEntity<ConditionDefinition> getConditionDefinition(@PathVariable Long id) {
        Optional<ConditionDefinition> conditionDefinition = conditionDefinitionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conditionDefinition);
    }

    /**
     * {@code DELETE  /condition-definitions/:id} : delete the "id" conditionDefinition.
     *
     * @param id the id of the conditionDefinition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/condition-definitions/{id}")
    public ResponseEntity<Void> deleteConditionDefinition(@PathVariable Long id) {
        conditionDefinitionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(headerUtil.createEntityDeletionAlert(true, ENTITY_NAME, id.toString())).build();
    }
}
