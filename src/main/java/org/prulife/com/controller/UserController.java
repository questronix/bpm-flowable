package org.prulife.com.controller;

import org.prulife.com.entities.Users;
import org.prulife.com.services.DummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
public class UserController {
    @Autowired
    private DummyService myService;

    @RequestMapping(value="/process", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Users getUsers(@RequestBody UserBody userBody) {
        Users user = myService.getUser(userBody.test);
        return user;
    }

    static class UserBody {
        public String test;

    }

}
