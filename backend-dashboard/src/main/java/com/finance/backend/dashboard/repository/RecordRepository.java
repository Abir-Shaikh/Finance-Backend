package com.finance.backend.dashboard.repository;

import com.finance.backend.dashboard.model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<FinancialRecord , Long> {
    List<FinancialRecord> findByType(String type);
    List<FinancialRecord> findByCategory(String category);
    List<FinancialRecord> findByTypeAndCategory(String type, String category);
}