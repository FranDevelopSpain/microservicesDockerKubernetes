package org.frandevelopspain.springcloud.msvc.users.services;

import org.frandevelopspain.springcloud.msvc.users.clients.CourseClientRest;
import org.frandevelopspain.springcloud.msvc.users.models.entity.User;
import org.frandevelopspain.springcloud.msvc.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;
    @Autowired
    private CourseClientRest clientRest;
    @Override
    @Transactional(readOnly = true)
    public List<User> list() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> byId(Long id) {
        return repository.findById(id);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
        clientRest.deleteUserCourseById(id);
    }

    @Override
    public List<User> findByIds(Iterable<Long> ids) {
        return (List<User>) repository.findAllById(ids);
    }

    @Override
    public Optional<User> byEmail(String email) {
        return repository.findbyEmail(email);
    }
}
