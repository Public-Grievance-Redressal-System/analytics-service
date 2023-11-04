package com.grievanceredressalsystem.analyticsservice.repositories;

import com.grievanceredressalsystem.analyticsservice.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
}
