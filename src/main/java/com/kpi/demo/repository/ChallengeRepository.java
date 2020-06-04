package com.kpi.demo.repository;

import com.kpi.demo.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Challenge findByName(String name);
    Challenge findById(long id);
}
