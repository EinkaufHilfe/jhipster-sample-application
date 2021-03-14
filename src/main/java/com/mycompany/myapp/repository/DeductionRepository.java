package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Deduction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Deduction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeductionRepository extends JpaRepository<Deduction, Long> {
}
