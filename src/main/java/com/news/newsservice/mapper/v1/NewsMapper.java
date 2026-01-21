package com.news.newsservice.mapper.v1;

import com.news.newsservice.entity.News;
import com.news.newsservice.web.dto.v1.NewsListResponse;
import com.news.newsservice.web.dto.v1.NewsResponse;
import com.news.newsservice.web.dto.v1.NewsUpsertRequest;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommentMapper.class})
public interface NewsMapper {

    News requestToNews(NewsUpsertRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, NewsUpsertRequest request);

    @Mapping(target = "countComments", expression = "java(news.getComments() != null ? news.getComments().size() : 0)")
    NewsResponse newsToResponse(News news);

    default NewsListResponse newsListToNewsListResponse(List<News> news) {
        NewsListResponse response = new NewsListResponse();

        response.setNews(news
                .stream()
                .map(this::newsToResponse)
                .collect(Collectors.toList()));

        response.getNews()
                .forEach(newsResponse -> newsResponse.setComments(null));

        return response;
    }
}
