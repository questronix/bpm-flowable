package org.prulife.com.entities;


import lombok.Data;

@Data
public class RequestModel {

    private String userId;
    private int limit;
    private int page;

}
