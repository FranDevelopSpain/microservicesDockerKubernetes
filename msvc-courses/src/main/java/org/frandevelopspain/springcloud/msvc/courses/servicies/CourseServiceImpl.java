package org.frandevelopspain.springcloud.msvc.courses.servicies;

import org.frandevelopspain.springcloud.msvc.courses.clients.UserClientRest;
import org.frandevelopspain.springcloud.msvc.courses.models.User;
import org.frandevelopspain.springcloud.msvc.courses.models.entity.Course;
import org.frandevelopspain.springcloud.msvc.courses.models.entity.UserCourse;
import org.frandevelopspain.springcloud.msvc.courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository repository;
    @Autowired
    private UserClientRest client;
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

    @Override
    @Transactional
    public void deleteUserCourseById(Long id) {
        repository.deleteUserCoursebyId(id);
    }

    @Override
    public Optional<Course> byIdWithUsers(Long id) {
        Optional<Course> o = repository.findById(id);
        if (o.isPresent()){
            Course course = o.get();
            if (!course.getUserCourses().isEmpty()){
                List<Long> ids = course.getUserCourses().stream()
                        .map(UserCourse::getUserId).toList();

                List<User> users = client.getUsersbyCourse(ids);
                course.setUsers(users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> assignUser(User user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if(o.isPresent()){
            User userMsvc = client.detail(user.getId());

            Course course = o.get();
            UserCourse userCourse = new UserCourse();
            userCourse.setUserId(userMsvc.getId());

            course.addUserCourses(userCourse);
            repository.save(course);
            return Optional.of(userMsvc);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if(o.isPresent()){
            User userMsvc = client.create(user);

            Course course = o.get();
            UserCourse userCourse = new UserCourse();
            userCourse.setUserId(userMsvc.getId());

            course.addUserCourses(userCourse);
            repository.save(course);
            return Optional.of(userMsvc);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> deleteUser(User user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if(o.isPresent()){
            User userMsvc = client.detail(user.getId());

            Course course = o.get();
            UserCourse userCourse = new UserCourse();
            userCourse.setUserId(userMsvc.getId());

            course.removeUserCourses(userCourse);
            repository.save(course);
            return Optional.of(userMsvc);
        }

        return Optional.empty();
    }
}
