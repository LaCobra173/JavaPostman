package ru.example;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/{id}")
    public String getMap(@RequestParam("name") String name,
                         @RequestParam("year") int year /*@PathVariable Integer id*/) {

        return "User with id " + name + " " + year;
    }

}
