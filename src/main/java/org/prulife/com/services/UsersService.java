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

//    public Users create(String username) {
//        Users user = new Users();
//        user.setUsername(username);
//        user.setLdapId("1");
//        user.setRole("CSA");
//        user.setFirstName("Sample");
//        user.setLastName("Last name");
//        usersRepository.save(user);
//        return null;
//    }
}
