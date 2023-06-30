package org.frandevelopspain.springcloud.msvc.courses.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.frandevelopspain.springcloud.msvc.courses.models.User;
import org.frandevelopspain.springcloud.msvc.courses.models.entity.Course;
import org.frandevelopspain.springcloud.msvc.courses.servicies.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping
    public ResponseEntity<List<Course>> list(){
    return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>detail(@PathVariable Long id){
        Optional<Course>o = service.byIdWithUsers(id);
        if (o.isPresent()){
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?>create(@Valid @RequestBody Course course, BindingResult result){
        if(result.hasErrors()){
            return validate(result);
        }
        Course courseDb = service.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validate(result);
        }

        Optional<Course> o = service.byId(id);
        if(o.isPresent()){
            Course courseDb = o.get();
            courseDb.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Course> o = service.byId(id);
        if (o.isPresent()){
            service.delete(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable Long courseId){
        Optional<User> o;
        try {
          o =  service.assignUser(user,courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(Collections.singletonMap("message", "No user by id in the communication"
                            + e.getMessage()));
        }
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long courseId){
        Optional<User> o;
        try {
            o =  service.createUser(user,courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(Collections.singletonMap("message", "The user could not be created"
                            + e.getMessage()));
        }
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{courseId}")
    public ResponseEntity<?> deleteUser(@RequestBody User user, @PathVariable Long courseId){
        Optional<User> o;
        try {
            o =  service.deleteUser(user,courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(Collections.singletonMap("message", "The user could not be deleted"
                            + e.getMessage()));
        }
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user-course/{id}")
    public ResponseEntity<?> deleteUserCourseById(@PathVariable Long id){
        service.deleteUserCourseById(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(), "The field" + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
