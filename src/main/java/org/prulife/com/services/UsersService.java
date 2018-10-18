package org.prulife.com.services;

import org.prulife.com.entities.Users;
import org.prulife.com.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public Users findById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new RuntimeException("Can't find user id: " + id));
    }

    public void createDemoUsers() {
        if(usersRepository.count()<1){
            usersRepository.save(createUser("admin", "Admin", "Admin", "0", "ADMIN"));

            usersRepository.save(createUser("kenster", "Kenster", "Crucillo", "1", "CSA"));
            usersRepository.save(createUser("mhackyu", "Mark", "Paderes", "2", "CSA"));
            usersRepository.save(createUser("jerome", "Jerome", "Patiga", "3", "PROCESSOR"));
            usersRepository.save(createUser("band", "Ban", "Daggao", "4", "PROCESSOR"));
            usersRepository.save(createUser("michaely", "Michael", "Yap", "5", "PROCESSOR"));
            usersRepository.save(createUser("socpan", "Soc", "Pandaraoan", "6", "CSA"));

            usersRepository.save(createUser("csa1", "CSA1", "TEST", "7", "CSA"));
            usersRepository.save(createUser("csa2", "CSA2", "TEST", "8", "CSA"));
            usersRepository.save(createUser("processor1", "PROCESSOR1", "TEST", "9", "PROCESSOR"));
            usersRepository.save(createUser("processor2", "PROCESSOR2", "TEST", "10", "PROCESSOR"));
        }
    }

    private Users createUser(String username, String fn, String ln, String ldapid, String role){
        Users user = new Users();
        user.setFirstName(fn);
        user.setLastName(ln);
        user.setLdapId(ldapid);
        user.setStatus(1);
        user.setUsername(username);
        user.setRole(role);
        return user;
    }
}
