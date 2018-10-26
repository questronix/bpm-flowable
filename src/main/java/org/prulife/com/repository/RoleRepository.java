package org.prulife.com.repository;

import org.prulife.com.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
