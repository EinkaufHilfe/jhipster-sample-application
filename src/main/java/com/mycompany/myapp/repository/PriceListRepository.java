package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PriceList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PriceList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {
}
