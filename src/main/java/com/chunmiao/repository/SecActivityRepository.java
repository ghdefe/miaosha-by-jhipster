package com.chunmiao.repository;

import com.chunmiao.domain.SecActivity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SecActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecActivityRepository extends JpaRepository<SecActivity, Long> {
}
