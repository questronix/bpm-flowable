package org.prulife.com.repository;

import org.prulife.com.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String userName);
}
