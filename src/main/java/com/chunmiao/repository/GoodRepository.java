package com.chunmiao.repository;

import com.chunmiao.domain.Good;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Good entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoodRepository extends JpaRepository<Good, Long> {

    // 得到库存
    @Query("select g.stock from Good g where g.id = ?1")
    Long getStockById(Long id);

    // 乐观锁更新库存
    @Modifying
    @Query("update Good g set g.stock=g.stock-1 where g.id = ?1 and g.stock = ?2")
    int decreseStockOptimistic(Long id,Long stock);

    // 悲观锁查询库存
    @Query(value = "select * from Good g where g.id = ?1 for update",nativeQuery = true)
    Good getStockByIdPessimistic(Long id);

}
