package com.finance.backend.dashboard.service;

import com.finance.backend.dashboard.model.FinancialRecord;
import com.finance.backend.dashboard.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordRepository repository;

    //create
    public FinancialRecord create(FinancialRecord record){
        validate(record);
        return repository.save(record);
    }

    //read
    public List<FinancialRecord> getAll(){
        return repository.findAll();
    }

    //update
    public FinancialRecord update(Long id , FinancialRecord newRecord){
        FinancialRecord existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Record Not Found"));

        existing.setAmount(newRecord.getAmount());
        existing.setType(newRecord.getType());
        existing.setCategory(newRecord.getCategory());
        existing.setDate(newRecord.getDate());
        existing.setNotes(newRecord.getNotes());

        return repository.save(existing);
    }

    public void delete(Long id){
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record Not Found");
        }
        repository.deleteById(id);
    }

    public List<FinancialRecord> filter(String type , String category){
        if (type != null && category != null) {
            return repository.findByTypeAndCategory(type, category);
        }
        if (type != null) {
            return repository.findByType(type);
        }
        if (category != null) {
            return repository.findByCategory(category);
        }
        return repository.findAll();
    }

    private void validate(FinancialRecord record) {
        if (record.getAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Amount must be Positive");
        }

        if (!record.getType().equalsIgnoreCase("income") &&
                !record.getType().equalsIgnoreCase("expense")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Invalid type");
        }
    }
}