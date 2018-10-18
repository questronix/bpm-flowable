package org.prulife.com.services;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;

import org.prulife.com.entities.Policy;
import org.prulife.com.entities.TaskObject;
import org.prulife.com.entities.Users;
import org.prulife.com.repository.PolicyRepository;
import org.prulife.com.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    /**
     * Logic for complete CSA Task
     * Upon completion it will create new task for Processor's evaluation
     *
     * @param Task task
     * @param String action
     * @param Policy policy
     * @return TaskObject
     */
    public TaskObject completeCSA(Task task, String action, Policy policy){
        String taskid = task.getId();
        if(action.toLowerCase().equals("complete")){
            Policy p = policyRepository.findById(policy.getId()).get();
            p.setStatus("processor");
            p.setNumber(policy.getNumber());
            p.setInfo(policy.getInfo());
            Users user = userRepository.findByUsername("jerome");
            runtimeService.setVariable(task.getExecutionId(), "policy", p);
            runtimeService.setVariable(task.getExecutionId(), "status", "processor");
            runtimeService.setVariable(task.getExecutionId(), "isCompleteAndValid", true);
            runtimeService.setVariable(task.getExecutionId(), "userid", user.getId());
            runtimeService.setVariable(task.getExecutionId(), "user", user);
            runtimeService.setVariable(task.getExecutionId(), "group", "processor");
            taskService.complete(taskid);
            Task t = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).orderByTaskCreateTime().desc().singleResult();
            t.setParentTaskId(task.getId());
            t.setOwner(user.getUsername());
            t.setCategory(user.getRole());
            t.setAssignee(user.getId() + "");
            taskService.saveTask(t);
            policyRepository.save(p);
            return new TaskObject(t, runtimeService);
        }else if(action.toLowerCase().equals("delete")){
//            taskService.deleteTask(taskid); TODO:
        }
        return null;
    }

    /**
     * Logic for complete Processor Task
     * Upon completion it will assign new task to other user
     *
     * @param Task task
     * @param String action
     * @param Policy policy
     * @return TaskObject
     */
    public TaskObject completeProcessor(Task task, String action, Policy policy) {
        String taskid = task.getId();
        return new TaskObject(task, runtimeService);
    }

    public Task getTaskById(String tid, String uid) {
        return taskService.createTaskQuery().taskAssignee(uid).taskId(tid).singleResult();
    }

    public TaskObject getTaskObjectById(String tid, String uid) {
        Task task =  taskService.createTaskQuery().taskAssignee(uid).taskId(tid).singleResult();
        return new TaskObject(task, runtimeService);
    }

    public Task getTaskById(String tid, String username, Policy policy) {
        Task task =  taskService.createTaskQuery().taskAssignee(username).taskId(tid).singleResult();
        Policy p = (Policy) runtimeService.getVariable(task.getProcessInstanceId(), "policy");
        p = policy;
        runtimeService.setVariable(task.getExecutionId(), "policy", p);
        policyRepository.save(policy);
        return task;
    }

    public List<TaskObject> getAllTasks(String uid) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(uid).orderByTaskCreateTime().desc().list();
        List<TaskObject> list = new ArrayList<TaskObject>();
        for(Task task : tasks){
            list.add(new TaskObject(task, runtimeService));
        }
        return list;
    }

    public RuntimeService getRuntimeService(){
        return runtimeService;
    }
    public TaskService getTaskService() { return taskService; }
    public HistoryService getHistoryService() { return  historyService; }

}
