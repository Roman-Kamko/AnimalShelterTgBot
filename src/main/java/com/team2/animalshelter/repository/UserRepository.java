package com.team2.animalshelter.repository;

import com.team2.animalshelter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
