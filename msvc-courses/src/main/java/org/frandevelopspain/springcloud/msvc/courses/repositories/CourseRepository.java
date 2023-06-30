package org.frandevelopspain.springcloud.msvc.courses.repositories;

import org.frandevelopspain.springcloud.msvc.courses.models.entity.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course,Long> {

    @Query("delete from UserCourse us where us.userId=1")
    void deleteUserCoursebyId(Long id);
}
