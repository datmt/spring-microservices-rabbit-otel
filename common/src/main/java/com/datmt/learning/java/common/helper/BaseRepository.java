package com.datmt.learning.java.common.helper;

import com.datmt.learning.java.common.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
    Optional<T> findByUlid(String ulid);

    void deleteByUlid(String ulid);

    boolean existsByUlid(String ulid);

    Page<T> findAll(Pageable pageable);
}
