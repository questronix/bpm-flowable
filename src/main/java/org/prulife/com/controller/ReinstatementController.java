package org.prulife.com.controller;


import org.prulife.com.entities.ResponseModel;
import org.prulife.com.services.PService;
import org.prulife.com.services.ReinstatementService;
import org.prulife.com.utilities.ResponseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/workflow/reinstatement")

public class ReinstatementController {

    @Autowired
    private PService processService;

    @Autowired
    ReinstatementService reinstatementService;

    @CrossOrigin(origins = "*")
    @PostMapping(value="/start", produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseModel startProcessInstance(@RequestBody Map<String, Object> req) {

        String processName = req.get("processName").toString();
        return reinstatementService.startProcess(processName,(Map<String, Object>) req.get("variables"));

    }

    @CrossOrigin(origins = "*")
    @PostMapping(value="/completeTask", produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseModel completeTask(@RequestBody Map<String, Object> req) {

        String taskId = req.get("taskId").toString();
        if(reinstatementService.completeTask(taskId,(Map<String, Object>) req.get("variables"))){
            return ResponseUtility.returnSuccessResponseModel();

        }else{
            return  ResponseUtility.returnFailedResponseModel();
        }
    }

}
