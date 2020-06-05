package com.kpi.demo.service;

import com.kpi.demo.dto.UserDTO;
import com.kpi.demo.entity.Challenge;
import com.kpi.demo.entity.Session;
import com.kpi.demo.entity.User;
import com.kpi.demo.repository.ChallengeRepository;
import com.kpi.demo.repository.SessionRepository;
import com.kpi.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Log4j2
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final ChallengeRepository challengeRepository;

    public UserService(UserRepository userRepository, SessionRepository sessionRepository, ChallengeRepository challengeRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.challengeRepository = challengeRepository;
    }


    public Session login(UserDTO userDTO) {
        User user = userRepository.findByLogin(userDTO.getLogin());
        if (user == null || !user.getPassword().equals(userDTO.getPassword())) {
            return null;//"";
        }
        return user.getSession();//.getToken();
    }
    public User getUserByToken(String token){
        return userRepository.findBySession_Token(token);
    }

    public void editUser(User user, UserDTO userdto){
        if(userdto.getPassword() != null){
            user.setPassword(userdto.getPassword());
        }
        if(userdto.getUsername() != null){
            user.setUsername(userdto.getUsername());
        }
        if(userdto.getAvatar() != null){
            user.setAvatar(userdto.getAvatar());
        }
        userRepository.save(user);
        log.info("User was edited. Username : " + user.getUsername());
    }

    public Session saveNewUser(UserDTO userdto) {
        User userFromDb = userRepository.findByLogin(userdto.getLogin());

        if (userFromDb != null) {
            log.warn("login not unique!");
            return null;//"";
        }
        Session session = Session.builder()
                .token(userdto.getUsername().hashCode()+"")
                .build();
        User user = User
                .builder()
                .username(userdto.getUsername())
                .avatar(userdto.getAvatar())
                .login(userdto.getLogin())
                .password(userdto.getPassword())
                .session(session)
                .build();

        sessionRepository.save(session);
        userRepository.save(user);
        log.info("User was saved. Username : " + user.getUsername());
        return session;//.getToken();
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
        if (challengeRepository.findByName("smoking") == null) {
            Challenge challenge = Challenge.builder()
                    .creator(userRepository.findByLogin("admin"))
                    .name("smoking")
                    .build();
            challengeRepository.save(challenge);
        }
    }
}
