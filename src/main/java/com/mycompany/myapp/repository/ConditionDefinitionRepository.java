package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ConditionDefinition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConditionDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConditionDefinitionRepository extends JpaRepository<ConditionDefinition, Long> {
}
