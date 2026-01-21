package com.news.newsservice.web.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String secondName;

    private String lastName;

    private String email;

    private Instant createAt;

    private Instant updateAt;
}
