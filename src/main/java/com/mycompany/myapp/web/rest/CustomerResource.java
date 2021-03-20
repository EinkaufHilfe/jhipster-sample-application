package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.service.CustomerService;
import com.mycompany.myapp.service.dto.CustomerDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Customer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerResource {
    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private static final String ENTITY_NAME = "customer";

    private final HeaderUtil headerUtil;
    private final CustomerService customerService;

    public CustomerResource(HeaderUtil headerUtil, CustomerService customerService) {
        this.headerUtil = headerUtil;
        this.customerService = customerService;
    }

    /**
     * {@code POST  /customers} : Create a new customer.
     *
     * @param customer the customer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customer, or with status {@code 400 (Bad Request)} if the customer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customer) throws URISyntaxException {
        CustomerDTO result = customerService.create(customer);
        return ResponseEntity
            .created(new URI("/api/customers/" + result.getId()))
            .headers(headerUtil.createEntityCreationAlert(true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customers} : Updates an existing customer.
     *
     * @param customer the customer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customer,
     * or with status {@code 400 (Bad Request)} if the customer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customer couldn't be updated.
     */
    @PutMapping("/customers")
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerDTO customer) {
        if (customer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Customer result = customerService.update(customer);
        return ResponseEntity.ok().headers(headerUtil.createEntityUpdateAlert(true, ENTITY_NAME, customer.getId().toString())).body(result);
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
     */
    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.findAll();
    }

    /**
     * {@code GET  /customers/:id} : get the "id" customer.
     *
     * @param id the id of the customer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
        CustomerDTO customer = customerService.findById(id);
        return ResponseEntity.ok().body(customer);
    }

    /**
     * {@code DELETE  /customers/:id} : delete the "id" customer.
     *
     * @param id the id of the customer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().headers(headerUtil.createEntityDeletionAlert(true, ENTITY_NAME, id.toString())).build();
    }
}
