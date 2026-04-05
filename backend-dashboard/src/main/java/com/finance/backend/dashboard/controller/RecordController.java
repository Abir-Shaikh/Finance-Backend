package com.finance.backend.dashboard.controller;

import com.finance.backend.dashboard.model.FinancialRecord;
import com.finance.backend.dashboard.service.RecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public FinancialRecord create(@Valid @RequestBody FinancialRecord record){
        return recordService.create(record);
    }

    @GetMapping
    public List<FinancialRecord> getAll(){
        return recordService.getAll();
    }

    @GetMapping("/filter")
    public List<FinancialRecord> filter(
            @RequestParam(required = false)String type,
            @RequestParam(required = false) String category){
        return recordService.filter(type,category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        recordService.delete(id);
    }
}