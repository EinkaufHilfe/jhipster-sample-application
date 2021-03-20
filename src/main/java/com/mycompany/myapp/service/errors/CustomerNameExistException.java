package com.mycompany.myapp.service.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerNameExistException extends RuntimeException {
    private final Logger log = LoggerFactory.getLogger(CustomerNameExistException.class);

    public CustomerNameExistException(String name) {
        log.debug("Cannot create customer with name {}; already exists", name);
    }
}
