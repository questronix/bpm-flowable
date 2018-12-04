package org.prulife.com.services;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import org.flowable.task.api.history.HistoricTaskInstance;
import org.prulife.com.entities.ResponseModel;
import org.prulife.com.entities.TaskObject;
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


    /**
     * Completes the task of CSA
     * Upon completion it will create new task for Processor's evaluation
     *
     * @param body Map Request Body
     * @return TaskObject
     */
    public Boolean completeCSAPrescreening(String taskId, Map<String, Object> body){
        try{
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

            ResponseModel responseModel = new ResponseModel();
            runtimeService.setVariable(task.getExecutionId(), "status", "processor");
            runtimeService.setVariable(task.getExecutionId(), "beyondLimit", body.get("beyondLimit"));
            runtimeService.setVariable(task.getExecutionId(), "hrcStr", body.get("hrcStr"));
            runtimeService.setVariable(task.getExecutionId(), "cia", body.get("cia"));
            runtimeService.setVariable(task.getExecutionId(), "cio", body.get("cio"));
            runtimeService.setVariable(task.getExecutionId(), "soi", body.get("soi"));
            runtimeService.setVariable(task.getExecutionId(), "fatca", body.get("fatca"));
            runtimeService.setVariable(task.getExecutionId(), "modules", "processor");
            runtimeService.setVariable(task.getExecutionId(), "radcaa", body.get("radcaa"));
            runtimeService.setVariable(task.getExecutionId(), "pos", body.get("pos"));

            taskService.complete(task.getId());
            Task t = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).orderByTaskCreateTime().desc().singleResult();
            t.setParentTaskId(task.getId());
            taskService.saveTask(t);
            return true;
         }
        catch (Exception ex){
            return  false;
        }
    }

    public Boolean completeCsaProcessing(String taskId){
        try{
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            taskService.complete(task.getId());
//            Task t = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).orderByTaskCreateTime().desc().singleResult();
//            t.setParentTaskId(task.getId());
//            taskService.saveTask(t);
            return true;
        }
        catch (Exception ex){
            return  false;
        }
    }

    public Boolean completeCsaApproval(String taskId, Map<String, Object> body){
        try{
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            runtimeService.setVariable(task.getExecutionId(), "isApprove", body.get("isApprove"));
            runtimeService.setVariable(task.getExecutionId(), "remarks", body.get("remarks"));

            taskService.complete(task.getId());
//            Task t = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).orderByTaskCreateTime().desc().singleResult();
//            t.setParentTaskId(task.getId());
//            taskService.complete(t.getId());
//            taskService.saveTask(t);
            return true;
        }
        catch (Exception ex){
            return  false;
        }
    }



//    public TaskObject getTaskByExcutionID (String excusionID) {
//        ProcessInstance =  runtimeService.createProcessInstanceQuery()
//        ProcessInstance TaskObject = runtimeService.createProcessInstanceQuery()
//    }

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
//            Users user = userRepository.findByUsername("jerome");
            runtimeService.setVariable(task.getExecutionId(), "status", "complete");
            runtimeService.setVariable(task.getExecutionId(), "modules", "processor");
            taskService.complete(taskid);
//            HistoricTaskInstance htask = getTaskHistoryByUsername(taskid, user.getId().toString());
            HistoricTaskInstance htask = getTaskHistoryByUsername(taskid, "3");
            return new TaskObject(htask, historyService);
        }else if(action.toLowerCase().equals("delete")){
//            taskService.deleteTask(taskid); TODO:
        }
        return new TaskObject(task, runtimeService);
    }

//    /**
//     * Completion of task by its id
//     * Upon completion it will assign the task to other user
//     *
//     * @param task Task object
//     * @return TaskObject
//     */
//    public TaskObject claimTask(Task task, String asignee) {
//        String taskid = task.getId();
//        runtimeService.setVariable(task.getExecutionId(), "status", "processing");
//        taskService.claim(taskid, asignee);
//        return new TaskObject(task, runtimeService);
//
//    }

    /**
     * Completion of task by its id
     * Upon completion it will assign the task to other user
     *
     * @param task Task object
     * @return TaskObject
     */
    public Map<String, Object> claimTask(Task task, String fromId, String toId, String action) {
        Map<String, Object> map = new HashMap<>();
        String taskid = task.getId();

        switch (action.toLowerCase()) {
            case "processing": runtimeService.setVariable(task.getExecutionId(), "status", "processing");
            break;
            case "approving": runtimeService.setVariable(task.getExecutionId(), "status", "approving");

                break;
            default: map.put("isSuccess", false);
                return map;

        }
        if(toId != fromId ) {
            taskService.unclaim(taskid);
            taskService.claim(taskid, toId);
            map.put("isSuccess", true);
            return map;
        }
        map.put("isSuccess", false);
        return map;
    }

    /**
     * <-------- RUNNING TASKS METHODS
     */

    /**
     * GET Running Task by its id
     * @param tid Task id
     * @param username User id
     * @return Task
     */
    public Task getTaskByUsername(String tid, String username) {
        return taskService.createTaskQuery().taskAssignee(username).taskId(tid).singleResult();
    }

