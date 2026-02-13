package com.news.newsservice.service.impl;

import com.news.newsservice.aop.AuthoriseUsernameForNewsUpdateAndDelete;
import com.news.newsservice.entity.News;
import com.news.newsservice.exception.EntityNotFoundException;
import com.news.newsservice.mapper.v1.NewsMapper;
import com.news.newsservice.repository.NewsRepository;
import com.news.newsservice.repository.NewsSpecification;
import com.news.newsservice.service.NewsService;
import com.news.newsservice.web.dto.v1.NewsFilter;
import com.news.newsservice.web.dto.v1.NewsUpsertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static com.news.newsservice.service.MessageTemplates.TEMPLATE_NEWS_NOT_FOUND_EXCEPTION;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    @Override
    public List<News> findAll(NewsFilter filter) {
        return newsRepository.findAll(NewsSpecification.withFilter(filter),
                PageRequest.of(
                        filter.pageNumber(),
                        filter.pageSize()
                )).getContent();
    }

    @Override
    public News findById(UUID id) {
        return newsRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(TEMPLATE_NEWS_NOT_FOUND_EXCEPTION, id)));
    }

    @Transactional
    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Transactional
    @Override
    @AuthoriseUsernameForNewsUpdateAndDelete
    public News update(UUID newsId, NewsUpsertRequest request) {
        News existedNews = findById(newsId);

        newsMapper.updateNews(request, existedNews);

        return newsRepository.save(existedNews);
    }

    @Override
    public UUID findUserIdById(UUID id) {
        return newsRepository.findUserIdById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(TEMPLATE_NEWS_NOT_FOUND_EXCEPTION, id)));
    }

    @Transactional
    @Override
    @AuthoriseUsernameForNewsUpdateAndDelete
    public void delete(UUID id) {
        findById(id);

        newsRepository.deleteById(id);
    }
}
