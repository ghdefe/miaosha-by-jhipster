package com.chunmiao.repository;

import com.chunmiao.domain.Good;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Good entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoodRepository extends JpaRepository<Good, Long> {

    @Query("select g.stock from Good g where g.id = ?1")
    Long getStockById(Long id);
}
