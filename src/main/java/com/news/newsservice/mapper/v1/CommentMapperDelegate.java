package com.news.newsservice.mapper.v1;


import com.news.newsservice.entity.Comment;
import com.news.newsservice.service.NewsService;
import com.news.newsservice.service.UserService;
import com.news.newsservice.web.dto.v1.CommentUpsertRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    @Override
    public Comment requestToComment(CommentUpsertRequest request) {
        Comment comment = new Comment();
        comment.setMessage(request.getMessage());
        comment.setUser(userService.findById(request.getUserId()));
        comment.setNews(newsService.findById(request.getNewsId()));

        return comment;
    }

    @Override
    public Comment requestToComment(Long commentId, CommentUpsertRequest request) {
        Comment comment = requestToComment(request);
        comment.setId(commentId);

        return comment;
    }
}
