package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Deduction;
import com.mycompany.myapp.repository.DeductionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DeductionService {
    private final DeductionRepository repository;

    public DeductionService(DeductionRepository repository) {
        this.repository = repository;
    }

    public Deduction save(Deduction deduction) {
        return repository.save(deduction);
    }

    public List<Deduction> findAll() {
        return repository.findAll();
    }

    public Optional<Deduction> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
