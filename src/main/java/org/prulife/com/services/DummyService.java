package org.prulife.com.services;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;

import org.prulife.com.entities.Users;
import org.prulife.com.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DummyService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UsersRepository userRepository;

    public void startProcess() {
        Users user = userRepository.findByUsername("kenster");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("users", user);
        runtimeService.startProcessInstanceByKey("startCSA", variables);
    }

    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().list();
    }

    public Users getUser(String username){
        return userRepository.findByUsername(username);
    }

}
