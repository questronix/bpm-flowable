package org.prulife.com.services;

import com.mashape.unirest.http.HttpResponse;
import org.prulife.com.config.ServicesApiEndpointConfig;
import org.prulife.com.entities.ResponseModel;
import org.prulife.com.utilities.HttpUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component(value = "TaskAssignment")
public class Assignment {

    private  static final Map<String , Object> HEADERS =  Collections.unmodifiableMap(new HashMap<String, Object>() {{
        put("content-type", "application/json");
        put("cache-control", "no-cache");
    }});

    private HttpUtility serviceInvoker;

    private ServicesApiEndpointConfig servicesApiEndpointConfig;

    @Autowired
    Assignment(HttpUtility serviceInvoker, ServicesApiEndpointConfig servicesApiEndpointConfig){
        this.serviceInvoker = serviceInvoker;
        this.servicesApiEndpointConfig = servicesApiEndpointConfig;

    }

//    public String assignCsaPrescreener(String username) {
//        return username;
//    }
//
//    public String assignCsaProcessor(String prescreener) {
//        return prescreener;
//    }
//
//    public String assignCsaApprover(String unitHead) { return unitHead; }
//
//    public String assignCsaApprover(String unitHead) { return unitHead; }


    public String assignAsmProcessor(String transactionNo) {
        Gson gson = new Gson();
        Map<String, Object> request = new HashMap<>();
        request.put("transactionNo", transactionNo);
        request.put("transactionStateId", "4");
        HttpResponse<String> httpResponse = serviceInvoker.HttpPost(servicesApiEndpointConfig.getBpmDBmanagerGetAssignedProcessorEndpoint(), HEADERS, gson.toJson(request));
        System.out.println("--------" + httpResponse);

        ResponseModel model = gson.fromJson(httpResponse.getBody(), ResponseModel.class);
        Map<String, Object>  res = model.getResult();
        System.out.println("Assigned user-----"+ res.get("assignedUser"));
        return res.get("assignedUser").toString();
    }

    public String assignAsmApprover(String transactionNo, int personalLimit) {
        Gson gson = new Gson();
        Map<String, Object> request = new HashMap<>();
        request.put("transactionNo", transactionNo);
        request.put("transactionStateId", "5");
        request.put("personalLimit", personalLimit);

        HttpResponse<String> httpResponse = serviceInvoker.HttpPost(servicesApiEndpointConfig.getBpmDBmanagerGetAssignedApproverEndpoint(), HEADERS, gson.toJson(request));
        System.out.println("--------" + httpResponse);

        ResponseModel model = gson.fromJson(httpResponse.getBody(), ResponseModel.class);
        Map<String, Object>  res = model.getResult();
        System.out.println("Assigned user-----"+ res.get("assignedUser"));
        return res.get("assignedUser").toString();
    }

    public String assignUCProcessor(String transactionNo) {
        Gson gson = new Gson();
        Map<String, Object> request = new HashMap<>();
        request.put("transactionNo", transactionNo);
        request.put("transactionStateId", "6");
        HttpResponse<String> httpResponse = serviceInvoker.HttpPost(servicesApiEndpointConfig.getBpmDBmanagerGetAssignedProcessorEndpoint(), HEADERS, gson.toJson(request));
        System.out.println("--------" + httpResponse);

        ResponseModel model = gson.fromJson(httpResponse.getBody(), ResponseModel.class);
        Map<String, Object>  res = model.getResult();
        System.out.println("Assigned user-----"+ res.get("assignedUser"));
        return res.get("assignedUser").toString();
    }

    public String assignUCApprover(String transactionNo, String personalLimit) {
        Gson gson = new Gson();
        Map<String, Object> request = new HashMap<>();
        request.put("transactionNo", transactionNo);
        request.put("transactionStateId", "7");
        request.put("personalLimit", personalLimit);

        HttpResponse<String> httpResponse = serviceInvoker.HttpPost(servicesApiEndpointConfig.getBpmDBmanagerGetAssignedApproverEndpoint(), HEADERS, gson.toJson(request));
        System.out.println("--------" + httpResponse);

        ResponseModel model = gson.fromJson(httpResponse.getBody(), ResponseModel.class);
        Map<String, Object>  res = model.getResult();
        System.out.println("Assigned user-----"+ res.get("assignedUser"));
        return res.get("assignedUser").toString();
    }

    public String assignComplianceReviewer(String transactionNo) {
        Gson gson = new Gson();
        Map<String, Object> request = new HashMap<>();
        request.put("transactionNo", transactionNo);
        request.put("transactionStateId", "8");
        HttpResponse<String> httpResponse = serviceInvoker.HttpPost(servicesApiEndpointConfig.getBpmDBmanagerGetAssignedProcessorEndpoint(), HEADERS, gson.toJson(request));
        System.out.println("--------" + httpResponse);

        ResponseModel model = gson.fromJson(httpResponse.getBody(), ResponseModel.class);
        Map<String, Object>  res = model.getResult();
        System.out.println("Assigned user-----"+ res.get("assignedUser"));
        return res.get("assignedUser").toString();
    }

}
