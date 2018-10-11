package org.prulife.com.services;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;

import org.prulife.com.entities.Policy;
import org.prulife.com.repository.PolicyRepository;
import org.prulife.com.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TasksService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PolicyRepository policyRepository;

    public Task getTaskById(String tid, String username) {
        Task task =  taskService.createTaskQuery().taskAssignee(username).taskId(tid).singleResult();
        return task;
    }

    public Task getTaskById(String tid, String username, Policy policy) {
        Task task =  taskService.createTaskQuery().taskAssignee(username).taskId(tid).singleResult();
        Policy p = (Policy) runtimeService.getVariable(task.getProcessInstanceId(), "policy");
        p = policy;
        runtimeService.setVariable(task.getExecutionId(), "policy", p);
        policyRepository.save(policy);
        return task;
    }

    public List<Task> getAllTasks(String username) {
        return taskService.createTaskQuery().taskAssignee(username).list();
    }

    public RuntimeService getRuntimeService(){
        return runtimeService;
    }
    public TaskService getTaskService() { return taskService; }
    public HistoryService getHistoryService() { return  historyService; }
}
