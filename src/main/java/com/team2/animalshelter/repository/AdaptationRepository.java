package com.team2.animalshelter.repository;

import com.team2.animalshelter.entity.Adaptation;
import com.team2.animalshelter.entity.Report;
import com.team2.animalshelter.entity.enums.AdaptationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdaptationRepository extends JpaRepository<Adaptation, Long> {

    @Query("""
            select ad
            from Adaptation ad
                join fetch ad.animal a
                join fetch ad.owner o
            where ad.problem = true
            """)
    List<Adaptation> findAllWithProblem();

    List<Adaptation> findAllByAdaptationStatus(AdaptationStatus status);

    @Query("""
            select r
            from Report r
                join fetch r.adaptation a
            where a.owner.telegramId = :ownerId
            order by r.date desc
            """)
    List<Report> findLastReportDate(@Param("ownerId") Long ownerId);

}
