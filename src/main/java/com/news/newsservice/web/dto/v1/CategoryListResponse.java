package com.news.newsservice.web.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListResponse {

    private List<CategoryResponse> categories =
            new ArrayList<>();
}
