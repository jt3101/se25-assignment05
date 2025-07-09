package de.unibayreuth.se.campuscoffee.api.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for user metadata.
 *
 */
@Data
@Builder(toBuilder = true)
public class UserDto {
    private Long id; // id is null when creating a new task
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NotBlank
    @Pattern(regexp = "^\\w+$", message = "It's a bad login")
    @Size(max = 255)
    private final String loginName;
    @Email(message = "Not normal email")
    private final String emailAddress;
    @Size(min = 1, max = 255, message = "It can't be blank.")
    private final String firstName;
    @Size(min = 1, max = 255, message = "It can't be blank")
    private final String lastName;
}
