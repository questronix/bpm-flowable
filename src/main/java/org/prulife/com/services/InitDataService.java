package org.prulife.com.services;

import org.apache.catalina.User;
import org.prulife.com.entities.Role;
import org.prulife.com.entities.Users;
import org.prulife.com.repository.RoleRepository;
import org.prulife.com.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InitDataService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersRepository usersRepository;

    public void init() {
        if (roleRepository.count() < 1) {
            System.out.println("Creating roles...");
            initRoles();
        }
        if (usersRepository.count() < 1) {
            System.out.println("Creating users...");
            initUsers();
        }
        System.out.println("--CREATION STATUS--");
        System.out.println(roleRepository.count() + " roles created");
        System.out.println(usersRepository.count() + " users created");
    }

    private void initRoles() {
        roleRepository.save(this.createRole("1", "CSA"));
        roleRepository.save(this.createRole("2", "PROCESSOR"));
    }

    private Role createRole(String roleId, String description) {
        Role role = new Role();
        role.setRoleId(roleId);
        role.setDescription(description);
        return role;
    }

    private void initUsers() {
        Role csa = roleRepository.findByDescription("CSA");
        Role processor = roleRepository.findByDescription("PROCESSOR");

        usersRepository.save(this.createUser("mhackyu", csa, 1, "LA1"));
        usersRepository.save(this.createUser("c68431", csa, 1, "LA2"));
        usersRepository.save(this.createUser("jerome", processor, 1, "LA3"));
    }

    private Users createUser(String username, Role role, int isActive, String LaUserId) {
        Users user = new Users();
        user.setUsername(username);
        user.setRole(role);
        user.setIsActive(1);
        user.setLaUserId(LaUserId);
        return user;
    }
}
