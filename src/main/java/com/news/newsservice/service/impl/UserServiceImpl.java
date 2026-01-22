package com.news.newsservice.service.impl;

import com.news.newsservice.entity.User;
import com.news.newsservice.exception.EntityNotFoundException;
import com.news.newsservice.repository.UserRepository;
import com.news.newsservice.repository.UserSpecification;
import com.news.newsservice.service.UserService;
import com.news.newsservice.utils.BeanUtils;
import com.news.newsservice.web.dto.v1.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

import static com.news.newsservice.service.MessageTemplates.TEMPLATE_USER_NOT_FOUND_EXCEPTION;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll(UserFilter filter) {
        return userRepository.findAll(UserSpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(),
                        filter.getPageSize()
                )).getContent();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(TEMPLATE_USER_NOT_FOUND_EXCEPTION, id)));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User existedUser = findById(user.getId());

        BeanUtils.copyNonNullProperties(user, existedUser);

        return userRepository.save(existedUser);
    }

    @Override
    public void delete(Long id) {
        findById(id);

        userRepository.deleteById(id);
    }
}
