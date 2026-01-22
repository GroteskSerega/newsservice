package com.news.newsservice.web.controller.v1;

import com.news.newsservice.mapper.v1.NewsMapper;
import com.news.newsservice.service.NewsService;
import com.news.newsservice.web.dto.ErrorResponse;
import com.news.newsservice.web.dto.v1.NewsFilter;
import com.news.newsservice.web.dto.v1.NewsListResponse;
import com.news.newsservice.web.dto.v1.NewsResponse;
import com.news.newsservice.web.dto.v1.NewsUpsertRequest;
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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "News V1",
        description = "News API version V1")
@RequestMapping("/api/v1/news")
@RestController
public class NewsController {

    private final NewsMapper newsMapper;

    private final NewsService newsService;

    @Operation(
            summary = "Get news",
            description = "Get all news"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NewsListResponse.class))
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
    public ResponseEntity<NewsListResponse> findAll(@Valid NewsFilter filter) {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(
                        newsService.findAll(filter)
                )
        );
    }

    @Operation(
            summary = "Get news by id",
            description = "Get news by id. Return id, text, user, category, createAt, updateAt"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NewsResponse.class))
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
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                newsMapper.newsToResponse(
                        newsService.findById(id)
                )
        );
    }

    @Operation(
            summary = "Create news",
            description = "Create news with params text, userId, categoryId. Return dto with fields"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NewsResponse.class))
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
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid NewsUpsertRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        newsMapper.newsToResponse(
                                newsService.save(
                                        newsMapper.requestToNews(request)
                                )
                        )
                );
    }

    @Operation(
            summary = "Update news",
            description = "Update news by id. Return dto with fields"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NewsResponse.class))
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
                    responseCode = "401",
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
    public ResponseEntity<NewsResponse> update(@PathVariable("id") Long newsId,
                                               @RequestBody @Valid NewsUpsertRequest request) {
        return ResponseEntity.ok(
                newsMapper.newsToResponse(
                        newsService.update(
                                newsMapper.requestToNews(newsId, request)
                        )
                )
        );
    }

    @Operation(
            summary = "Delete news by id",
            description = "Delete news by id. Return http code"
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
                    responseCode = "401",
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
