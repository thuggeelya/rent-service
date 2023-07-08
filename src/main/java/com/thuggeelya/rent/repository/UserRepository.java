package com.thuggeelya.rent.repository;

import com.thuggeelya.rent.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    @Query("select (count(a) > 0) from AppUser a where a.username = ?1")
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}