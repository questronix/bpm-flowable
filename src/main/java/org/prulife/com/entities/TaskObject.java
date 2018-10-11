package org.prulife.com.entities;

import lombok.Data;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import java.util.Date;


@Data
public class TaskObject {

    private String id;
    private Date startTime;
    private String assignee;
    private String name;
    private String processDefinitionId;
    private Users user;
    private Policy policy;
    private String appno;
    private String owner;
    private Boolean suspended;
    private String parentTaskId;

    public TaskObject(){

    }

    public TaskObject(Task t, RuntimeService rs){
        this.setId(t.getId());
        this.setStartTime(t.getCreateTime());
        this.setAssignee(t.getAssignee());
        this.setName(t.getName());
        this.setProcessDefinitionId(t.getProcessDefinitionId());
        if(rs.getVariable(t.getProcessInstanceId(), "user") != null){
            this.setUser((Users) rs.getVariable(t.getProcessInstanceId(), "user"));
        }
        if(rs.getVariable(t.getProcessInstanceId(), "policy") != null){
            this.setPolicy((Policy) rs.getVariable(t.getProcessInstanceId(), "policy"));
        }
        if(rs.getVariable(t.getProcessInstanceId(), "appno") != null){
            this.setAppno((String) rs.getVariable(t.getProcessInstanceId(), "appno"));
        }
        this.setOwner(t.getOwner());
        this.setSuspended(t.isSuspended());
        this.setParentTaskId(t.getParentTaskId());
    }
}
