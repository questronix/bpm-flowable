package org.prulife.com.services;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.prulife.com.entities.TaskObject;
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

    public TaskObject startProcess(String prescreener, String transactionNo, String policyNo, String unitHead) {
        //set flowable variables
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("transactionNo", transactionNo);
        variables.put("policyNo", policyNo);
        variables.put("modules", "csa");
        variables.put("status", "draft");
        variables.put("prescreener", prescreener);
        variables.put("unitHead", unitHead);
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("reinstatementStart", "Reinstatement", variables);
        Task to = taskService.createTaskQuery().processInstanceId(pi.getId()).taskAssignee(prescreener).orderByTaskCreateTime().desc().singleResult();
//        to.setOwner(username);
//        to.setCategory("csa");
        taskService.saveTask(to);
        to = taskService.createTaskQuery().processInstanceId(pi.getId()).taskAssignee(prescreener).orderByTaskCreateTime().desc().singleResult();
        return new TaskObject(to, runtimeService);
    }

    public RuntimeService getRuntimeService(){
        return runtimeService;
    }
}
