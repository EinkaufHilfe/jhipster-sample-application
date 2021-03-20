package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.User;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link User} and its DTO called {@link T}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Component
abstract class EntityMapper<R, T> {

    public List<T> entitiesToDTOs(List<R> entities) {
        return entities.stream().filter(Objects::nonNull).map(this::entityToDto).collect(Collectors.toList());
    }

    public abstract T entityToDto(R entity);

    public List<R> userDTOsToUsers(List<T> dtos) {
        return dtos.stream().filter(Objects::nonNull).map(this::userDTOToUser).collect(Collectors.toList());
    }

    public abstract R userDTOToUser(T dto);
}
