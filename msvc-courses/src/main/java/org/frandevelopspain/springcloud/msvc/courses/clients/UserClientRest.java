package org.frandevelopspain.springcloud.msvc.courses.clients;
import org.frandevelopspain.springcloud.msvc.courses.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-users", url = "localhost:8001")
public interface UserClientRest {

    @GetMapping("/{id}")
    User detail(@PathVariable Long id);

    @PostMapping("/")
    User create(@RequestBody User user);

    @GetMapping("/course-users")
    List<User> getUsersbyCourse(@RequestParam Iterable<Long> ids);

}
