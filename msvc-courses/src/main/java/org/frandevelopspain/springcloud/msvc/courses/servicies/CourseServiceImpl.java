package org.frandevelopspain.springcloud.msvc.courses.servicies;

import org.frandevelopspain.springcloud.msvc.courses.entity.Course;
import org.frandevelopspain.springcloud.msvc.courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository repository;
    @Override
    @Transactional(readOnly = true)
    public List<Course> list() {
        return (List<Course>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> byId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Course save(Course course) {
        return repository.save(course);
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