//    /**
//     * <-------- RUNNING TASKS METHODS
//     */
//
//    /**
//     * GET Running Task by its id
//     * @param tid Task id
//     * @param username User id
//     * @return Task
//     */
    public Task getTaskByUsername(String tid) {
        return taskService.createTaskQuery().taskId(tid).singleResult();
    }

    /**
     * GET Running Task by its id
     * @param tid Task id
     * @param username User id
     * @return TaskObject
     */
    public TaskObject getTaskObjectByUsername(String tid, String username) {
        Task task =  taskService.createTaskQuery().taskAssignee(username).taskId(tid).singleResult();
        return new TaskObject(task, runtimeService);
    }


    /**
     * GET All Running Task by its user id
     * @param username User id
     * @return List<TaskObject>
     */
    public List<TaskObject> getAllTasks(String username) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).orderByTaskCreateTime().desc().list();
        List<TaskObject> list = new ArrayList<TaskObject>();
        for(Task task : tasks){
            list.add(new TaskObject(task, runtimeService));
        }
        return list;
    }

    public Map<String, Object> getAllTasks(String username, int page, int item) {
        page = (page - 1) * item;
        Map map = new HashMap<String, Object>();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).orderByTaskCreateTime().desc().listPage(page, item);
        double ans = taskService.createTaskQuery().taskAssignee(username).count() / item;
        int taskCount = (int) Math.round(ans);
//        id
        List<TaskObject> list = new ArrayList<TaskObject>();
        for(Task task : tasks){
            list.add(new TaskObject(task, runtimeService));
        }
        map.put("pageCount", taskCount);
        map.put("tasks", list);

        return map;
    }
    public Map<String, Object> getAllTasks(String username, int page, int item, String policyNo) {
        page = (page - 1) * item;
        Map map = new HashMap<String, Object>();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).orderByTaskCreateTime().desc().listPage(page, item);
        double ans = taskService.createTaskQuery().taskAssignee(username).count() / item;
        int taskCount = (int) Math.round(ans);
//        id
        List<TaskObject> list = new ArrayList<TaskObject>();
        for(Task task : tasks){
            list.add(new TaskObject(task, runtimeService));
        }
        map.put("pageCount", taskCount);
        map.put("tasks", list);

        return map;
    }
//    public Map<String, Object> getAllTasks(String username, int page, int item) {
//        page = (page - 1) * item;
//        Map map = new HashMap<String, Object>();
//        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).orderByTaskCreateTime().desc().listPage(page, item);
//        double ans = taskService.createTaskQuery().taskAssignee(username).count() / item;
//        int taskCount = (int) Math.round(ans);
////        id
//        List<TaskObject> list = new ArrayList<TaskObject>();
//        for(Task task : tasks){
//            list.add(new TaskObject(task, runtimeService));
//        }
//        map.put("pageCount", taskCount);
//        map.put("tasks", list);
//
//        return map;
//    }


    public ResponseModel getUserTasks(String username, int page, int item) {

        ResponseModel model = new ResponseModel();
        model.setMessage("success");
//        model.isSuccess(false);
        page = (page - 1) * item;
        Map map = new HashMap<String, Object>();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).orderByTaskCreateTime().desc().listPage(page, item);
        double ans = taskService.createTaskQuery().taskAssignee(username).count() / item;
        int taskCount = (int) Math.round(ans);
//        id
        List<TaskObject> list = new ArrayList<TaskObject>();
//        for(Task task : tasks){
//            list.add(new TaskObject(task, runtimeService));
//        }
        map.put("pageCount", taskCount);
        map.put("tasks", tasks);
        model.setResult(map);

        return model;
    }

//    public List<TaskObject> getAllTasks(String username, int page, int item) {
//        page = (page - 1) * item;
//        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).orderByTaskCreateTime().desc().listPage(page, item);
//        long taskCount = taskService.createTaskQuery().taskAssignee(username).count();
//        List<TaskObject> list = new ArrayList<TaskObject>();
//        for(Task task : tasks){
//            list.add(new TaskObject(task, runtimeService));
//        }
//
//        return list;
//    }

    /**
     * GET All Paginated Running Task by its user id
     * @param username User id
     * @return List<TaskObject>
     */
    public List<TaskObject> getAllTasksPaginated(String username, int page, int item) {
        page = (page - 1) * item;
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).orderByTaskCreateTime().desc().listPage(page, item);
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
     * @param username User id
     * @return HistoryTaskInstance
     */
    public HistoricTaskInstance getTaskHistoryByUsername(String tid, String username) {
        return historyService.createHistoricTaskInstanceQuery().taskId(tid).taskAssignee(username).orderByTaskCreateTime().desc().singleResult();
    }

    /**
     * GET History Task by its id
     * @param tid Task id
     * @param username User id
     * @return TaskObject
     */
    public TaskObject getTaskObjectHistoryByUsername(String tid, String username) {
        HistoricTaskInstance hTask = historyService.createHistoricTaskInstanceQuery().taskId(tid).taskAssignee(username).finished().orderByTaskCreateTime().desc().singleResult();
        return new TaskObject(hTask, historyService);
    }

    /**
     * GET All History Task by its user id
     * @param username User id
     * @return List<TaskObject>
     */
    public List<TaskObject> getAllHistoryTasks(String username) {
        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().taskAssignee(username).finished().orderByTaskCreateTime().desc().list();
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
