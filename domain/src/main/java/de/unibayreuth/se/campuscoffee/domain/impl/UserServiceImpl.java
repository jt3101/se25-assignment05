package de.unibayreuth.se.campuscoffee.domain.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import de.unibayreuth.se.campuscoffee.domain.exceptions.UserNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.model.User;
import de.unibayreuth.se.campuscoffee.domain.ports.UserDataService;
import de.unibayreuth.se.campuscoffee.domain.ports.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDataService userDataService;

    @Override
    public void clear() {
        userDataService.clear();
    }

    @Override
    @NonNull
    public List<User> getAll() {
        return userDataService.getAll();
    }

    @Override
    @NonNull
    public User getById(@NonNull Long id) throws UserNotFoundException {
        return verifyUserExists(id);
    }

    @Override
    @NonNull
    public User getByLoginName(@NonNull String name) throws UserNotFoundException {
        return verifyUserExists(name);
    }

    @Override
    @NonNull
    public User upsert(@NonNull User user) throws UserNotFoundException {
        if (user.getId() == null) {
            user.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        } else {
            verifyUserExists(user.getId());
        }
        user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        return userDataService.upsert(user);
    }

    private User verifyUserExists(@NonNull Long id) throws UserNotFoundException {
        return userDataService.getById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " does not exist."));
    }

    private User verifyUserExists(@NonNull String name) throws UserNotFoundException {
        return userDataService.getByLoginName(name)
                .orElseThrow(() -> new UserNotFoundException("User with name \"" + name + "\" does not exist."));
    }
}
