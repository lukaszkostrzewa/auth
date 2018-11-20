package com.lukaszkostrzewa.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Lukasz Kostrzewa (SG0221165)
 * @since Nov 17, 2018
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByLogin(String login);

    Optional<User> findFirstByLoginAndPassword(String login, String password);
}