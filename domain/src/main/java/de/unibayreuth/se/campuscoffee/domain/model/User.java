package de.unibayreuth.se.campuscoffee.domain.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Domain class that stores the user metadata.
 */
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id; // null when the user has not been created yet
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("UTC")); // set on User creation
    private LocalDateTime updatedAt = createdAt; // set on User creation and update
    @NonNull
    private String loginName;
    @NonNull
    private String emailAddress;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
}
