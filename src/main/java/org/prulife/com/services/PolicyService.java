package org.prulife.com.services;

import org.prulife.com.entities.Policy;
import org.prulife.com.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public List<Policy> getAll() {
        return policyRepository.findAll();
    }

    public Policy findById(Long id) {
        return policyRepository.findById(id).orElseThrow(() -> new RuntimeException("Can't find Policy with id: " + id));
    }

    public Policy save(Policy policy) {
        return policyRepository.save(policy);
    }
}
