package com.news.newsservice.repository;

import com.news.newsservice.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID>, JpaSpecificationExecutor<News> {

    @Override
    @EntityGraph(attributePaths = {"user", "category"})
    Page<News> findAll(Specification<News> spec, Pageable pageable);

    @Query("SELECT n.user.id FROM news n WHERE n.id = :id")
    Optional<UUID> findUserIdById(@Param("id") UUID id);
}
