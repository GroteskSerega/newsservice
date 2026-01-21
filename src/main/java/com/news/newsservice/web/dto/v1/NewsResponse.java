package com.news.newsservice.web.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsResponse {

    private Long id;

    private String text;

    private UserResponse user;

    private CategoryResponse category;

    private List<CommentResponse> comments =
            new ArrayList<>();

    private Integer countComments;

    private Instant createAt;

    private Instant updateAt;
}
