package com.news.newsservice.web.controller.v1;

import com.news.newsservice.mapper.v1.UserMapper;
import com.news.newsservice.service.UserService;
import com.news.newsservice.web.dto.ErrorResponse;
import com.news.newsservice.web.dto.v1.UserFilter;
import com.news.newsservice.web.dto.v1.UserUpsertRequest;
import com.news.newsservice.web.dto.v1.UserListResponse;
import com.news.newsservice.web.dto.v1.UserResponse;
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
@Tag(name = "User V1",
        description = "User API version V1")
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private final UserMapper userMapper;

    private final UserService userService;

    @Operation(
            summary = "Get users",
            description = "Get all users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserListResponse.class))
                    }
            )
    })
    @GetMapping
    public ResponseEntity<UserListResponse> findAll() {
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(
                        userService.findAll()
                )
        );
    }

    @Operation(
            summary = "Get users",
            description = "Get users by filters"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserListResponse.class))
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
    @GetMapping("/filter")
    public ResponseEntity<UserListResponse> filterBy(@Valid UserFilter filter) {
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(
                        userService.filterBy(filter)
                )
        );
    }

    @Operation(
            summary = "Get user by id",
            description = "Get user by id. Return id, username, password"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class))
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
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userMapper.userToResponse(userService.findById(id))
        );
    }

    @Operation(
            summary = "Create user",
            description = "Create user with params username, password, email, id. Return dto with fields"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class))
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
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserUpsertRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(
                        userService.save(
                                userMapper.requestToUser(request)))
                );
    }

    @Operation(
            summary = "Update user",
            description = "Update user by id. Return dto with fields"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class))
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
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId,
                                               @RequestBody @Valid UserUpsertRequest request) {
        return ResponseEntity.ok(
                userMapper.userToResponse(userService.update(
                        userMapper.requestToUser(userId,
                                request)))
        );
    }

    @Operation(
            summary = "Delete user by id",
            description = "Delete user by id. Return http code"
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
