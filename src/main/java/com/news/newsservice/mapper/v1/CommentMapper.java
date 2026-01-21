package com.news.newsservice.mapper.v1;

import com.news.newsservice.entity.Comment;
import com.news.newsservice.web.dto.v1.CommentListResponse;
import com.news.newsservice.web.dto.v1.CommentResponse;
import com.news.newsservice.web.dto.v1.CommentUpsertRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment requestToComment(CommentUpsertRequest request);

    @Mapping(source = "commentId", target = "id")
    Comment requestToComment(Long commentId, CommentUpsertRequest request);

    @Mapping(source = "news.id", target = "newsId")
    CommentResponse commentToResponse(Comment comment);

    List<CommentResponse> commentListToListResponse(List<Comment> comments);

    default CommentListResponse commentListToCommentListResponse(List<Comment> comments) {
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentListToListResponse(comments));

        return response;
    }

//    default CommentListResponse commentListToCommentListResponse(List<Comment> comments) {
//        CommentListResponse response = new CommentListResponse();
//
//        response.setComments(comments
//                .stream()
//                .map(this::commentToResponse)
//                .collect(Collectors.toList()));
//
//        return response;
//    }
}
