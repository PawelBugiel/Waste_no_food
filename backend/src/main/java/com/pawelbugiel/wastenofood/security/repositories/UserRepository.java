package com.pawelbugiel.wastenofood.security.repositories;

import com.pawelbugiel.wastenofood.security.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles",
            countQuery = "SELECT count(u) FROM User u")
    Page<User> findAllWithRoles(Pageable pageable);

    @Query(value = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :partialEmail, '%'))",
            countQuery = "SELECT count(u) FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :partialEmail, '%'))")
    Page<User> findUserByPartialEmailWithRoles(Pageable pageable, @Param("partialEmail") String partialEmail);
}