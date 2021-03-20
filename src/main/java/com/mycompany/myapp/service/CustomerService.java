package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.repository.CustomerRepository;
import com.mycompany.myapp.service.dto.CustomerDTO;
import com.mycompany.myapp.service.errors.CustomerNameExistException;
import com.mycompany.myapp.service.mapper.CustomerMapper;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private CustomerMapper mapper;

    public CustomerService(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CustomerDTO create(CustomerDTO customer) {
        if (repository.findByName(customer.getName()).isPresent()) {
            throw new CustomerNameExistException(customer.getName());
        }
        Customer newCustomer = new Customer();
        newCustomer.setName(customer.getName());
        return mapper.entityToDto(repository.save(newCustomer));
    }

    public Customer update(CustomerDTO customer) {
        return repository.save(new Customer());
    }

    public CustomerDTO findById(Long id) {
        return repository.findById(id).map(mapper::entityToDto).orElseThrow(() -> new EntityNotFoundException());
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<CustomerDTO> findAll() {
        List<Customer> customers = repository.findAll();
        return mapper.entitiesToDTOs(customers);
    }
}
