package org.prulife.com.delegates;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.task.api.Task;
import org.flowable.engine.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Component("AsmProcessingDelegate")
public class AsmProcessingDeligate implements JavaDelegate {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;


    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("AsmProcessingDeligate is Called!");
//        ProcessInstance pr = runtimeService.createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
//        execution.setVariable("username", "juan");
//        Task task = (Task) execution.getVariable("task");
//        task.setOwner("juan");
//        task.setAssignee("juan");
//        taskService.saveTask(task);
//        System.out.println("id: " + task);
    }

}

