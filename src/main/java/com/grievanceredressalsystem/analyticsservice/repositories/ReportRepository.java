package com.grievanceredressalsystem.analyticsservice.repositories;

import com.grievanceredressalsystem.analyticsservice.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepo extends JpaRepository<Report, UUID> {
}
