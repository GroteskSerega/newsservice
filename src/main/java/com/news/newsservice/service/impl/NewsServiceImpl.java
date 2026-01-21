package com.news.newsservice.service.impl;

import com.news.newsservice.aop.AuthUsernameForNewsUpdateAndDelete;
import com.news.newsservice.entity.News;
import com.news.newsservice.exception.EntityNotFoundException;
import com.news.newsservice.repository.NewsRepository;
import com.news.newsservice.repository.NewsSpecification;
import com.news.newsservice.service.NewsService;
import com.news.newsservice.utils.BeanUtils;
import com.news.newsservice.web.dto.v1.NewsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

import static com.news.newsservice.service.MessageTemplates.TEMPLATE_NEWS_NOT_FOUND_EXCEPTION;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    public List<News> filterBy(NewsFilter filter) {
        return newsRepository.findAll(NewsSpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(),
                        filter.getPageSize()
                )).getContent();
    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(TEMPLATE_NEWS_NOT_FOUND_EXCEPTION, id)));
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    @AuthUsernameForNewsUpdateAndDelete
    public News update(News news) {
        News existedNews = findById(news.getId());

        BeanUtils.copyNonNullProperties(news, existedNews);

        return newsRepository.save(existedNews);
    }

    @Override
    @AuthUsernameForNewsUpdateAndDelete
    public void delete(Long id) {
        findById(id);

        newsRepository.deleteById(id);
    }
}
