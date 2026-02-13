package com.news.newsservice.service.impl;

import com.news.newsservice.aop.AuthoriseUsernameForCommentUpdateAndDelete;
import com.news.newsservice.entity.Comment;
import com.news.newsservice.exception.EntityNotFoundException;
import com.news.newsservice.mapper.v1.CommentMapper;
import com.news.newsservice.repository.CommentRepository;
import com.news.newsservice.repository.CommentSpecification;
import com.news.newsservice.service.CommentService;
import com.news.newsservice.web.dto.v1.CommentFilter;
import com.news.newsservice.web.dto.v1.CommentUpsertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static com.news.newsservice.service.MessageTemplates.TEMPLATE_COMMENT_NOT_FOUND_EXCEPTION;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    public List<Comment> findAll(CommentFilter filter) {
        return commentRepository.findAll(CommentSpecification.withFilter(filter),
                PageRequest.of(
                        filter.pageNumber(),
                        filter.pageSize()
                )).getContent();
    }

    @Override
    public Comment findById(UUID id) {
        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(TEMPLATE_COMMENT_NOT_FOUND_EXCEPTION, id)));
    }

    @Transactional
    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    @AuthoriseUsernameForCommentUpdateAndDelete
    public Comment update(UUID commentId, CommentUpsertRequest request) {
        Comment existedComment = findById(commentId);

        commentMapper.updateComment(request, existedComment);

        return commentRepository.save(existedComment);
    }

    @Override
    public UUID findUserIdById(UUID id) {
        return commentRepository.findUserIdById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(TEMPLATE_COMMENT_NOT_FOUND_EXCEPTION, id)));
    }

    @Transactional
    @Override
    @AuthoriseUsernameForCommentUpdateAndDelete
    public void delete(UUID id) {
        findById(id);

        commentRepository.deleteById(id);
    }
}
