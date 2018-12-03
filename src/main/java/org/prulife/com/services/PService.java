package org.prulife.com.services;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.prulife.com.entities.TaskObject;
import org.prulife.com.entities.Users;
import org.prulife.com.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public TaskObject startProcess(String userId, String username, String transactionNo, String policyNo) {
        //Search User
//        Users user = userRepository.findByUsername(username);
//        System.out.println(user);

        //set flowable variables
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("transactionNo", transactionNo);
        variables.put("policyNo", policyNo);
        variables.put("modules", "csa");
        variables.put("status", "draft");
        variables.put("username", username);
        variables.put("userid", userId);
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("startReinstatement", "Reinstatement", variables);
        Task to = taskService.createTaskQuery().processInstanceId(pi.getId()).taskAssignee(userId).orderByTaskCreateTime().desc().singleResult();
        to.setOwner(userId);
        to.setCategory("csa");
        taskService.saveTask(to);
        to = taskService.createTaskQuery().processInstanceId(pi.getId()).taskAssignee(userId).orderByTaskCreateTime().desc().singleResult();
        return new TaskObject(to, runtimeService);
    }

    public RuntimeService getRuntimeService(){
        return runtimeService;
    }
}
