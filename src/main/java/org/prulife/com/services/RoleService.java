package org.prulife.com.services;

import org.prulife.com.entities.Role;
import org.prulife.com.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}
