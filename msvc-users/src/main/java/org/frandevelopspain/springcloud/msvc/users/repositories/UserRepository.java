package org.frandevelopspain.springcloud.msvc.users.repositories;

import org.frandevelopspain.springcloud.msvc.users.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
