package org.prulife.com.controller;

import org.prulife.com.entities.TaskObject;
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
    TaskObject startProcessInstance(@RequestBody Map<String, String> req) {
        TaskObject to = processService.startProcess(req.get("username"), req.get("transactionNo"), req.get("policyNo"));
        return to;
    }

}
