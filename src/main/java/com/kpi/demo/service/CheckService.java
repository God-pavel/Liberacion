package com.kpi.demo.service;

import com.kpi.demo.dto.CheckDTO;
import com.kpi.demo.entity.Check;
import com.kpi.demo.entity.Room;
import com.kpi.demo.entity.User;
import com.kpi.demo.repository.CheckRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CheckService {

    private final CheckRepository checkRepository;

    public CheckService(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    public void createCheck(User creator, Room room, CheckDTO checkDTO) {
        Check check = Check
                .builder()
                .checker(creator)
                .room(room)
                .dateTime(checkDTO.getDatetime())
                .build();
        checkRepository.save(check);
        log.info("Check was saved. Check id : " + check.getId());
    }

    public boolean isCheckExist(User creator, Room room, CheckDTO checkDTO) {
        return checkRepository.findAll().stream()
                .anyMatch(check -> check.getDateTime().equals(checkDTO.getDatetime())
                        && check.getRoom().getId().equals(room.getId())
                        && check.getChecker().getId().equals(creator.getId()));
    }
}
