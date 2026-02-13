package com.news.newsservice.mapper.v1;

import com.news.newsservice.entity.Comment;
import com.news.newsservice.web.dto.v1.CommentListResponse;
import com.news.newsservice.web.dto.v1.CommentResponse;
import com.news.newsservice.web.dto.v1.CommentUpsertRequest;
import org.mapstruct.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment requestToComment(CommentUpsertRequest request, UserDetails userDetails);

    @Mapping(source = "news.id", target = "newsId")
    CommentResponse commentToResponse(Comment comment);

    List<CommentResponse> commentListToListResponse(List<Comment> comments);

    default CommentListResponse commentListToCommentListResponse(List<Comment> comments) {
        return new CommentListResponse(commentListToListResponse(comments));
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "news", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    void updateComment(CommentUpsertRequest request, @MappingTarget Comment comment);
}
