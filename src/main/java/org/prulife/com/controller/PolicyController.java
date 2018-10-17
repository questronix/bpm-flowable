package org.prulife.com.controller;

import org.prulife.com.entities.Policy;
import org.prulife.com.entities.Users;
import org.prulife.com.services.PolicyService;
import org.prulife.com.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
public class PolicyController {
    @Autowired
    private PolicyService policyService;

    @Autowired
    private UsersService usersService;

    @RequestMapping(value="/policy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Policy> index() {
        return policyService.getAll();
    }

    @RequestMapping(value="/policy/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Policy findById(@PathVariable("id") Long id) {
        return policyService.findById(id);
    }

    @RequestMapping(value="/policy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Policy save(@RequestBody PolicyBody policyBody) {
        Users user = usersService.findById(policyBody.createdBy);
        Policy policy = new Policy();
        policy.setCreatedBy(user.getId());
        policy.setNumber(policyBody.number);
        policy.setInfo(policyBody.info);

        return policyService.save(policy);
    }

    static class PolicyBody {
        private String number;
        private String appNo;
        private Boolean status;
        private String info;
        public Long createdBy;
    }
}
