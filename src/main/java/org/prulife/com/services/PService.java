package org.prulife.com.services;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.prulife.com.entities.Policy;
import org.prulife.com.entities.Users;
import org.prulife.com.repository.PolicyRepository;
import org.prulife.com.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class PService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PolicyRepository policyRepository;

    public ProcessInstance startProcess(String username) {
        //Search User
        Users user = userRepository.findByUsername(username);

        //assign it to policy
        Policy policy = new Policy();
        policy.setCreatedBy(user);
        policy.setAppNo(counter());

        //save the policy to DB
        Policy p = policyRepository.save(policy);

        //set flowable variables
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("user", user);
        variables.put("policy", p);
        variables.put("username", user.getUsername());
        variables.put("appno", policy.getAppNo());
        variables.put("group", "csa");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("startCSA", "CSA Pre-Processing", variables);
        return pi;
    }

    private String counter(){
        Policy p = policyRepository.findTopByOrderByIdDesc();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR) % 100;
        if(p != null){
            return year + String.format("%07d", (p.getId() +1));
        }else{
            return year + String.format("%07d", 1);
        }
    }

    public RuntimeService getRuntimeService(){
        return runtimeService;
    }
}
