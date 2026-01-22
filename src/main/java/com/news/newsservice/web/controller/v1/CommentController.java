package com.news.newsservice.web.controller.v1;

import com.news.newsservice.mapper.v1.CommentMapper;
import com.news.newsservice.service.CommentService;
import com.news.newsservice.web.dto.ErrorResponse;
import com.news.newsservice.web.dto.v1.CommentFilter;
import com.news.newsservice.web.dto.v1.CommentListResponse;
import com.news.newsservice.web.dto.v1.CommentResponse;
import com.news.newsservice.web.dto.v1.CommentUpsertRequest;
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
@Tag(name = "Comment v1",
        description = "Comment API version V1")
@RequestMapping("/api/v1/comment")
@RestController
public class CommentController {

    private final CommentMapper commentMapper;

    private final CommentService commentService;

    @Operation(
            summary = "Get comments",
            description = "Get comments by filters"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentListResponse.class))
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
    public ResponseEntity<CommentListResponse> findAll(@Valid CommentFilter commentFilter) {
        return ResponseEntity.ok(
                commentMapper.commentListToCommentListResponse(
                        commentService.findAll(commentFilter)
                )
        );
    }

    @Operation(
            summary = "Get comment by id",
            description = "Get comment by id. Return id, message"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentResponse.class))
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
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                commentMapper.commentToResponse(
                        commentService.findById(id)
                )
        );
    }

    @Operation(
            summary = "Create comment",
            description = "Create comment with params message, userId, newsId. Return dto with fields"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentResponse.class))
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
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid CommentUpsertRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        commentMapper.commentToResponse(
                                commentService.save(
                                        commentMapper.requestToComment(request)
                                )
                        )
                );
    }

    @Operation(
            summary = "Update comment",
            description = "Update comment by id. Return dto with fields"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentResponse.class))
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
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId,
                                                  @RequestBody @Valid CommentUpsertRequest request) {
        return ResponseEntity.ok(
                commentMapper.commentToResponse(
                        commentService.update(
                                commentMapper.requestToComment(commentId, request)
                        )
                )
        );
    }

    @Operation(
            summary = "Delete comment by id",
            description = "Delete comment by id. Return http code"
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
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
