package com.news.newsservice.mapper.v1;

import com.news.newsservice.entity.Category;
import com.news.newsservice.web.dto.v1.CategoryListResponse;
import com.news.newsservice.web.dto.v1.CategoryResponse;
import com.news.newsservice.web.dto.v1.CategoryUpsertRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category requestToCategory(CategoryUpsertRequest request);

    CategoryResponse categoryToResponse(Category category);

    default CategoryListResponse categoryListToCategoryListResponse(List<Category> categories) {
        return new CategoryListResponse(categories
                .stream()
                .map(this::categoryToResponse)
                .toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    void updateCategory(CategoryUpsertRequest request, @MappingTarget Category category);
}
