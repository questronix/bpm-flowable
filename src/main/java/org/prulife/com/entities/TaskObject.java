package org.prulife.com.entities;

import lombok.Data;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.flowable.variable.api.history.HistoricVariableInstanceQuery;
import org.prulife.com.services.TasksService;

import java.util.Date;
import java.util.List;


@Data
public class TaskObject {

    private String id;
    private String name;
    private String assignee;
    private String parentTaskId;
    private String processDefinitionId;
    private String processInstanceId;
    private String executionId;
    private String category;

    private Date claimTime;
    private Date dueDate;
    private Date startTime;
    private Date endTime;

    private Boolean suspended;
    private Boolean completed;

    private Variables variables;

    public TaskObject(){ }

    public TaskObject(Task t, RuntimeService rs){
        this.setId(t.getId());
        this.setName(t.getName());
        this.setAssignee(t.getAssignee());
        this.setParentTaskId(t.getParentTaskId());
        this.setProcessDefinitionId(t.getProcessDefinitionId());
        this.setProcessInstanceId(t.getProcessInstanceId());
        this.setCategory(t.getCategory());
        this.setExecutionId(t.getExecutionId());
        Variables v = new Variables();
        if(rs.getVariable(t.getProcessInstanceId(), "user") != null){
            v.setUser((Users) rs.getVariable(t.getProcessInstanceId(), "user"));
        }
        if(rs.getVariable(t.getProcessInstanceId(), "policy") != null){
            v.setPolicy((Policy) rs.getVariable(t.getProcessInstanceId(), "policy"));
        }
        if(rs.getVariable(t.getProcessInstanceId(), "appno") != null){
            v.setAppno(rs.getVariable(t.getProcessInstanceId(), "appno").toString());
        }
        if(rs.getVariable(t.getProcessInstanceId(), "group") != null){
            v.setGroup(rs.getVariable(t.getProcessInstanceId(), "group").toString());
        }
        if(rs.getVariable(t.getProcessInstanceId(), "status") != null){
            v.setStatus(rs.getVariable(t.getProcessInstanceId(), "status").toString());
        }
        if(rs.getVariable(t.getProcessInstanceId(), "userid") != null){
            v.setUsername(rs.getVariable(t.getProcessInstanceId(), "userid").toString());
        }
        this.setVariables(v);
        this.setStartTime(t.getCreateTime());
        this.setDueDate(t.getDueDate());
        this.setEndTime(null);
        this.setClaimTime(t.getClaimTime());

        this.setSuspended(t.isSuspended());
        this.setCompleted(false);
    }

    public TaskObject(HistoricTaskInstance t, HistoryService hs){
        this.setId(t.getId());
        this.setName(t.getName());
        this.setAssignee(t.getAssignee());
        this.setParentTaskId(t.getParentTaskId());
        this.setProcessDefinitionId(t.getProcessDefinitionId());
        this.setProcessInstanceId(t.getProcessInstanceId());
        this.setCategory(t.getCategory());
        this.setExecutionId(t.getExecutionId());

        List<HistoricVariableInstance> vars = hs.createHistoricVariableInstanceQuery().processInstanceId(t.getProcessInstanceId()).list();
        Variables v = new Variables();
        for(HistoricVariableInstance var : vars){
            if(var.getVariableName().equals("user")) v.setUser((Users) var.getValue());
            if(var.getVariableName().equals("policy")) v.setPolicy((Policy) var.getValue());
            if(var.getVariableName().equals("appno")) v.setAppno(var.getValue().toString());
            if(var.getVariableName().equals("group")) v.setGroup(var.getValue().toString());
            if(var.getVariableName().equals("status")) v.setStatus(var.getValue().toString());
            if(var.getVariableName().equals("userid")) v.setStatus(var.getValue().toString());
        }
        this.setVariables(v);

        this.setSuspended(false);
        this.setCompleted(true);
        this.setEndTime(t.getEndTime());
        this.setDueDate(t.getDueDate());
    }


    @Data
    public class Variables{
        private Users user;
        private String username;
        private Policy policy;
        private String appno;
        private String status;
        private String group;
    }
}
