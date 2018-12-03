package org.prulife.com.entities;

import lombok.Data;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.flowable.variable.api.history.HistoricVariableInstanceQuery;
import org.flowable.variable.api.persistence.entity.VariableInstance;
import org.prulife.com.services.TasksService;

import java.util.Date;
import java.util.List;
import java.util.Map;


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

    private Map<String, Object> variables;



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
        this.setVariables(rs.getVariables(t.getProcessInstanceId()));

//        Variables v = new Variables();
//        if(rs.getVariable(t.getProcessInstanceId(), "username") != null){
//            v.setUsername(rs.getVariable(t.getProcessInstanceId(), "username").toString());
//        }
//        if(rs.getVariable(t.getProcessInstanceId(), "transactionNo") != null){
//            v.setTransactionNumber(rs.getVariable(t.getProcessInstanceId(), "transactionNo").toString());
//        }
//        if(rs.getVariable(t.getProcessInstanceId(), "modules") != null){
//            v.setGroup(rs.getVariable(t.getProcessInstanceId(), "modules").toString());
//        }
//        if(rs.getVariable(t.getProcessInstanceId(), "status") != null){
//            v.setStatus(rs.getVariable(t.getProcessInstanceId(), "status").toString());
//        }
//        if(rs.getVariable(t.getProcessInstanceId(), "userid") != null){
//            v.setUserid(rs.getVariable(t.getProcessInstanceId(), "userid").toString());
//        }
//        if(rs.getVariable(t.getProcessInstanceId(), "policyNo") != null){
//            v.setPolicyNo(rs.getVariable(t.getProcessInstanceId(), "policyNo").toString());
//        }
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
            if(var.getVariableName().equals("username")) v.setUsername((String) var.getValue());
            if(var.getVariableName().equals("userid")) v.setUserid((String) var.getValue());
            if(var.getVariableName().equals("transactionNo")) v.setTransactionNumber(var.getValue().toString());
            if(var.getVariableName().equals("modules")) v.setGroup(var.getValue().toString());
            if(var.getVariableName().equals("status")) v.setStatus(var.getValue().toString());
            if(var.getVariableName().equals("policyNo")) v.setPolicyNo((String) var.getValue());
        }
//        this.setVariables(v);

        this.setSuspended(false);
        this.setCompleted(true);
        this.setStartTime(t.getStartTime());
        this.setClaimTime(t.getClaimTime());
        this.setEndTime(t.getEndTime());
        this.setDueDate(t.getDueDate());
    }

    @Data
    public class Variables{
        private String username;
        private String userid;
        private String transactionNumber;
        private String policyNo;
        private String status;
        private String group;
    }
}
