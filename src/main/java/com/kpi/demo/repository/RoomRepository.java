package com.kpi.demo.repository;

import com.kpi.demo.entity.Room;
import com.kpi.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findById(long id);
    Room findByName(String name);
    List<Room> findByParticipantsContains(User user);
}
