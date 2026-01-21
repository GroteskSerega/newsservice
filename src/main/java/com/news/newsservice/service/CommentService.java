package com.news.newsservice.service;

import com.news.newsservice.entity.Comment;
import com.news.newsservice.web.dto.v1.CommentFilter;

import java.util.List;

public interface CommentService {

    List<Comment> filterBy(CommentFilter filter);

    List<Comment> findAll();

    Comment findById(Long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void delete(Long id);
}
