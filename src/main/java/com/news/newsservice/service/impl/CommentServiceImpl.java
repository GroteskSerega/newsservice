package com.news.newsservice.service.impl;

import com.news.newsservice.aop.AuthUsernameForCommentUpdateAndDelete;
import com.news.newsservice.entity.Comment;
import com.news.newsservice.exception.EntityNotFoundException;
import com.news.newsservice.repository.CommentRepository;
import com.news.newsservice.repository.CommentSpecification;
import com.news.newsservice.service.CommentService;
import com.news.newsservice.utils.BeanUtils;
import com.news.newsservice.web.dto.v1.CommentFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

import static com.news.newsservice.service.MessageTemplates.TEMPLATE_COMMENT_NOT_FOUND_EXCEPTION;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAll(CommentFilter filter) {
        return commentRepository.findAll(CommentSpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(),
                        filter.getPageSize()
                )).getContent();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(TEMPLATE_COMMENT_NOT_FOUND_EXCEPTION, id)));
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @AuthUsernameForCommentUpdateAndDelete
    public Comment update(Comment comment) {
        Comment existedComment = findById(comment.getId());

        BeanUtils.copyNonNullProperties(comment, existedComment);

        return commentRepository.save(existedComment);
    }

    @Override
    @AuthUsernameForCommentUpdateAndDelete
    public void delete(Long id) {
        findById(id);

        commentRepository.deleteById(id);
    }
}
