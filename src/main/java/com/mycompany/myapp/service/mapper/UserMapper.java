package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.dto.UserDTO;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Component
public class UserMapper extends EntityMapper<User, UserDTO> {

    @Override
    public UserDTO entityToDto(User entity) {
        return new UserDTO(entity);
    }

    @Override
    public User userDTOToUser(UserDTO dto) {
        if (dto == null) {
            return null;
        } else {
            User user = new User();
            user.setId(dto.getId());
            user.setLogin(dto.getLogin());
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setEmail(dto.getEmail());
            user.setImageUrl(dto.getImageUrl());
            user.setActivated(dto.isActivated());
            user.setLangKey(dto.getLangKey());
            Set<Authority> authorities = this.authoritiesFromStrings(dto.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }

    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities =
                authoritiesAsString
                    .stream()
                    .map(
                        string -> {
                            Authority auth = new Authority();
                            auth.setName(string);
                            return auth;
                        }
                    )
                    .collect(Collectors.toSet());
        }

        return authorities;
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
