package com.news.newsservice.service;

import com.news.newsservice.entity.Comment;
import com.news.newsservice.web.dto.v1.CommentFilter;
import com.news.newsservice.web.dto.v1.CommentUpsertRequest;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    List<Comment> findAll(CommentFilter filter);

    Comment findById(UUID id);

    Comment save(Comment comment);

    Comment update(UUID commentId, CommentUpsertRequest request);

    UUID findUserIdById(UUID id);

    void delete(UUID id);
}
