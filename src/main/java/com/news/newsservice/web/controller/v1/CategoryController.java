package com.news.newsservice.web.controller.v1;

import com.news.newsservice.mapper.v1.CategoryMapper;
import com.news.newsservice.service.CategoryService;
import com.news.newsservice.web.dto.ErrorResponse;
import com.news.newsservice.web.dto.v1.CategoryFilter;
import com.news.newsservice.web.dto.v1.CategoryListResponse;
import com.news.newsservice.web.dto.v1.CategoryResponse;
import com.news.newsservice.web.dto.v1.CategoryUpsertRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequiredArgsConstructor
@Tag(name = "Category V1",
        description = "Category API version V1")
@RequestMapping("/api/v1/category")
@RestController
public class CategoryController {

    private final CategoryMapper categoryMapper;

    private final CategoryService categoryService;

    @Operation(
            summary = "Get categories",
            description = "Get categories by filters"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CategoryListResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }
            )
    })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryListResponse> findAll(@Valid CategoryFilter filter) {
        return ResponseEntity.ok(
                categoryMapper.categoryListToCategoryListResponse(
                        categoryService.findAll(filter)
                )
        );
    }

    @Operation(
            summary = "Create category",
            description = "Create category with parameter category. Return dto with fields"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CategoryResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryUpsertRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        categoryMapper.categoryToResponse(
                                categoryService.save(
                                        categoryMapper.requestToCategory(request)
                                )
                        )
                );
    }

    @Operation(
            summary = "Update category",
            description = "Update category with id. Return dto with fields"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CategoryResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") UUID categoryId,
                                                   @RequestBody @Valid CategoryUpsertRequest request) {
        return ResponseEntity.ok(
                categoryMapper.categoryToResponse(
                        categoryService.update(categoryId, request)
                )
        );
    }

    @Operation(
            summary = "Delete category by id",
            description = "Delete category by id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema)
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
