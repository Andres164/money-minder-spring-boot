package com.baio.money_minder.controllers;

import com.baio.money_minder.entities.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping("/hello")
    public User sayHello() {
        return new User("ANDRES EL BAIO");
    }
}
