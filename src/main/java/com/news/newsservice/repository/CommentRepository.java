package com.news.newsservice.repository;

import com.news.newsservice.entity.Comment;
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
public interface CommentRepository extends JpaRepository<Comment, UUID>, JpaSpecificationExecutor<Comment> {

    @EntityGraph(attributePaths = {"user", "news", "news.category"})
    Page<Comment> findAll(Specification<Comment> spec, Pageable pageable);

    @Query("SELECT c.user.id FROM comments c WHERE c.id = :id")
    Optional<UUID> findUserIdById(@Param("id") UUID id);
}
