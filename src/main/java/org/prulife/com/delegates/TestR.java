package org.prulife.com.delegates;

import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

public class TestR {
    @Autowired
    TaskService taskService;

    public void task() {
        System.out.println("a");

    }
}
