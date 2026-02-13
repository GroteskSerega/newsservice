package com.news.newsservice.service.impl;

import com.news.newsservice.aop.AuthoriseUsernameForUserUpdateAndDelete;
import com.news.newsservice.entity.Role;
import com.news.newsservice.entity.User;
import com.news.newsservice.exception.EntityNotFoundException;
import com.news.newsservice.mapper.v1.UserMapper;
import com.news.newsservice.repository.UserRepository;
import com.news.newsservice.repository.UserSpecification;
import com.news.newsservice.service.UserService;
import com.news.newsservice.web.dto.v1.UserFilter;
import com.news.newsservice.web.dto.v1.UserUpsertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.news.newsservice.service.MessageTemplates.TEMPLATE_USER_NOT_FOUND_EXCEPTION;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<User> findAll(UserFilter filter) {
        return userRepository.findAll(UserSpecification.withFilter(filter),
                PageRequest.of(
                        filter.pageNumber(),
                        filter.pageSize()
                )).getContent();
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(TEMPLATE_USER_NOT_FOUND_EXCEPTION, id)));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Username not found!"));
    }

    @Transactional
    @Override
    public User save(User user, Role role) {
        user.setRoles(new ArrayList<>(List.of(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        role.setUser(user);

        return userRepository.saveAndFlush(user);
    }

    @Transactional
    @Override
    @AuthoriseUsernameForUserUpdateAndDelete
    public User update(UUID userId, UserUpsertRequest request, Role role) {
        User existedUser = findById(userId);

        userMapper.updateUser(request, existedUser);

        if (request.password() != null && !request.password().isBlank()) {
            existedUser.setPassword(passwordEncoder.encode(request.password()));
        }

        if (role != null) {
            existedUser.getRoles().clear();
            role.setUser(existedUser);
            existedUser.getRoles().add(role);
        }

        return userRepository.save(existedUser);
    }

    @Transactional
    @Override
    @AuthoriseUsernameForUserUpdateAndDelete
    public void delete(UUID id) {
        findById(id);

        userRepository.deleteById(id);
    }
}
