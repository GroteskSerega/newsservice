package com.news.newsservice.mapper.v1;

import com.news.newsservice.entity.User;
import com.news.newsservice.web.dto.v1.UserUpsertRequest;
import com.news.newsservice.web.dto.v1.UserListResponse;
import com.news.newsservice.web.dto.v1.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewsMapper.class})
public interface UserMapper {

    User requestToUser(UserUpsertRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId,
                       UserUpsertRequest request);

    UserResponse userToResponse(User user);

    default UserListResponse userListToUserListResponse(List<User> users) {
        UserListResponse response = new UserListResponse();

        response.setUsers(users
                .stream()
                .map(this::userToResponse)
                .collect(Collectors.toList()));

        return response;
    }
}
