package com.team2.animalshelter.repository;

import com.team2.animalshelter.entity.Animal;
import com.team2.animalshelter.entity.enums.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("""
            select a
            from Animal a
                join fetch a.adaptations ad
            where a.animalType = :type
                and ad.size = 0
            """)
    List<Animal> findAllWithoutOwner(@Param("type") AnimalType type);

    @Query("""
            select a
            from Animal a
                join fetch a.adaptations ad
            where a.animalType = :type
                and ad.size > 0
            """)
    List<Animal> findAllAdopted(@Param("type") AnimalType type);

}
