package org.prulife.com.controller;

import org.flowable.engine.runtime.ProcessInstance;
import org.prulife.com.entities.ProcessObject;
import org.prulife.com.services.PService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/process")
//@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
public class PController {

    @Autowired
    private PService processService;

    @PostMapping(value="/start", produces= MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public @ResponseBody
    ProcessObject startProcessInstance(@RequestBody Map<String, String> req) {
        ProcessInstance pi = processService.startProcess(req.get("username"));
        if(pi != null){
            ProcessObject po = new ProcessObject(pi, processService.getRuntimeService());
            return po;
        }else {
            return new ProcessObject();
        }
    }

}
