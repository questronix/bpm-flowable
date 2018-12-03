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

//    public Users findById(Long id) {
//        return usersRepository.findById(id).orElseThrow(() -> new RuntimeException("Can't find user id: " + id));
//    }

    public Users findByUsername(String username){
        return usersRepository.findByUsername(username);
    };
}
