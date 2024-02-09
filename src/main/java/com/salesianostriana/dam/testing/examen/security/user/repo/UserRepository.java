package com.salesianostriana.dam.testing.examen.security.user.repo;

import com.salesianostriana.dam.testing.examen.security.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsernameIgnoreCase(String username);

    Optional<User> findFirstByUsername(String username);

}
