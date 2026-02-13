package com.news.newsservice.service;

import com.news.newsservice.entity.Role;
import com.news.newsservice.entity.User;
import com.news.newsservice.web.dto.v1.UserFilter;
import com.news.newsservice.web.dto.v1.UserUpsertRequest;

import java.util.List;
import java.util.UUID;


public interface UserService {

    List<User> findAll(UserFilter filter);

    User findById(UUID id);

    User findByUsername(String username);

    User save(User user, Role role);

    User update(UUID userId, UserUpsertRequest user, Role role);

    void delete(UUID id);
}
