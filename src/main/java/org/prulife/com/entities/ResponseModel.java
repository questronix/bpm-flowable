package org.prulife.com.entities;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseModel {

    private String status;

    private String statusCode;

    private String message;

    private Boolean isSuccess;

    private Map<String, Object> result;

}
