package org.prulife.com.services;

import org.apache.catalina.User;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;

import org.flowable.task.api.history.HistoricTaskInstance;
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
     * Completes the task of CSA
     * Upon completion it will create new task for Processor's evaluation
     *
     * @param task Task object
     * @param action String action
     * @param body Map Request Body
     * @return TaskObject
     */
    public TaskObject completeCSA(Task task, String action, Map<String, Object> body){
        String taskid = task.getId();
        if(action.toLowerCase().equals("complete")){
            Policy policy = new Policy();
            LinkedHashMap hash = (LinkedHashMap) body.get("policy");
            policy.setId(((Number)hash.get("id")).longValue());
            policy.setInfo((String) hash.get("info"));
            policy.setNumber((String) hash.get("number"));

            Policy p = policyRepository.findById(policy.getId()).get();
            p.setStatus("processor");
            p.setNumber(policy.getNumber());
            p.setInfo(policy.getInfo());
            Users user = userRepository.findByUsername("jerome");
            runtimeService.setVariable(task.getExecutionId(), "policy", p);
            runtimeService.setVariable(task.getExecutionId(), "status", "processor");
            runtimeService.setVariable(task.getExecutionId(), "isCompleteAndValid", body.get("isCompleteAndValid"));
            runtimeService.setVariable(task.getExecutionId(), "userid", user.getId());
            runtimeService.setVariable(task.getExecutionId(), "user", user);
            runtimeService.setVariable(task.getExecutionId(), "group", "processor");
            taskService.complete(taskid);
            Task t = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).orderByTaskCreateTime().desc().singleResult();
            t.setParentTaskId(task.getId());
            t.setOwner(user.getUsername());
            t.setCategory(user.getRole().getName());
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
     * Completes the task of Processor
     * Upon completion it will assign new task to other user
     *
     * @param task Task object
     * @param action String action
     * @param input Map Request Body
     * @return TaskObject
     */
    public TaskObject completeProcessor(Task task, String action, Map<String, Object> input) {
        String taskid = task.getId();
        if(action.toLowerCase().equals("complete")){
            Policy policy = null;
            if(runtimeService.getVariable(task.getProcessInstanceId(), "policy") != null){
                policy = (Policy) runtimeService.getVariable(task.getProcessInstanceId(), "policy");
            }
            Users user = userRepository.findByUsername("jerome");
            runtimeService.setVariable(task.getExecutionId(), "user", user);
            runtimeService.setVariable(task.getExecutionId(), "processor-input", input.get("input"));
            runtimeService.setVariable(task.getExecutionId(), "hit", input.get("hit"));
            runtimeService.setVariable(task.getExecutionId(), "beyondAuthority", input.get("beyondAuthority"));
            runtimeService.setVariable(task.getExecutionId(), "status", "complete");
            runtimeService.setVariable(task.getExecutionId(), "group", "processor");
            runtimeService.setVariable(task.getExecutionId(), "userid", user.getId());
            runtimeService.setVariable(task.getExecutionId(), "policy", policy);
            taskService.complete(taskid);
            HistoricTaskInstance htask = getTaskHistoryById(taskid, user.getId().toString());
            return new TaskObject(htask, historyService);
        }else if(action.toLowerCase().equals("delete")){
//            taskService.deleteTask(taskid); TODO:
        }
        return new TaskObject(task, runtimeService);
    }


    /**
     * Completion of task by its id
     * Upon completion it will assign the task to other user
     *
     * @param task Task object
     * @param action String action
     * @return TaskObject
     */
    public TaskObject claimTask(Task task, String action) {
        String taskid = task.getId();
        if(action.toLowerCase().equals("claim")){
            taskService.claim(taskid, task.getAssignee());
            Task t = getTaskById(taskid, task.getAssignee());
            return new TaskObject(t, runtimeService);
        }else if(action.toLowerCase().equals("delete")){
//            taskService.deleteTask(taskid); TODO:
        }
        return new TaskObject(task, runtimeService);
    }

    /**
     * <-------- RUNNING TASKS METHODS
     */

    /**
     * GET Running Task by its id
     * @param tid Task id
     * @param uid User id
     * @return Task
     */
    public Task getTaskById(String tid, String uid) {
        return taskService.createTaskQuery().taskAssignee(uid).taskId(tid).singleResult();
    }

    /**
     * GET Running Task by its id
     * @param tid Task id
     * @param uid User id
     * @return TaskObject
     */
    public TaskObject getTaskObjectById(String tid, String uid) {
        Task task =  taskService.createTaskQuery().taskAssignee(uid).taskId(tid).singleResult();
        return new TaskObject(task, runtimeService);
    }


    /**
     * GET All Running Task by its user id
     * @param uid User id
     * @return List<TaskObject>
     */
    public List<TaskObject> getAllTasks(String uid) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(uid).orderByTaskCreateTime().desc().list();
        List<TaskObject> list = new ArrayList<TaskObject>();
        for(Task task : tasks){
            list.add(new TaskObject(task, runtimeService));
        }
        return list;
    }

    /**
     * --------> END RUNNING TASKS METHODS
     */



    /**
     * <-------- HISTORY TASKS METHODS
     */

    /**
     * GET History Task by its id
     * @param tid Task id
     * @param uid User id
     * @return HistoryTaskInstance
     */
    public HistoricTaskInstance getTaskHistoryById(String tid, String uid) {
        return historyService.createHistoricTaskInstanceQuery().taskId(tid).taskAssignee(uid).orderByTaskCreateTime().desc().singleResult();
    }

    /**
     * GET History Task by its id
     * @param tid Task id
     * @param uid User id
     * @return TaskObject
     */
    public TaskObject getTaskObjectHistoryById(String tid, String uid) {
        HistoricTaskInstance hTask = historyService.createHistoricTaskInstanceQuery().taskId(tid).taskAssignee(uid).finished().orderByTaskCreateTime().desc().singleResult();
        return new TaskObject(hTask, historyService);
    }

    /**
     * GET All History Task by its user id
     * @param uid User id
     * @return List<TaskObject>
     */
    public List<TaskObject> getAllHistoryTasks(String uid) {
        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().taskAssignee(uid).finished().orderByTaskCreateTime().desc().list();
        List<TaskObject> list = new ArrayList<TaskObject>();
        for(HistoricTaskInstance task : tasks){
            list.add(new TaskObject(task, historyService));
        }
        return list;
    }


    /**
     * --------> END HISTORY TASKS METHODS
     */


    public RuntimeService getRuntimeService(){
        return runtimeService;
    }
    public TaskService getTaskService() { return taskService; }
    public HistoryService getHistoryService() { return  historyService; }

}
