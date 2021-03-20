package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.repository.CustomerRepository;
import com.mycompany.myapp.service.dto.CustomerDTO;
import com.mycompany.myapp.service.errors.CustomerNameExistException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer save(CustomerDTO customer) {
        if (repository.findByName(customer.getName()).isPresent()) {
            throw new CustomerNameExistException(customer.getName());
        }
        Customer newCustomer = new Customer();
        newCustomer.setName(customer.getName());
        return repository.save(newCustomer);
    }
}
