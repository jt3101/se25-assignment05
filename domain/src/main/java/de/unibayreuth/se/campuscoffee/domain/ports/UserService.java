package de.unibayreuth.se.campuscoffee.domain.ports;

import java.util.List;
import de.unibayreuth.se.campuscoffee.domain.model.User;
import org.springframework.lang.NonNull;

import de.unibayreuth.se.campuscoffee.domain.exceptions.UserNotFoundException;

/**
 * Interface for the implementation of the user service that the domain module
 * provides as a port and implements itself.
 */
public interface UserService {
    void clear();

    //
    @NonNull
    List<User> getAll();

    @NonNull
    User getById(@NonNull Long id) throws UserNotFoundException;

    @NonNull
    User getByLoginName(@NonNull String loginName) throws UserNotFoundException;

    @NonNull
    User upsert(@NonNull User user) throws UserNotFoundException;
}
