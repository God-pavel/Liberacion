package com.kpi.demo.service;

import com.kpi.demo.dto.CheckDTO;
import com.kpi.demo.entity.Check;
import com.kpi.demo.entity.Room;
import com.kpi.demo.entity.User;
import com.kpi.demo.repository.CheckRepository;
import com.kpi.demo.repository.RoomRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CheckService {

    private final CheckRepository checkRepository;
    private final RoomRepository roomRepository;

    public CheckService(CheckRepository checkRepository, RoomRepository roomRepository) {
        this.checkRepository = checkRepository;
        this.roomRepository = roomRepository;
    }

    public Check createCheck(User creator, Room room, CheckDTO checkDTO) {
        Check check = Check
                .builder()
                .checker(creator)
                .dateTime(checkDTO.getDatetime())
                .build();
        checkRepository.save(check);
        room.getChecks().add(check);
        roomRepository.save(room);
        log.info("Check was saved. Check id : " + check.getId());
        return check;
    }

    public boolean isCheckExist(User creator, Room room, CheckDTO checkDTO) {
        return checkRepository.findAll().stream()
                .anyMatch(check -> check.getDateTime().equals(checkDTO.getDatetime())
                        && check.getChecker().getId().equals(creator.getId()));
    }
}
