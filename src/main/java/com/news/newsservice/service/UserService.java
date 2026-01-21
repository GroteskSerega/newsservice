package com.news.newsservice.service;

import com.news.newsservice.entity.User;
import com.news.newsservice.web.dto.v1.UserFilter;

import java.util.List;


public interface UserService {

    List<User> filterBy(UserFilter filter);

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User update(User user);

    void delete(Long id);
}
