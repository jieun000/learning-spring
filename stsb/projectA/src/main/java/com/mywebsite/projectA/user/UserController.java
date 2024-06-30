package com.mywebsite.projectA.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<SiteUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public SiteUser createUser(@RequestBody SiteUser user) {
        return userService.saveUser(user);
    }
    
}
