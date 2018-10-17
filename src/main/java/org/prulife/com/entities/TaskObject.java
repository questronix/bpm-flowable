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
            v.setAppno((String) rs.getVariable(t.getProcessInstanceId(), "appno"));
        }
        if(rs.getVariable(t.getProcessInstanceId(), "group") != null){
            v.setGroup((String) rs.getVariable(t.getProcessInstanceId(), "group"));
        }
        if(rs.getVariable(t.getProcessInstanceId(), "status") != null){
            v.setStatus((String) rs.getVariable(t.getProcessInstanceId(), "status"));
        }
        if(rs.getVariable(t.getProcessInstanceId(), "username") != null){
            v.setUsername(rs.getVariable(t.getProcessInstanceId(), "username").toString());
        }
        this.setVariables(v);
        this.setStartTime(t.getCreateTime());
        this.setDueDate(t.getDueDate());
        this.setEndTime(null);
        this.setClaimTime(t.getClaimTime());

        this.setSuspended(t.isSuspended());
        this.setCompleted(false);
    }

    public TaskObject(Task t, TasksService ts){

        this.setId(t.getId());
        this.setName(t.getName());
        this.setAssignee(t.getAssignee());
        this.setParentTaskId(t.getParentTaskId());
        this.setProcessDefinitionId(t.getProcessDefinitionId());
        this.setCategory(t.getCategory());
        this.setExecutionId(t.getExecutionId());


        if(ts.getRuntimeService().getVariable(t.getProcessInstanceId(), "user") != null){
            this.variables.setUser((Users) ts.getRuntimeService().getVariable(t.getProcessInstanceId(), "user"));
        }
        if(ts.getRuntimeService().getVariable(t.getProcessInstanceId(), "policy") != null){
            this.variables.setPolicy((Policy) ts.getRuntimeService().getVariable(t.getProcessInstanceId(), "policy"));
        }
        if(ts.getRuntimeService().getVariable(t.getProcessInstanceId(), "appno") != null){
            this.variables.setAppno((String) ts.getRuntimeService().getVariable(t.getProcessInstanceId(), "appno"));
        }
        if(ts.getRuntimeService().getVariable(t.getProcessInstanceId(), "group") != null){
            this.variables.setGroup((String) ts.getRuntimeService().getVariable(t.getProcessInstanceId(), "group"));
        }
        if(ts.getRuntimeService().getVariable(t.getProcessInstanceId(), "status") != null){
            this.variables.setGroup((String) ts.getRuntimeService().getVariable(t.getProcessInstanceId(), "status"));
        }

        this.setStartTime(t.getCreateTime());
        this.setDueDate(t.getDueDate());
        this.setEndTime(null);
        this.setClaimTime(t.getClaimTime());

        this.setSuspended(t.isSuspended());
        this.setCompleted(false);
    }

    public TaskObject(HistoricTaskInstance t, TasksService ts){
        this.setId(t.getId());
        this.setStartTime(t.getCreateTime());
        this.setAssignee(t.getAssignee());
        this.setName(t.getName());
        this.setProcessDefinitionId(t.getProcessDefinitionId());
        List<HistoricVariableInstance> vars = ts.getHistoryService().createHistoricVariableInstanceQuery().processInstanceId(t.getProcessInstanceId()).list();
        for(HistoricVariableInstance var : vars){
            if(var.getVariableName().equals("user")) this.variables.setUser((Users) var.getValue());
            if(var.getVariableName().equals("policy")) this.variables.setPolicy((Policy) var.getValue());
            if(var.getVariableName().equals("appno")) this.variables.setAppno((String) var.getValue());
            if(var.getVariableName().equals("group")) this.variables.setGroup((String) var.getValue());
            if(var.getVariableName().equals("status")) this.variables.setStatus((String) var.getValue());
        }
        this.setSuspended(false);
        this.setParentTaskId(t.getParentTaskId());
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
