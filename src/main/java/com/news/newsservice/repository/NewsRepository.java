package com.news.newsservice.repository;

import com.news.newsservice.entity.News;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

//    @Override
//    @EntityGraph(attributePaths = {"comments", "user", "category"})
//    List<News> findAll();
}
