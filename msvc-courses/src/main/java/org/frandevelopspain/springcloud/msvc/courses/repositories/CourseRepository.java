package org.frandevelopspain.springcloud.msvc.courses.repositories;

import org.frandevelopspain.springcloud.msvc.courses.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course,Long> {
}
