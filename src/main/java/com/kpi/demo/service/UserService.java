package com.kpi.demo.service;

import com.kpi.demo.dto.UserDTO;
import com.kpi.demo.entity.User;
import com.kpi.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Log4j2
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean login(UserDTO userDTO) {
        User user = userRepository.findByLogin(userDTO.getLogin());
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(userDTO.getPassword());
    }

    public boolean saveNewUser(UserDTO userdto) {
        User userFromDb = userRepository.findByLogin(userdto.getUsername());

        if (userFromDb != null) {
            log.warn("login not unique!");
            return false;
        }
        User user = User
                .builder()
                .username(userdto.getUsername())
                .login(userdto.getLogin())
                .password(userdto.getPassword())
                .build();
        userRepository.save(user);
        log.info("User was saved. Username : " + user.getUsername());
        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @PostConstruct
    public void add() {
        if (userRepository.findByLogin("admin") == null) {
            User user = User.builder()
                    .login("admin")
                    .username("admin")
                    .password("test")
                    .build();
            userRepository.save(user);
        }

    }
}
