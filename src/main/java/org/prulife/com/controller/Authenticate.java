package org.prulife.com.controller;

import org.prulife.com.entities.Users;
import org.prulife.com.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Authenticate {

    @Autowired
    private UsersService usersService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Users login(@RequestBody Map<String, String> body){
        return usersService.findByUsername(body.get("username").toString().toLowerCase());
    }
}
