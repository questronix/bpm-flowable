package org.prulife.com.services;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.prulife.com.entities.Policy;
import org.prulife.com.entities.TaskObject;
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

    public TaskObject startProcess(String username, String info, String appNo) {
        //Search User
        Users user = userRepository.findByUsername(username);

        //assign it to policy
        Policy policy = new Policy();
        policy.setCreatedBy(user.getId());
        policy.setTransactionNo(appNo);
        policy.setInfo(info);
        policy.setStatus("draft");

        //save the policy to DB
        Policy p = policyRepository.save(policy);

        //set flowable variables
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("user", user);
        variables.put("policy", p);
        variables.put("userid", user.getId());
        variables.put("transactionNumber", policy.getTransactionNo());
        variables.put("group", "csa");
        variables.put("status", "draft");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("startReinstatement", "Reinstatement", variables);
        Task to = taskService.createTaskQuery().processInstanceId(pi.getId()).taskAssignee(user.getId()+"").orderByTaskCreateTime().desc().singleResult();
        to.setOwner(user.getUsername());
        to.setCategory("csa");
        taskService.saveTask(to);
        to = taskService.createTaskQuery().processInstanceId(pi.getId()).taskAssignee(user.getId()+"").orderByTaskCreateTime().desc().singleResult();
        return new TaskObject(to, runtimeService);
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
