package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.dto.CustomerDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Component
public class CustomerMapper extends EntityMapper<Customer, CustomerDTO> {

    @Override
    public CustomerDTO entityToDto(Customer entity) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(entity.getId());
        customerDTO.setLastModifiedBy(entity.getLastModifiedBy());
        customerDTO.setCreatedBy(entity.getCreatedBy());
        customerDTO.setCreatedDate(entity.getCreatedDate());
        customerDTO.setLastModifiedDate(entity.getLastModifiedDate());
        customerDTO.setName(entity.getName());
        return customerDTO;
    }

    @Override
    public Customer userDTOToUser(CustomerDTO dto) {
        if (dto == null) {
            return null;
        } else {
            Customer user = new Customer();
            user.setId(dto.getId());
            user.setName(dto.getName());
            return user;
        }
    }
}
