package com.team2.animalshelter.repository;

import com.team2.animalshelter.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
