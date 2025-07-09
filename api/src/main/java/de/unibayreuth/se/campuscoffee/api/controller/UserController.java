package de.unibayreuth.se.campuscoffee.api.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import de.unibayreuth.se.campuscoffee.api.dtos.UserDto;
import de.unibayreuth.se.campuscoffee.domain.exceptions.UserNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.ports.UserService;
import de.unibayreuth.se.campuscoffee.api.mapper.UserDtoMapper;

@OpenAPIDefinition(info = @Info(title = "CampusCoffee", version = "0.0.1"))
@Tag(name = "Users")
@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
        private final UserService userService;
        private final UserDtoMapper userDtoMapper;

        @Operation(summary = "Get all users.", responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = UserDto.class)), description = "All users as a JSON array.")
        })
        @GetMapping("")
        public ResponseEntity<List<UserDto>> getAll() {
                return ResponseEntity.ok(
                                userService.getAll().stream()
                                                .map(userDtoMapper::fromDomain)
                                                .toList());
        }

        @Operation(summary = "Get user by ID.", responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)), description = "The user with the provided ID as a JSON object."),
                        @ApiResponse(responseCode = "400", description = "No user with the provided ID could be found.")
        })
        @GetMapping("/{id}")
        public ResponseEntity<UserDto> getById(@PathVariable Long id) {
                try {
                        return ResponseEntity.ok(
                                        userDtoMapper.fromDomain(userService.getById(id)));
                } catch (UserNotFoundException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                }
        }

        @Operation(summary = "Get user by name.", responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)), description = "The user with the provided name as a JSON object."),
                        @ApiResponse(responseCode = "400", description = "No user with the provided name could be found.")
        })
        @GetMapping("/filter")
        public ResponseEntity<UserDto> getByName(@RequestParam("name") String name) {
                try {
                        return ResponseEntity.ok(
                                        userDtoMapper.fromDomain(userService.getByLoginName(name)));
                } catch (UserNotFoundException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                }
        }

        @Operation(summary = "Creates a new user.", responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)), description = "The new user as a JSON object."),
                        @ApiResponse(responseCode = "400", description = "ID is not empty.")
        })
        @PostMapping("")
        public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto userDto) {
                return upsert(userDto);
        }

        @Operation(summary = "Updates an existing user.", responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)), description = "The updated user as a JSON object."),
                        @ApiResponse(responseCode = "400", description = "IDs in path and body do not match or no user with the provided ID could be found.")
        })
        @PutMapping("/{id}")
        public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
                if (!id.equals(userDto.getId())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "User ID in path and body do not match.");
                }

                try {
                        return upsert(userDto);
                } catch (UserNotFoundException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                }
        }

        private ResponseEntity<UserDto> upsert(UserDto userDto) throws UserNotFoundException {
                return ResponseEntity.ok(
                                userDtoMapper.fromDomain(
                                                userService.upsert(
                                                                userDtoMapper.toDomain(userDto))));
        }

}
