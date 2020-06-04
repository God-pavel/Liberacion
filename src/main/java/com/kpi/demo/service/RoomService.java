package com.kpi.demo.service;

import com.kpi.demo.dto.RoomDTO;
import com.kpi.demo.entity.Room;
import com.kpi.demo.entity.User;
import com.kpi.demo.repository.ChallengeRepository;
import com.kpi.demo.repository.RoomRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Log4j2
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final ChallengeRepository challengeRepository;

    public RoomService(RoomRepository roomRepository, ChallengeRepository challengeRepository) {
        this.roomRepository = roomRepository;
        this.challengeRepository = challengeRepository;
    }

    public List<Room> getAllRoomsForUser(User user) {
        return roomRepository.findByParticipantsContains(user);
    }
    public boolean isUserInRoom(User user, Room targetRoom){
        return roomRepository.findByParticipantsContains(user).stream()
                .anyMatch(room -> room.getId().equals(targetRoom.getId()));
    }

    public void addUserToRoom(User user, long roomId){
        Room room = roomRepository.findById(roomId);
        room.getParticipants().add(user);
        roomRepository.save(room);
    }

    public long createRoom(User creator, RoomDTO roomDTO) {
        Room roomFromDb = roomRepository.findByName(roomDTO.getName());

        if (roomFromDb != null) {
            log.warn("login not unique!");
            return 0;
        }

        Room room = Room
                .builder()
                .name(roomDTO.getName())
                .avatar(roomDTO.getAvatar())
                .challenge(challengeRepository.findById(roomDTO.getChallenge()))
                .expireDate(roomDTO.getEnd())
                .creator(creator)
                .participants(new HashSet<>())
                .build();
        room.getParticipants().add(creator);
        roomRepository.save(room);
        log.info("Room was saved. Room name : " + room.getName());
        return room.getId();
    }

    public Room getRoomById(long id) {
        return roomRepository.findById(id);
    }

}
