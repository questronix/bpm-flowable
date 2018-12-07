package org.prulife.com.services;

import org.prulife.com.entities.ResponseModel;

import java.util.Map;

public interface WorkflowBase {

    ResponseModel startProcess(String processName, Map<String, Object> variables);
    Boolean completeTask(String taskId, Map<String, Object> variables);


}
