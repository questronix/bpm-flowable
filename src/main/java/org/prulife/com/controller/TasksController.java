package org.prulife.com.controller;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/tasks")
//@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public @ResponseBody
    List<TaskObject> getAllTasks(@RequestParam("username") String username) {
        List<Task> tasks = tasksService.getAllTasks(username);
        List<TaskObject> list = new ArrayList<TaskObject>();
        for(Task task : tasks){
            list.add(new TaskObject(task, tasksService.getRuntimeService()));
        }
        List<HistoricTaskInstance> htasks = tasksService.getHistoryService().createHistoricTaskInstanceQuery().finished().list();
        for(HistoricTaskInstance htask : htasks){
            list.add(new TaskObject(htask, tasksService.getHistoryService()));
        }

        return list;
    }

    @GetMapping(value="/{tid}", produces= MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public @ResponseBody
    TaskObject getTask(@PathVariable("tid") String tid, @RequestParam("username") String username) {
        Task task = tasksService.getTaskById(tid, username);
        return new TaskObject(task, tasksService.getRuntimeService());
    }

    @PostMapping(value="/{tid}", produces= MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public @ResponseBody
    TaskObject completeTask(@PathVariable("tid") String tid, @RequestParam("username") String username, @RequestBody Policy policy) {
        Task task = tasksService.getTaskById(tid, username, policy);
        String taskid = task.getId();
        tasksService.getTaskService().complete(taskid);
        HistoricTaskInstance htask = tasksService.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .finished()
                .taskId(taskid)
                .singleResult();
        return new TaskObject(htask, tasksService.getHistoryService());
    }

}
