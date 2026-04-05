package com.finance.backend.dashboard.service;

import com.finance.backend.dashboard.model.FinancialRecord;
import com.finance.backend.dashboard.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private RecordRepository repository;

    public Map<String, Object> getSummary() {
        List<FinancialRecord> records = repository.findAll();

        double income = records.stream()
                .filter(r -> r.getType().equalsIgnoreCase("income"))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double expense = records.stream()
                .filter(r -> r.getType().equalsIgnoreCase("expense"))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        Map<String, Double> categoryTotals = records.stream()
                .collect(Collectors.groupingBy(
                        FinancialRecord::getCategory, Collectors.summingDouble(FinancialRecord::getAmount)
                ));


        Map<String, Object> result = new HashMap<>();
        result.put("totalIncome", income);
        result.put("totalExpense" , expense);
        result.put("balance" , income - expense);
        result.put("CategoryTotals" , categoryTotals);

        return result;
    }
}