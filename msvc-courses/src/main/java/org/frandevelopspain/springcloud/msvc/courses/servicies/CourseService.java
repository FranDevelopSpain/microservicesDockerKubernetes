package org.frandevelopspain.springcloud.msvc.courses.servicies;

import org.frandevelopspain.springcloud.msvc.courses.models.User;
import org.frandevelopspain.springcloud.msvc.courses.models.entity.Course;
import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> list();
    Optional<Course>byId(Long id);
    Course save(Course course);
    void delete(Long id);
    void deleteUserCourseById(Long id);
    Optional<Course>byIdWithUsers(Long id);

    Optional<User>assignUser(User user, Long courseId);
    Optional<User>createUser(User user, Long courseId);
    Optional<User>deleteUser(User user, Long courseId);
}
