package org.prulife.com.services;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.prulife.com.entities.ResponseModel;
import org.prulife.com.entities.TaskObject;
import org.prulife.com.utilities.ResponseUtility;
import org.springframework.stereotype.Component;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReinstatementService implements WorkflowBase
{

    TaskService taskService;

    RuntimeService runtimeService;


    public ReinstatementService(TaskService taskService, RuntimeService runtimeService){

        this.taskService = taskService;
        this.runtimeService = runtimeService;

    }

    @Override
    public ResponseModel startProcess(String processName, Map<String, Object> variables) {

        try {

            ProcessInstance process = runtimeService.startProcessInstanceByKey(processName, variables);
            process.getProcessInstanceId();
            Task task = taskService.createTaskQuery().processInstanceId(process.getProcessInstanceId()).taskName("CSA Pre-screening").singleResult();
            ResponseModel res = ResponseUtility.returnSuccessResponseModel();
            Map<String,Object> map = new HashMap<>();
            map.put("taskId", task.getId());
            return ResponseUtility.returnSuccessResponseModel(map);

        }
        catch (Exception ex){
            return  ResponseUtility.returnFailedResponseModel();
        }

    }

    @Override
    public Boolean completeTask(String taskId, Map<String, Object> variables) {

        try{

            taskService.complete(taskId, variables);
            return true;

        }
        catch (Exception ex){

            return  false;

        }

    }


}
