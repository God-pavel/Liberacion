package com.kpi.demo.controller;

import com.kpi.demo.dto.UserDTO;
import com.kpi.demo.entity.User;
import com.kpi.demo.entity.Session;
import com.kpi.demo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public Session saveUser(@RequestBody UserDTO user) {
        final Session token = userService.saveNewUser(user);
        if (StringUtils.isEmpty(token)) {
            throw new AlreadyRegisteredException();
        } else return token;
    }

    @PostMapping("login")
    public Session login(@RequestBody UserDTO user) {
        final Session token = userService.login(user);
        if (StringUtils.isEmpty(token)) {
            throw new UnauthorizedException();
        } else return token;
    }

    @PostMapping("user")
    public void edit(@RequestBody UserDTO userDTO, @RequestHeader("authorization") String bearer) {
        User user = getAuthorizedUserByHeader(bearer);
        userService.editUser(user, userDTO);
    }
    @GetMapping("user")
    public User getUserInfo(/*@RequestBody UserDTO userDTO,*/ @RequestHeader("authorization") String bearer) {
        return getAuthorizedUserByHeader(bearer);
    }

    private User getAuthorizedUserByHeader(String bearer) {
        String token = bearer.substring(bearer.indexOf(" ") + 1);
        User user = userService.getUserByToken(token);
        if (StringUtils.isEmpty(token) || user == null) {
            throw new UserController.UnauthorizedException();
        }
        return user;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class UnauthorizedException extends RuntimeException {
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class AlreadyRegisteredException extends RuntimeException {
    }
}
