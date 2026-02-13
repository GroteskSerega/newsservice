package com.news.newsservice.mapper.v1;


import com.news.newsservice.entity.Comment;
import com.news.newsservice.service.NewsService;
import com.news.newsservice.service.UserService;
import com.news.newsservice.web.dto.v1.CommentUpsertRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class CommentMapperDelegate implements CommentMapper {

    private UserService userService;

    private NewsService newsService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public Comment requestToComment(CommentUpsertRequest request, UserDetails userDetails) {
        Comment comment = new Comment();
        comment.setMessage(request.message());
        comment.setUser(userService.findByUsername(userDetails.getUsername()));
        comment.setNews(newsService.findById(request.newsId()));

        return comment;
    }
}
