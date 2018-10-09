package org.prulife.com.repository;

import org.prulife.com.entities.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
