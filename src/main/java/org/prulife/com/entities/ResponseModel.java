package org.prulife.com.entities;

import lombok.Data;

import java.security.PrivateKey;
import java.util.Map;

@Data
public class ResponseModel {

    private String status;
    private boolean isSuccess;
    private String message;
    private Map<String, Object> result;
}
