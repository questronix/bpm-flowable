package org.prulife.com.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ServicesApiEndpointConfig {

    @Value("${com.pluk.services.bpmdbManager.GetAssignedProcessorEndpoint:http://10.172.81.146:12004/getAssignedProcessor}")
    private String bpmDBmanagerGetAssignedProcessorEndpoint;

    @Value("${com.pluk.services.bpmdbManager.GetAssignedApproverEndpoint:http://10.172.81.146:12004/getAssignedApprover}")
    private String bpmDBmanagerGetAssignedApproverEndpoint;
}
