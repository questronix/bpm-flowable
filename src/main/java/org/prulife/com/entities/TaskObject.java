package org.prulife.com.entities;

import lombok.Data;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.flowable.variable.api.history.HistoricVariableInstanceQuery;

import java.util.Date;
import java.util.List;


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
    private Boolean completed;
    private Date endTime;

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

    public TaskObject(HistoricTaskInstance t, HistoryService rs){
        this.setId(t.getId());
        this.setStartTime(t.getCreateTime());
        this.setAssignee(t.getAssignee());
        this.setName(t.getName());
        this.setProcessDefinitionId(t.getProcessDefinitionId());
        List<HistoricVariableInstance> vars = rs.createHistoricVariableInstanceQuery().processInstanceId(t.getProcessInstanceId()).list();
        for(HistoricVariableInstance var : vars){
            if(var.getVariableName().equals("user")) this.setUser((Users) var.getValue());
            if(var.getVariableName().equals("policy")) this.setPolicy((Policy) var.getValue());
            if(var.getVariableName().equals("appno")) this.setAppno((String) var.getValue());
        }
        this.setOwner(t.getOwner());
        this.setSuspended(false);
        this.setParentTaskId(t.getParentTaskId());
        this.setCompleted(true);
        this.setEndTime(t.getEndTime());
    }
}
