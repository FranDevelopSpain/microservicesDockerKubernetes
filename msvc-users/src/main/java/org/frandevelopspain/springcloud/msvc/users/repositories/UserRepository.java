package org.frandevelopspain.springcloud.msvc.users.repositories;

import org.frandevelopspain.springcloud.msvc.users.models.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findbyEmail(String email);


}
