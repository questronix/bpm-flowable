package org.prulife.com.entities;


import lombok.Data;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

@Data
public class ProcessObject {

    private String id;
    private Date startTime;
    private String name;
    private String deploymentId;
    private Users user;
    private Policy policy;
    private String docid;
    private String businessKey;
    private String processInstanceId;
    private int processVersion;

    public ProcessObject(){

    }

    public  ProcessObject(ProcessInstance pi, RuntimeService rs){
        this.setId(pi.getId());
        this.setStartTime(pi.getStartTime());
        this.setName(pi.getName());
        this.setDeploymentId(pi.getDeploymentId());
        this.setUser((Users) rs.getVariable(pi.getId(), "user"));
        this.setPolicy((Policy) rs.getVariable(pi.getId(), "policy"));
        this.setDocid((String) rs.getVariable(pi.getId(), "docid"));
        this.setBusinessKey(pi.getBusinessKey());
        this.setProcessInstanceId(pi.getProcessInstanceId());
        this.setProcessVersion(pi.getProcessDefinitionVersion());
    }
}
