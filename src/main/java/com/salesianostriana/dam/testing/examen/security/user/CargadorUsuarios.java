package com.salesianostriana.dam.testing.examen.security.user;

import com.salesianostriana.dam.testing.examen.security.user.model.User;
import com.salesianostriana.dam.testing.examen.security.user.model.UserRole;
import com.salesianostriana.dam.testing.examen.security.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CargadorUsuarios {

    private final UserRepository userRepository;

    public void cargarUsuarios() {

        List<User> usuarios = List.of(User.builder()
                .username("user")
                .password("{noop}1234")
                .roles(Set.of(UserRole.USER))
                .build(),
                User.builder()
                        .username("admin")
                        .password("{noop}admin")
                        .roles(Set.of(UserRole.ADMIN))
                        .build());

        userRepository.saveAll(usuarios);

    }


}
