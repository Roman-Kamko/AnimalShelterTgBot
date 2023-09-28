package com.team2.animalshelter.repository;

import com.team2.animalshelter.entity.Adaptation;
import com.team2.animalshelter.entity.Report;
import com.team2.animalshelter.entity.enums.AdaptationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdaptationRepository extends JpaRepository<Adaptation, Long> {

    @Query("""
            select a
            from Adaptation a
            where a.adaptationStatus in (:statuses)
            """)
    List<Adaptation> findAllByAdaptationStatus(AdaptationStatus... statuses);

    @Query("""
            select r
            from Report r
                join fetch r.adaptation a
            where a.owner.telegramId = :ownerId
            order by r.date desc
            """)
    List<Report> findLastReportDate(@Param("ownerId") Long ownerId);

    Optional<Adaptation> findByOwner_telegramId(Long ownerId);

    @Query("""
            select a
            from Adaptation a
                join fetch Report r
            where a.endDate = r.date
            """)
    List<Adaptation> findAllWhereEndDateEqualsLastReportDate();

    @Query(value = """
            select case
                       when (select date
                             from report r
                                      join adaptation a on a.id = r.adaptation_id
                             where a.owner_id = :ownerId
                             order by date desc
                             limit 1) <= now()::date - 2
                           then true
                       else false
                       end
            """,
            nativeQuery = true)
    boolean isExpiredAdaptation (@Param("ownerId") Long ownerId);

}
