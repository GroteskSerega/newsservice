package com.news.newsservice.mapper.v1;

import com.news.newsservice.entity.News;
import com.news.newsservice.web.dto.v1.NewsListResponse;
import com.news.newsservice.web.dto.v1.NewsResponse;
import com.news.newsservice.web.dto.v1.NewsUpsertRequest;
import org.mapstruct.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommentMapper.class})
public interface NewsMapper {

    News requestToNews(NewsUpsertRequest request, UserDetails userDetails);

//    @Mapping(target = "countComments", expression = "java(news.getComments() != null ? news.getComments().size() : 0)")
    NewsResponse newsToResponse(News news);

    @Mapping(target = "comments", ignore = true)
    NewsResponse newsToResponseWithoutComments(News news);

    default NewsListResponse newsListToNewsListResponse(List<News> news) {
        return new NewsListResponse(news
                .stream()
                .map(this::newsToResponseWithoutComments)
                .toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    void updateNews(NewsUpsertRequest request, @MappingTarget News news);
}
