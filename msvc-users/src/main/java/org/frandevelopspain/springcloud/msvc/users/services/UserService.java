package org.frandevelopspain.springcloud.msvc.users.services;

import org.frandevelopspain.springcloud.msvc.users.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User>list();
    Optional<User>byId(Long id);
    User save(User user);
    void delete(Long id);

    Optional<User> byEmail(String email);
}
