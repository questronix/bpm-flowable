package org.prulife.com.controller;

import org.flowable.task.api.Task;

import org.prulife.com.entities.RequestModel;
import org.prulife.com.entities.ResponseModel;
import org.prulife.com.entities.TaskObject;
import org.prulife.com.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.text.ParseException;
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

    @RequestMapping(value = "/getTasksPerUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseModel getUserTasks(@RequestBody RequestModel body){


        return tasksService.getUserTasks(body.getUserId(), body.getPage(),body.getLimit());

//        return null;
    }

    @GetMapping(value="/paginated", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> getAllTasksPaginated(@RequestParam("uid") String uid, @RequestParam(name="offset", required = false) int offset, @RequestParam(name = "max", required = false) int max){
        return tasksService.getAllTasks(uid, offset, max);
    }

    @GetMapping(value = "/{tid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TaskObject getTaskById(@PathVariable("tid") String tid, @RequestParam("uid") String uid){
        return tasksService.getTaskObjectById(tid, uid);
    }

    /**
     * ----------------------------- GET HISTORY
     */

    @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<TaskObject> getAllHistoryTasks(@RequestParam("uid") String uid){
        return tasksService.getAllHistoryTasks(uid);
    }

    @GetMapping(value = "/history/{tid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TaskObject getHistoryTaskById(@PathVariable("tid") String tid, @RequestParam("uid") String uid){
        return tasksService.getTaskObjectHistoryById(tid, uid);
    }




    /**
     * ----------------------------- POST
     */

//    @PostMapping(value = "/{tid}/claim", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody TaskObject claimTaskById(@PathVariable("tid") String tid,
//                                                     @RequestParam("uid") String uid,
//                                                     @RequestBody Map<String, Object> body) throws ParseException {
//        Task task = tasksService.getTaskById(tid, uid);
//        String action = (String) body.get("action");
//        return tasksService.claimTask(task, action);
//    }

    @CrossOrigin(origins = "http://localhost:4000")
    @RequestMapping(value = "/assignTask", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> claimTaskByUserId(@RequestBody Map<String, Object> body){
        String id = body.get("id").toString();
        String toId = body.get("toId").toString();
        String fromId = body.get("fromId").toString();
//        String username =  body.get().toString();
        String action =  body.get("action").toString();
        Task task = tasksService.getTaskById(id, fromId);

        return tasksService.claimTask(task, fromId, toId, action);
    }

    @PostMapping(value = "/{tid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TaskObject completeTaskById(@PathVariable("tid") String tid,
                                                     @RequestParam("uid") String uid,
                                                     @RequestBody Map<String, Object> body) throws ParseException {
        Task task = tasksService.getTaskById(tid, uid);
        String type = (String) body.get("type");
        if(type.toUpperCase().equals("CSA")){
            String action = (String) body.get("action");
            return tasksService.completeCSA(task, action, body);
        }else if(type.toUpperCase().equals("PROCESSOR")){
            String action = (String) body.get("action");
            return tasksService.completeProcessor(task, action, body);
        }
        return tasksService.getTaskObjectById(tid, uid);
    }

}
