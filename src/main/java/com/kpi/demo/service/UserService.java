package com.kpi.demo.service;

import com.kpi.demo.dto.UserDTO;
import com.kpi.demo.entity.Session;
import com.kpi.demo.entity.User;
import com.kpi.demo.repository.SessionRepository;
import com.kpi.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Log4j2
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public UserService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }


    public long login(UserDTO userDTO) {
        User user = userRepository.findByLogin(userDTO.getLogin());
        if (user == null || !user.getPassword().equals(userDTO.getPassword())) {
            return 0;
        }
        return user.getSession().getToken();
    }

    public long saveNewUser(UserDTO userdto) {
        User userFromDb = userRepository.findByLogin(userdto.getLogin());

        if (userFromDb != null) {
            log.warn("login not unique!");
            return 0;
        }
        Session session = Session.builder()
                .token(userdto.getUsername().hashCode())
                .build();
        User user = User
                .builder()
                .username(userdto.getUsername())
                .login(userdto.getLogin())
                .password(userdto.getPassword())
                .session(session)
                .build();

        sessionRepository.save(session);
        userRepository.save(user);
        log.info("User was saved. Username : " + user.getUsername());
        return session.getToken();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @PostConstruct
    public void add() {
        if (userRepository.findByLogin("admin") == null) {
            UserDTO user = UserDTO.builder()
                    .login("admin")
                    .username("admin")
                    .password("test")
                    .build();
            saveNewUser(user);
        }

    }
}
