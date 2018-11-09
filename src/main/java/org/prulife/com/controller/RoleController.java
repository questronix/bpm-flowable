package org.prulife.com.controller;

import javassist.NotFoundException;
import org.prulife.com.entities.Role;
import org.prulife.com.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Role> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Role getRoleById(@PathVariable("id") Long id) {
        return roleService.findById(id).orElse(null);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Role updateRoleById(@PathVariable("id") Long id, @RequestBody Map<String, Object> body) throws ParseException{
        Role role = roleService.findById(id).orElse(null);
//        role.setName((String) body.get("name"));
        role.setDescription((String) body.get("description"));

        return roleService.save(role);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Role create(@RequestBody Map<String, Object> body) throws ParseException{
        Role role = new Role();
//        role.setName((String) body.get("name"));
        role.setDescription((String) body.get("description"));

        return roleService.save(role);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    void deletByRoleId(@PathVariable("id") Long id) throws ParseException{
        roleService.deleteById(id);
    }

}
