package com.thuggeelya.rent.repository;

import com.thuggeelya.rent.model.Role;
import com.thuggeelya.rent.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}