package com.team2.animalshelter.repository;

import com.team2.animalshelter.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
