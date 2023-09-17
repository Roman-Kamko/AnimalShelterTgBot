package com.team2.animalshelter.repository;

import com.team2.animalshelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    Optional<Shelter> findShelterByUsers_telegramId(Long id);

}
