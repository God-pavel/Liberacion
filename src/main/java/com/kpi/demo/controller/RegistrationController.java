package com.kpi.demo.controller;

import com.kpi.demo.dto.UserDTO;
import com.kpi.demo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/")
public class RegistrationController {

    @Autowired
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public long saveUser(@RequestBody UserDTO user) {
        return userService.saveNewUser(user);
    }

    @GetMapping("login")
    public long login(@RequestBody UserDTO user) {
        return userService.login(user);
    }
}
