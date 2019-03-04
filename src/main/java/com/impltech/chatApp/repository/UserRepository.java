package com.impltech.chatApp.repository;

import com.impltech.chatApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(final String email);

    Optional<User> findById(final Long userId);

    Optional<User> findByUsername(final String username);

    Boolean existsByUsername(final String username);

    Boolean existsByEmail(final String email);

}
