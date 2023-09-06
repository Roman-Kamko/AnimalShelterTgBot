package com.team2.animalshelter.repository;

import com.team2.animalshelter.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
