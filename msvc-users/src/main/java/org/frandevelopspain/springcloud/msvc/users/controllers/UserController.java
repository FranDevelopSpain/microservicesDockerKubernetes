package org.frandevelopspain.springcloud.msvc.users.controllers;

import jakarta.validation.Valid;
import org.frandevelopspain.springcloud.msvc.users.models.entity.User;
import org.frandevelopspain.springcloud.msvc.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User>list(){
        return service.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id){
        Optional<User> oUser = service.byId(id);
        if (oUser.isPresent()){
            return ResponseEntity.ok().body(oUser.get());
        }

        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?>create(@Valid @RequestBody User user, BindingResult result){
        if (!user.getEmail().isEmpty() && service.byEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "The user already exists with this email"));
        }

        if(result.hasErrors()){
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>update(@Valid @RequestBody User user,BindingResult result,
                                   @PathVariable Long id){
        if(result.hasErrors()){
            return validate(result);
        }

        Optional<User> o = service.byId(id);
        if (o.isPresent()){
            User userDb = o.get();
            if (!user.getEmail().isEmpty() &&
                    !user.getEmail().equalsIgnoreCase(userDb.getEmail())
                    && service.byEmail(user.getEmail()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "The user already exists with this email"));
            }

            userDb.setName(user.getName());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDb));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable Long id){
        Optional<User> o = service.byId(id);

        if (o.isPresent()){
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/course-users")
    public ResponseEntity<?> getUsersbyCourse(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.findByIds(ids));
    }

    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(), "The field" + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
