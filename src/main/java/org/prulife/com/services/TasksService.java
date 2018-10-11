package org.prulife.com.services;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;

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
    private UsersRepository userRepository;

    public Task getTaskById(String tid, String username) {
        Task task =  taskService.createTaskQuery().taskAssignee(username).taskId(tid).singleResult();
        return task;
    }

    public List<Task> getAllTasks(String username) {
        return taskService.createTaskQuery().taskAssignee(username).list();
    }

    public RuntimeService getRuntimeService(){
        return runtimeService;
    }
    public TaskService getTaskService() { return taskService; }

}
