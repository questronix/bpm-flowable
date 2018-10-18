package org.prulife.com.controller;

import lombok.Data;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;

import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.prulife.com.entities.Policy;
import org.prulife.com.entities.TaskObject;
import org.prulife.com.entities.Users;
import org.prulife.com.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/tasks")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<TaskObject> getAllTasks(@RequestParam("uid") String uid){
        return tasksService.getAllTasks(uid);
    }

    @GetMapping(value = "/{tid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TaskObject getTaskById(@PathVariable("tid") String tid, @RequestParam("uid") String uid){
        return tasksService.getTaskObjectById(tid, uid);
    }

    @PostMapping(value = "/{tid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TaskObject completeTaskById(@PathVariable("tid") String tid,
                                                     @RequestParam("uid") String uid,
                                                     @RequestBody Map<String, Object> body) throws ParseException {
        Task task = tasksService.getTaskById(tid, uid);
        String type = (String) body.get("type");
        if(type.toUpperCase().equals("CSA")){
            String action = (String) body.get("action");
            Policy policy = new Policy();
            LinkedHashMap hash = (LinkedHashMap) body.get("policy");
            policy.setId(((Number)hash.get("id")).longValue());
            policy.setInfo((String) hash.get("info"));
            policy.setNumber((String) hash.get("number"));
            return tasksService.completeCSA(task, action, policy);
        }else if(type.toUpperCase().equals("PROCESSOR")){
            String action = (String) body.get("action");
            Policy policy = new Policy();
            LinkedHashMap hash = (LinkedHashMap) body.get("policy");
            policy.setId(((Number)hash.get("id")).longValue());
            policy.setInfo((String) hash.get("info"));
            policy.setNumber((String) hash.get("number"));
            return tasksService.completeProcessor(task, action, policy);
        }
        return tasksService.getTaskObjectById(tid, uid);
    }

}
