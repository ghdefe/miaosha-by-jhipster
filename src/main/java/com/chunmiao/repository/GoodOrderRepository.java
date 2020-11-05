package com.chunmiao.repository;

import com.chunmiao.domain.GoodOrder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GoodOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoodOrderRepository extends JpaRepository<GoodOrder, Long> {
}
