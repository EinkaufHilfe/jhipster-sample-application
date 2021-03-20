package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.repository.CustomerRepository;
import com.mycompany.myapp.service.dto.CustomerDTO;
import com.mycompany.myapp.service.errors.CustomerNameExistException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer create(CustomerDTO customer) {
        if (repository.findByName(customer.getName()).isPresent()) {
            throw new CustomerNameExistException(customer.getName());
        }
        Customer newCustomer = new Customer();
        newCustomer.setName(customer.getName());
        return repository.save(newCustomer);
    }

    public Customer update(CustomerDTO customer) {
        return repository.save(new Customer());
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }
}
