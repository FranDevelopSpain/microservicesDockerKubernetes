package org.frandevelopspain.springcloud.msvc.courses.servicies;

import org.frandevelopspain.springcloud.msvc.courses.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> list();
    Optional<Course>byId(Long id);
    Course save(Course course);
    void delete(Long id);
}
