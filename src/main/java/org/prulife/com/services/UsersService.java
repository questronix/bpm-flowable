package org.prulife.com.services;

import org.prulife.com.entities.Role;
import org.prulife.com.entities.Users;
import org.prulife.com.repository.RoleRepository;
import org.prulife.com.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Users findById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new RuntimeException("Can't find user id: " + id));
    }

    public Users findByUsername(String username){
        return usersRepository.findByUsername(username);
    };

    public void createDemoUsers() {
        if(roleRepository.count()<1) {
            roleRepository.save(createRole("CSA", "Description for CSA"));
            roleRepository.save(createRole("PROCESSOR", "Description for Processor"));
            roleRepository.save(createRole("ADMIN", "Description for Admin"));
        }

        if(usersRepository.count()<1){
            Role csa = roleRepository.findByName("CSA");
            Role processor = roleRepository.findByName("Processor");
            Role admin = roleRepository.findByName("Admin");

//            usersRepository.save(createUser("admin", "Admin", "Admin", "0", admin));
//            usersRepository.save(createUser("kenster", "Kenster", "Crucillo", "1", csa));
//            usersRepository.save(createUser("mhackyu", "Mark", "Paderes", "2", csa ));
//            usersRepository.save(createUser("jerome", "Jerome", "Patiga", "3", processor));
//            usersRepository.save(createUser("ban", "Ban", "Daggao", "4", processor));
//            usersRepository.save(createUser("michaely", "Michael", "Yap", "5", processor));
//            usersRepository.save(createUser("socpan", "Soc", "Pandaraoan", "6", csa));
//
//            usersRepository.save(createUser("csa1", "CSA1", "TEST", "7", csa));
//            usersRepository.save(createUser("csa2", "CSA2", "TEST", "8", csa));
//            usersRepository.save(createUser("processor1", "PROCESSOR1", "TEST", "9", processor));
//            usersRepository.save(createUser("processor2", "PROCESSOR2", "TEST", "10", processor));
        }
    }

    private Role createRole(String name, String description) {
        Role role = new Role();
//        role.setName(name);
        role.setDescription(description);
        return role;
    }

    private Users createUser(String username, String ldapid, Role role){
        Users user = new Users();
//        user.setFirstName(fn);
//        user.setLastName(ln);
//        user.setLdapId(ldapid);
        user.setStatus(1);
        user.setUsername(username);
        user.setRole(role);
        return user;
    }
}
