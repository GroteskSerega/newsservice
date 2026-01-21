package com.news.newsservice.repository;

import com.news.newsservice.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

//    @Override
//    @EntityGraph(attributePaths = {"news", "user", "news.category"})
//    List<Comment> findAll();
}
